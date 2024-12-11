package com.lzmhc.webhtop.controllers;

import com.lzmhc.webhtop.dto.InfoDto;
import com.lzmhc.webhtop.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class IndexController {
    @Autowired
    private InfoService infoService;
    @GetMapping
    public String getIndex(Map<String,InfoDto> map){
        map.put("info",infoService.getInfo());
        return "index";
    }
}
