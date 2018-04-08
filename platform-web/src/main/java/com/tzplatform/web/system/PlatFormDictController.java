package com.tzplatform.web.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormDict;
import com.tzplatform.service.system.PlatFormDictService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformDict")
public class PlatFormDictController {

    @Resource
    private PlatFormDictService platFormDictService;


    /**
     * 查询字典列表
     *
     * @param platFormDict
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/dictValueList", produces = "application/json; charset=utf-8")
    public BaseResultDto dictValueList(PlatFormDict platFormDict) {
        return platFormDictService.dictValueList(platFormDict);
    }
}
