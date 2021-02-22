package com.design.pension_system.sys.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;


@ApiModel(
        description = "字典表查询参数"
)
public class DicSearchVO {
    @NotBlank
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("排序字段")
    private String orderBy;
    @NotBlank
    @ApiModelProperty("返回对象名")
    private String objName;

    public DicSearchVO() {
    }

    public String toString() {
        return com.alibaba.fastjson.JSONObject.toJSONString(this);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getObjName() {
        return this.objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }
}