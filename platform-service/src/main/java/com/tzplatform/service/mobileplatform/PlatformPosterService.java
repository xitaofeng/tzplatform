package com.tzplatform.service.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.mobileplatform.Poster;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface PlatformPosterService {

     BaseResultDto addPoster(Poster poster, CommonsMultipartFile[] posterImage);
     BaseResultDto listPoster(Poster poster, HttpServletRequest request);
     BaseResultDto updatePoster(Poster poster, CommonsMultipartFile[] posterImage);
     BaseResultDto deletePoster(Poster poster);
     BaseResultDto updateStatus(Poster poster);


}
