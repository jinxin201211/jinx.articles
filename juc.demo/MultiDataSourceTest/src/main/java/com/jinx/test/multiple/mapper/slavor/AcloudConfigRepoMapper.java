package com.jinx.test.multiple.mapper.slavor;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinx.test.multiple.entity.AcloudConfigRepo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 配置仓库 Mapper 接口
 * </p>
 *
 * @author jinxin
 * @since 2024-01-11
 */
@Mapper
@DS("slavor")
public interface AcloudConfigRepoMapper extends BaseMapper<AcloudConfigRepo> {

}
