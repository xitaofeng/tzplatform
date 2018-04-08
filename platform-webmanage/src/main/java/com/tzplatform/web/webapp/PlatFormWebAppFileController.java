package com.tzplatform.web.webapp;

import com.tzplatform.service.webapp.PlatFormWebAppService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/platformAppFile")
public class PlatFormWebAppFileController {
    @Resource
    private PlatFormWebAppService platFormWebAppService;

    @RequestMapping(method = RequestMethod.GET, value = "/getFileList", produces = "application/json;charset=utf-8")
    public void getFileList(HttpServletResponse response, String id) {
        platFormWebAppService.getFileList(id, response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAppFileList", produces = "application/json;charset=utf-8")
    public void getAppFileList(HttpServletResponse response, String id) {
        platFormWebAppService.getAppFileList(id, response);
    }

    @RequestMapping(method = RequestMethod.GET,value = "getFileByPath",produces = "application/json;charset=utf-8")
    public void getFileByPath(HttpServletResponse response, String path,String filename){
        platFormWebAppService.getFileByPath(path,filename,response);
    }
}
