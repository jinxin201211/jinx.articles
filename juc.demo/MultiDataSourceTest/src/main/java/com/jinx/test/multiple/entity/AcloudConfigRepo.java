package com.jinx.test.multiple.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 配置仓库
 * </p>
 *
 * @author jinxin
 * @since 2024-01-11
 */
@TableName("acloud_config_repo")
@ApiModel(value = "AcloudConfigRepo对象", description = "配置仓库")
public class AcloudConfigRepo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @ApiModelProperty("应用名称（服务ID）")
    @TableField("APPLICATION")
    private String application;

    @TableField("PROFILE")
    private String profile;

    @TableField("LABEL")
    private String label;

    @TableField("VERSION")
    private String version;

    @ApiModelProperty("属性KEY")
    @TableField("PROP_KEY")
    private String propKey;

    @ApiModelProperty("属性VALUE")
    @TableField("PROP_VALUE")
    private String propValue;

    @ApiModelProperty("简要描述")
    @TableField("DESCRIPTION")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AcloudConfigRepo{" +
                "id = " + id +
                ", application = " + application +
                ", profile = " + profile +
                ", label = " + label +
                ", version = " + version +
                ", propKey = " + propKey +
                ", propValue = " + propValue +
                ", description = " + description +
                "}";
    }
}
