package com.jinx.test.multiple.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinx.test.multiple.entity.AcloudConfigRepo;
import com.jinx.test.multiple.mapper.slavor.AcloudConfigRepoMapper;
import com.jinx.test.multiple.service.IAcloudConfigRepoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置仓库 服务实现类
 * </p>
 *
 * @author jinxin
 * @since 2024-01-11
 */
@Service
public class AcloudConfigRepoServiceImpl extends ServiceImpl<AcloudConfigRepoMapper, AcloudConfigRepo> implements IAcloudConfigRepoService {

}
