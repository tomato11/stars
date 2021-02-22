package com.design.pension_system.sys.mapper;

import java.util.HashMap;
import java.util.List;

public interface ActivityMapper {
    int insertActivity(HashMap param);

    int updateActivity(HashMap param);

    int deleteActivity(String wid);

    List<HashMap> ActivityListByAdmin(HashMap params);

    HashMap ActivityDetails(String wid);


    List<HashMap> ActivityListByUser(HashMap params);
}
