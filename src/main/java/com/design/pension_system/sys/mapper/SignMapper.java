package com.design.pension_system.sys.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface SignMapper {

    String checkLoginCode(HashMap params);

    int userRegister(HashMap param);

    String checkPassWord(HashMap params);

    String queryPhoneExist(Object phone);

    void insertToken(@Param("ticket") String ticket, @Param("loginId") String loginId);

    String checkToken(String ticket);

    Object checkLoginId(String loginId);

    Object checkPhone(String phone);
}
