package com.gitlab.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchViewController {

    @GetMapping("/search")
    public String getSearchProductPage() {
        return "/search";
    }

    @GetMapping("/")
    public String index() {
        return "/search";
    }
}