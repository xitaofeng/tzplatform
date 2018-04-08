package com.tzplatform.service.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.CampusScenery;
import com.tzplatform.entity.mobileplatform.CampusSceneryType;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface PlatformCampusSceneryService {
     BaseResultDto campusSceneryType(CampusSceneryType campusSceneryType,CommonsMultipartFile[] images);
     BaseResultDto campusSceneryTypeList(CampusSceneryType campusSceneryType);

     BaseResultDto addCampusScenery(CampusScenery campusScenery,CommonsMultipartFile[] images);
     BaseResultDto  campusSceneryList(CampusScenery campusScenery,HttpServletRequest request);
     BaseResultDto approve(CampusScenery campusScenery);
     BaseResultDto delete(String ids);
}
