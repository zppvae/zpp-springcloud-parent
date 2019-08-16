package org.zpp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zpp.api.model.SysRoleUser;
import org.zpp.user.mapper.SysRoleUserMapper;
import org.zpp.user.service.SysRoleUserService;

/**
 * @author zpp
 * @date 2019/8/16 10:45
 */
@Service
@Slf4j
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper,SysRoleUser> implements SysRoleUserService {
}
