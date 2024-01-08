package com.ra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/403Page")
public class DemoController {
    @GetMapping({"", "/"})
    public String home() {
        return "403Page";
    }
}
