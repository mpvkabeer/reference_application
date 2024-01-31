package com.jsrabk.reference.app.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommonController {

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }
}

