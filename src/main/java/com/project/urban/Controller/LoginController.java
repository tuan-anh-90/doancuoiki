package com.project.urban.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String viewLogin() {
        return "login";
    }

    @GetMapping("/")
    public String viewLogin1() {
        return "login";
    }

    @GetMapping("/crudpost")
    public String viewcrudpost() {
        return "crudpost";
    }

    @GetMapping("/baidang")
    public String viewBaiDang() {
        return "baidang";
    }

    @GetMapping("/note")
    public String viewNote() {
        return "note";
    }

    @GetMapping("/home")
    public String viewHome() {
        return "home";
    }

    @GetMapping("/homeadmin")
    public String viewHomeUser() {
        return "homeadmin";
    }

    @GetMapping("/tabbar")
    public String viewTabbar() {
        return "tabbar";
    }

    @GetMapping("/boxtabbaruser")
    public String viewTabbaruser() {
        return "boxtabbaruser";
    }

    @GetMapping("/tabbarAdmin")
    public String viewtabbarAdmin() {
        return "tabbarAdmin";
    }

    @GetMapping("/search")
    public String viewsearch() {
        return "search";
    }

    @GetMapping("/resetpassword")
    public String viewResetpassword() {
        return "resetpassword";
    }

    @GetMapping("/listUser")
    public String viewlistUser() {
        return "indexcrud";
    }

    @GetMapping("/profile")
    public String viewprofile() {
        return "profile";
    }

    @GetMapping("/demohome")
    public String viewdemohome() {
        return "demohome";
    }

    @GetMapping("/homeuser")
    public String viewhomeuser() {
        return "homeuser";
    }

    @GetMapping("/editdon")
    public String viewEditDon() {
        return "editdon";
    }

    @GetMapping("/changepassword")
    public String viewchangepassword() {
        return "changepassword";
    }

    @GetMapping("/don")
    public String viewDon() {
        return "don";
    }
}
