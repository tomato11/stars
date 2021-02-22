package com.design.pension_system.sys.mapper;

import java.util.HashMap;
import java.util.List;

public interface ForumMapper {
    int insertForum(HashMap param);

    int updateForum(HashMap param);

    int deleteForum(String wid);






    List<HashMap> ForumListByUser(HashMap params);
}
