package com.design.pension_system.sys.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ActivityRoomMapper {


    int insertActivityRoom(HashMap param);

    int updateActivityRoom(HashMap param);

    int deleteActivityRoom(String wid);

    List<HashMap> activityRoomList(HashMap params);

    HashMap activityRoomDetails(String wid);


}
