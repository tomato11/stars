package com.design.pension_system.sys.service;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;

public interface ForumService {

    int insertForum(HashMap param);

    int updateForum(HashMap param);

    int deleteForum(String wid);



    PageInfo<HashMap> ForumListByUser(HashMap params);



}
