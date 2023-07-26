package com.gitlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchProductViewController {

    @GetMapping("/search/product")
    public String getSearchProductPage() {
        return "/searchProductPage";
    }
}
