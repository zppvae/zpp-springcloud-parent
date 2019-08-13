package org.zpp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.zpp.api.model.SysRole;
import org.zpp.user.mapper.SysRoleMapper;
import org.zpp.user.service.SysRoleService;

import java.util.List;

/**
 * @author zpp
 * @date 2019/8/9 16:58
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> getRolesByUserId(Integer userId) {
        return baseMapper.selectRolesByUserId(userId);
    }
}
