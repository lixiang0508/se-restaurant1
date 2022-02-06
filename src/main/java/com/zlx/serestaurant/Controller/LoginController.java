package com.zlx.serestaurant.Controller;


import com.zlx.serestaurant.request.StaffInfoRequest;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.request.LoginRequest;
import com.zlx.serestaurant.service.StaffService;
import com.zlx.serestaurant.utils.JWTUtils;
import com.zlx.serestaurant.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author zlx
 * @create 2021-10-18 8:37 上午
 */


@Controller
@Slf4j
public class LoginController {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    StaffService staffService;
    //判断用户输入的用户名 和密码是否符合规范
    private boolean checkStr(String str) {
        if(str.length()==0 ||str.length()>20 )  return false;
        for(int i=0;i<str.length();i+=1){
            if(((int)str.charAt(i)>='a'&&(int)str.charAt(i)<='z') ||
            ((int)str.charAt(i)>='A'&&(int)str.charAt(i)<='Z')||
                    ((int)str.charAt(i)>='0'&&(int)str.charAt(i)<='9')){
                    continue;
            }  else{
                return false;
            }
        }

        return true;
    }

    @GetMapping(value={"/","/index"})
    public String loginPage() {
        return "index";
    }

     //用户注册
    //注意 如果前端用Json来写 封装成POJO对象这种写法是不行的 应该用@RequestBody注解
    @ResponseBody
    @PostMapping("/api/register")
    /*@RequestParam("userName") String tmpUsrName, @RequestParam("password") String password
     @RequestParam("gender") int tmpGender, @RequestParam("age") int age, @RequestParam("phoneNumber") String tmpPhoneNumber,
    @RequestParam("role") String tmpRole*/
    public JsonData register(HttpSession session, Model model, @RequestParam("username") String tmpUsrName, @RequestParam("password") String tmpPwd,
     @RequestParam("phoneNumber") String tmpPhoneNumber) {
        //String tmpUsrName=staff.getUserName();    //用户填写的用户名
        int cnt = staffService.countByUserName(tmpUsrName);
        if(cnt>0) {
            return JsonData.buildError("用户名已经存在，请直接登录");
        }
        if(checkStr(tmpUsrName)==false)  {
            model.addAttribute("msg","用户名格式不合规范！") ;
              return new JsonData(-1,"用户名格式不合规范！");

        }
       // String tmpPwd=staff.getPassword();//用户填写的密码
        if(checkStr(tmpPwd)==false)  {
            model.addAttribute("msg","密码格式不合规范！") ;
            return new JsonData(-1,"密码格式不合规范！");
        }
        //String tmpName=staff.getName();  //  用户填写的姓名
        /*
        if(tmpName.length()>10) {
            model.addAttribute("msg","姓名不可以超过10个字！") ;
            return new JsonData(-1,"姓名不能超过10个字！");

        }
        //int tmpAge=staff.getAge();  //用户填写的年龄
        if(tmpAge>100){
            model.addAttribute("msg","年龄不能大于100岁！") ;
            return new JsonData(-1,"年龄不能大于100岁！");
        }    */
        //int tmpGender=staff.getGender();//用户填写的性别
        //String tmpPhoneNumber =staff.getPhoneNumber() ;//用户填写的电话号码
        if(tmpPhoneNumber.length()!=11) {
            model.addAttribute("msg","电话号码必须为11位") ;
            return JsonData.buildError("电话号码必须为11位");
        }
        //String tmpRole= staff.getRole();//用户填写的角色
           //向数据库中插入一条新的用户数据
       // Staff staff = new Staff(tmpUsrName,tmpPwd,tmpName,tmpGender,tmpAge,tmpPhoneNumber,tmpRole)   ;
          Staff staff= new Staff(tmpUsrName,tmpPwd,tmpPhoneNumber);
          staffService.addNewStaff(staff);


          return new JsonData(0,null,"注册成功")  ;


    }

    @GetMapping("/main.html")
    public String  mainPage(HttpSession session,Model model) {

        return "main"   ;   }

     @ResponseBody
     @PostMapping("/api/login1")
     //另一个版本的登录
     public JsonData login1(LoginRequest loginRequest) {
         String password=loginRequest.getPassword();
         String username= loginRequest.getUsername();
         Staff staff = staffService.selectStaff(username, password);
         if(staff==null) {
             return JsonData.buildError("用户名或密码错误");
         }
         //设置token到redis
         redisTemplate.opsForValue().set("zlx","nb");
         
         return null;
     }

@ResponseBody
@PostMapping("/api/login")
public JsonData login(LoginRequest loginRequest, HttpSession session, HttpServletResponse response){
        String password=loginRequest.getPassword();
       String username= loginRequest.getUsername();
       //注意 用户名不能重复
    Staff staff = staffService.selectStaff(username, password);


              
    //System.out.println(username);
    //System.out.println(password);
    //System.out.println(staff);
    if(staff==null) {
        //model.addAttribute("msg","用户名或者密码错误！");

        return JsonData.buildError("用户名或密码错误");
    }
    staff.setPassword("");
    //session 保留当前登录的对象
    //session.setAttribute("currentstaff",staff);
   //System.out.println(session.getId());
    String token = JWTUtils.geneJsonWebToken(staff) ;
    log.info("{}请求登录",staff.getUserName());
    log.info("token开始时间") ;
     log.info(String.valueOf(JWTUtils.checkJWT(token).getIssuedAt()));
     log.info("token失效时间");
     log.info(String.valueOf(JWTUtils.checkJWT(token).getExpiration()));
    //response.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=None");
    return new JsonData(0,staff,token) ;


}

@ResponseBody
@GetMapping("api/staff")
//session 保存在内存里 一旦断电就失效 改进版本：token存在redis里
//cookie跨域
public JsonData<Staff> staffInfo(HttpServletRequest request) {
    String token= (String) request.getAttribute("token");
    String id = JWTUtils.checkJWT(token).getId();
      Staff staff =staffService.findByStaffId(Integer.parseInt(id)) ;
    //Staff staff= (Staff) session.getAttribute("currentstaff");

    if(staff==null)   {
        return JsonData.buildError("用户未登录");
    }

    return JsonData.buildSuccess(staff,"用户已经登录");
}



//根据token 查询staff 信息
    @ResponseBody
    @PostMapping("api/find_by_token")
    public JsonData findInfoByToken(HttpServletRequest request){
       String userName=(String) request.getAttribute("userName");
       //String phoneNumber =(String) request.getAttribute("phoneNumber");
       if(userName==null) {
           return JsonData.buildError("查询失败") ;
       }
       Staff staff = staffService.findByUserName(userName);
        if(staff==null) {
            return JsonData.buildError("查询失败") ;
        }
       staff.setPassword("");//保护 密码设空
       return JsonData.buildSuccess(staff,"查找成功");

    }
    @ResponseBody
    @PostMapping("/api/FillInfo")
    public JsonData fillInfo(StaffInfoRequest staffInfoRequest){
              int staffid= staffInfoRequest.getStaffId();
              Staff staff=staffService.findByStaffId(staffid);
              staff.setAge(staffInfoRequest.getAge());
              staff.setGender(staffInfoRequest.getGender());
              staff.setUserName(staffInfoRequest.getUserName());
              staff.setPhoneNumber(staffInfoRequest.getPhoneNumber());
              staff.setPassword(staffInfoRequest.getPassword());
              staff.setName(staffInfoRequest.getName());
              staffService.updateStaff(staff) ;
              return JsonData.buildSuccess(staff,"修改成功");

              //查出来这个staff
             //更新数据 写入数据库

    }


}
