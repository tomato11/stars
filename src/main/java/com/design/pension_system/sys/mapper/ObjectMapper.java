package com.design.pension_system.sys.mapper;

import com.design.pension_system.sys.domain.DicCommon;
import com.design.pension_system.sys.domain.Region;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ObjectMapper {

    String queryTableNameById(String var1);


    void saveMiddlePhoto(@Param("photoList") List<HashMap> result, @Param("mainId") String wid,
                         @Param("tableName")String tableName);

    int deletePhoto(@Param("wid") String wid,@Param("tableName") String tableName);

    List<HashMap> queryPhotoByMainId(@Param("mainId") String mainId,@Param("tableName") String tableName);

    List<Region> queryXzqhTree();

    List<DicCommon> selectAll(HashMap<String, Object> params);

    int selectAllCount(HashMap<String, Object> params);
}
