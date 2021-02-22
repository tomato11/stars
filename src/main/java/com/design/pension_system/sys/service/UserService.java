package com.design.pension_system.sys.service;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

public interface UserService {

    PageInfo<HashMap> userList(HashMap params);

    List<HashMap> evaluateList(HashMap params);

    PageInfo<HashMap> businessList(HashMap params);

    List<HashMap> getBusinessByType(HashMap params);

    HashMap userDetils(String loginId);

    int updateUser(HashMap param);

    List<HashMap> getMenuByLoginId(HashMap params);
}
