package com.jinx.test.multiple.mapper.master;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinx.test.multiple.entity.GreenSearchAlgorithm;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 绿证查找算法表 Mapper 接口
 * </p>
 *
 * @author jinxin
 * @since 2024-01-11
 */
@Mapper
@DS("master")
public interface GreenSearchAlgorithmMapper extends BaseMapper<GreenSearchAlgorithm> {

}
