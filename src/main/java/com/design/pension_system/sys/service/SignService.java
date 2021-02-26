package com.design.pension_system.sys.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public interface SignService {


    Object sendLoginCode(String phone) throws UnsupportedEncodingException;

    int userRegister(HashMap param );

    ResponseEntity<Map> findUserByUP(HashMap user, HttpServletResponse response, HttpServletRequest request);

    Boolean checkLoginId(String loginId);

    Boolean checkPhone(String phone);
}
