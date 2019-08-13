package org.zpp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.zpp.api.model.SysResource;
import org.zpp.user.mapper.SysResourceMapper;
import org.zpp.user.service.SysResourceService;

import java.util.List;

/**
 * @author zpp
 * @date 2019/8/9 16:59
 */
@Service
@AllArgsConstructor
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {

    @Override
    public List<SysResource> getResourcesByRoleId(Integer roleId) {
        return baseMapper.selectResourcesByRoleId(roleId);
    }
}
