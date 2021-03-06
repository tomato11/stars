package com.design.pension_system.sys.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {


    List<HashMap> userList(HashMap params);

    List<HashMap> evaluateList(HashMap params);

    List<HashMap> businessList(HashMap params);

    List<HashMap> getBusinessByType(HashMap params);

    HashMap queryTypeAndWidByLoginId(String loginId);

    int updateUser(HashMap param);

    HashMap queryUserDetils(@Param("wid") String wid);

    void insertUserRoleMiddle(@Param("role") String role, @Param("loginId") String loginId);

    List<HashMap> getMenuByLoginId(HashMap params);
    String getLoginIdByToken(String token);

    List<HashMap> getEvaluateDic();


}
