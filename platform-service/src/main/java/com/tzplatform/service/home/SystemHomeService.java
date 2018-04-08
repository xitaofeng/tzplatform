package com.tzplatform.service.home;

import com.tzplatform.entity.common.BaseResultDto;

public interface SystemHomeService {

    BaseResultDto appTotal();

    BaseResultDto userTotal();

    BaseResultDto apiTotal();

}
