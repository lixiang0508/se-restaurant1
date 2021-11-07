package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.service.StaffService;
import com.zlx.serestaurant.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author zlx
 * @create 2021-10-18 8:37 上午
 */


@Controller
public class LoginController {


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
        @PostMapping("/api/login")
    //public JsonData  login(HttpSession session,Model model,@RequestParam("username") String username, @RequestParam("password") String password) @RequestBody Map<String,String> params{
        public JsonData  login(HttpSession session,Model model,@RequestParam("username") String username, @RequestParam("password") String password){
        //String username=params.get("username");
        //String password=params.get("password");
            Staff staff = staffService.selectStaff(username, password);
            if(staff==null) {
                model.addAttribute("msg","用户名或者密码错误！");

                return JsonData.buildError("用户名或密码错误");
            }

            session.setAttribute("loginStaff",staff);


            //return JsonData.buildSuccess1("登录成功") ;
            return new JsonData(0,null,"登陆成功") ;
}
        //}



}
