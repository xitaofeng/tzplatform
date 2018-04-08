package com.tzplatform.service.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormTechSupport;

public interface PlatFormTechSupportService {

    BaseResultDto addQuestion(PlatFormTechSupport platFormTechSupport);

    BaseResultDto editQuestion(PlatFormTechSupport platFormTechSupport);

    BaseResultDto delQuestion(String id);

    BaseResultDto questionList(PlatFormTechSupport platFormTechSupport);
}
