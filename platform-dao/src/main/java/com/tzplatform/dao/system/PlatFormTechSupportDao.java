package com.tzplatform.dao.system;

import com.tzplatform.entity.system.PlatFormTechSupport;

import java.util.List;

public interface PlatFormTechSupportDao {

    Integer addQuestion(PlatFormTechSupport platFormTechSupport);

    Integer editQuestion(PlatFormTechSupport platFormTechSupport);

    Integer delQuestion(String id);

    List<PlatFormTechSupport> questionList(PlatFormTechSupport platFormTechSupport);

    Integer questionListCount(PlatFormTechSupport platFormTechSupport);

}
