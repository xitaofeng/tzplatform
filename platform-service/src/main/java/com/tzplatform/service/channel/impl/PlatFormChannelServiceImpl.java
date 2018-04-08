package com.tzplatform.service.channel.impl;

import com.tzplatform.dao.channel.PlatFormChannelDao;
import com.tzplatform.entity.channel.PlatFormChannel;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.common.TreeDto;
import com.tzplatform.entity.system.PlatFormMenu;
import com.tzplatform.service.channel.PlatFormChannelService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "platFormChannelService")
public class PlatFormChannelServiceImpl implements PlatFormChannelService {

    @Resource
    private PlatFormChannelDao platFormChannelDao;

    /**
     * 添加栏目
     *
     * @param platFormChannel
     * @return
     */
    @Override
    @AopLog(description = "添加栏目", menuname = "栏目管理")
    public BaseResultDto addChannel(PlatFormChannel platFormChannel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormChannel.setId(UidUtils.getId());
        Integer result = platFormChannelDao.addChannel(platFormChannel);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 修改栏目
     *
     * @param platFormChannel
     * @return
     */
    @Override
    @AopLog(description = "修改栏目", menuname = "栏目管理")
    public BaseResultDto editChannel(PlatFormChannel platFormChannel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormChannelDao.editChannel(platFormChannel);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除栏目
     *
     * @param platFormChannel
     * @return
     */
    @Override
    @AopLog(description = "删除栏目", menuname = "栏目管理")
    public BaseResultDto delChannel(PlatFormChannel platFormChannel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormChannelDao.delChannel(platFormChannel);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_DELETE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_DELETE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询栏目
     *
     * @param platFormChannel
     * @return
     */
    @Override
    public BaseResultDto channelList(PlatFormChannel platFormChannel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormChannel> resultList = platFormChannelDao.channelList(platFormChannel);
        Integer resultCount = platFormChannelDao.channelListCount(platFormChannel);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 获取一级栏目
     *
     * @param platFormChannel
     * @return
     */
    @Override
    public BaseResultDto channelRootList(PlatFormChannel platFormChannel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormChannel.setPageSize(0);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(platFormChannelDao.channelRootList(platFormChannel));
        return baseResultDto;
    }

    /**
     * 获取栏目树结构
     *
     * @param platFormChannel
     * @return
     */
    @Override
    public BaseResultDto channelTree(PlatFormChannel platFormChannel) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<TreeDto> getParentTree = platFormChannelDao.channelRootList(platFormChannel).stream().map(channelDto -> {
            TreeDto getTree = new TreeDto();
            getTree.setLabel(channelDto.getName());
            getTree.setId(channelDto.getId());
            return getTree;
        }).collect(Collectors.toList());
        List<TreeDto> getTreeList = getChildTree(getParentTree);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(getTreeList);
        return baseResultDto;
    }

    /**
     * 检查当前节点是否有子节点
     *
     * @param platFormChannel
     * @return
     */
    public boolean checkHasChildren(PlatFormChannel platFormChannel) {
        boolean checkreuslt = false;
        if (platFormChannelDao.channelListCount(platFormChannel) > 0) {
            checkreuslt = true;
        }
        return checkreuslt;
    }

    /**
     * 递归查询树菜单公共方法
     *
     * @param topMenuList
     * @return
     */
    public List<TreeDto> getChildTree(List<TreeDto> topMenuList) {
        for (TreeDto treeDto : topMenuList) {
            PlatFormChannel platFormChannel = new PlatFormChannel();

            platFormChannel.setParentid(treeDto.getId());
            if (!checkHasChildren(platFormChannel)) {
                continue;
            }
            platFormChannel.setPageSize(0);
            List<TreeDto> getChildTree = platFormChannelDao.channelList(platFormChannel).stream().map(channelDto -> {
                TreeDto getTree = new TreeDto();
                getTree.setLabel(channelDto.getName());
                getTree.setId(channelDto.getId());
                return getTree;
            }).collect(Collectors.toList());
            treeDto.setChildren(getChildTree);
            getChildTree(getChildTree);
        }
        return topMenuList;
    }


}
