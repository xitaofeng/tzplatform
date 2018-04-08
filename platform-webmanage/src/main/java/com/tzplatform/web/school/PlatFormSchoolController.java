package com.tzplatform.web.school;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.school.PlatFormSchool;
import com.tzplatform.service.school.PlatFormSchoolService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformschool")
public class PlatFormSchoolController {

    @Resource
    private PlatFormSchoolService platFormSchoolService;


    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/schoolList", produces = "application/json;charset=utf-8")
    public BaseResultDto schoolList(PlatFormSchool platFormSchool){
        return platFormSchoolService.schoolList(platFormSchool);
    }

}
