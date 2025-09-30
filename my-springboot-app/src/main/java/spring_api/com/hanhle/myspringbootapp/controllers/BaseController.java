package spring_api.com.hanhle.myspringbootapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BaseController {

    @RequestMapping("/")
    public String Home(){
        return "This is Home Page";
    }
}
