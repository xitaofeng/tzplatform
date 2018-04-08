package com.tzplatform.web.channel;

import com.tzplatform.service.channel.PlatFormChannelContentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/platformChannelFile")
public class PlatFormChannelFile {
    @Resource
    private PlatFormChannelContentService platFormChannelContentService;

    @RequestMapping(method = RequestMethod.GET,value = "/getFileList",produces = "application/json;charset=utf-8")
    public void getFileList(HttpServletResponse response, String id){
        platFormChannelContentService.getFileList(id,response);
    }
}
