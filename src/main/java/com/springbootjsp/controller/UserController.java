package com.springbootjsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/index2")
    public String index2() {
        return "index2";
    }

    @RequestMapping("/index3")
    public String index3() {
        return "index3";
    }

    //@RequestMapping("/index3")
    //public String test(HttpServletRequest request) {
    //    request.setAttribute("isMustPay", "1");
    //    request.setAttribute("ISOpenYibaoAdvancePay", "1");
    //    String s = "";
    //    //Test.mapStringToMap(s);
    //    return "payOrder";
    //}
    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }

}
