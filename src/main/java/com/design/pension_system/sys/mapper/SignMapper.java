package com.design.pension_system.sys.mapper;

import java.util.HashMap;

public interface SignMapper {

    HashMap checkLoginCode(HashMap params);

    int userRegister(HashMap param);

    HashMap checkPassWord(HashMap params);

    HashMap queryPhoneExist(Object phone);
}
