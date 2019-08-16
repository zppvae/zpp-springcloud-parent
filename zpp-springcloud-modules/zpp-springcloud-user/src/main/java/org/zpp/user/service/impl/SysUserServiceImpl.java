package org.zpp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zpp.api.dto.UserDTO;
import org.zpp.api.model.SysResource;
import org.zpp.api.model.SysRole;
import org.zpp.api.model.SysRoleUser;
import org.zpp.api.model.SysUser;
import org.zpp.user.mapper.SysUserMapper;
import org.zpp.user.service.SysResourceService;
import org.zpp.user.service.SysRoleService;
import org.zpp.user.service.SysRoleUserService;
import org.zpp.user.service.SysUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zpp
 * @date 2018/12/10 15:26
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final SysRoleService sysRoleService;

    private final SysResourceService sysResourceService;

    private final SysRoleUserService sysRoleUserService;

    @Override
    public UserDTO getUserByUsername(String username) {
        SysUser user = baseMapper.selectUserByUsername(username);
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setUser(user);

        //用户角色
        List<SysRole> roles = sysRoleService.getRolesByUserId(user.getUserId());
        dto.setRoles(roles);

        List<Integer> roleIds = roles.stream()
                                .map(SysRole::getRoleId)
                                .collect(Collectors.toList());

        //资源
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId ->{
            List<SysResource> menus = sysResourceService.getResourcesByRoleId(roleId);
            List<String> list = menus.stream()
                    .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                    .map(SysResource::getPermission)
                    .collect(Collectors.toList());
            permissions.addAll(list);
        });
        dto.setPermissions(permissions);
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUser(UserDTO dto) {
        SysUser user = dto.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        baseMapper.insert(user);

        List<SysRoleUser> roleUsers = dto.getRoleIds()
                .stream().map(roleId -> {
                    SysRoleUser roleUser = new SysRoleUser();
                    roleUser.setUserId(user.getUserId());
                    roleUser.setRoleId(roleId);
                    return roleUser;
                }).collect(Collectors.toList());
        return sysRoleUserService.saveBatch(roleUsers);
    }
}
