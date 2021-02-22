package com.design.pension_system.sys.service;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public interface ActivityRoomService {

    int insertActivityRoom(HashMap param );

    int updateActivityRoom(HashMap param);

    int deleteActivityRoom(String wid);

    PageInfo<HashMap> activityRoomList(HashMap params);

    HashMap activityRoomDetails(String wid);


}
