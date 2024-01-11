package com.jinx.test.multiple.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 绿证查找算法表
 * </p>
 *
 * @author jinxin
 * @since 2024-01-11
 */
@TableName("green_search_algorithm")
@ApiModel(value = "GreenSearchAlgorithm对象", description = "绿证查找算法表")
public class GreenSearchAlgorithm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("algorithm_id")
    private String algorithmId;

    @ApiModelProperty("算法名称")
    @TableField("algorithm_name")
    private String algorithmName;

    @ApiModelProperty("查找类型 1模糊查询 2精准查询")
    @TableField("search_type")
    private Integer searchType;

    @ApiModelProperty("是否启用 1启用 0未启用")
    @TableField("is_enable")
    private Integer isEnable;

    @ApiModelProperty("是否删除 1删除 0未删除")
    @TableField("is_delete")
    private Integer isDelete;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    public String getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(String algorithmId) {
        this.algorithmId = algorithmId;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "GreenSearchAlgorithm{" +
                "algorithmId = " + algorithmId +
                ", algorithmName = " + algorithmName +
                ", searchType = " + searchType +
                ", isEnable = " + isEnable +
                ", isDelete = " + isDelete +
                ", createTime = " + createTime +
                ", updateTime = " + updateTime +
                "}";
    }
}
