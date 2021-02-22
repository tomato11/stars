package com.design.pension_system.sys.client;

import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Request;


import java.util.HashMap;
import java.util.Map;

public interface ClientUtil {
    @Request(
            url = "${url}",
            contentType = "application/json",
            type = "post"
    )
    public String post(@DataVariable("url") String url, @Query HashMap request);

    @Request(
            url =  "${url}",
            contentType = "application/json",
            type = "put"
    )
    com.alibaba.fastjson.JSONObject put(@DataVariable("url") String url, @Body Map<String, Object> map);


}
