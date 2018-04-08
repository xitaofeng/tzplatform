package com.tzplatform.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 转发到升级浏览器页面
 */
@Controller
@RequestMapping(value = "/upbrower")
public class UpBrowerController {


    @RequestMapping(value = "/upbrower")
    public String upbrower() {
        return "upbrower";
    }

}
