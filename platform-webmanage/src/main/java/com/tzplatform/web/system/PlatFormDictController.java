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
     * 添加字典
     *
     * @param platFormDict
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addDict", produces = "application/json; charset=utf-8")
    public BaseResultDto addDict(PlatFormDict platFormDict) {
        return platFormDictService.addDict(platFormDict);
    }

    /**
     * 修改字典
     *
     * @param platFormDict
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/editDict", produces = "application/json; charset=utf-8")
    public BaseResultDto editDict(PlatFormDict platFormDict) {
        return platFormDictService.editDict(platFormDict);
    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delDict", produces = "application/json; charset=utf-8")
    public BaseResultDto delDict(String id) {
        return platFormDictService.delDict(id);
    }

    /**
     * 查询字典类型
     *
     * @param platFormDict
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/dictList", produces = "application/json; charset=utf-8")
    public BaseResultDto dictList(PlatFormDict platFormDict) {
        return platFormDictService.dictList(platFormDict);
    }

    /**
     * 查询字典列表
     *
     * @param platFormDict
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/dictValueList", produces = "application/json; charset=utf-8")
    public BaseResultDto dictValueList(PlatFormDict platFormDict) {
        return platFormDictService.dictValueList(platFormDict);
    }
}
