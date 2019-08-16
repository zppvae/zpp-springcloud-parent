package org.zpp.api.dto;

import lombok.Data;
import org.zpp.api.model.SysRole;
import org.zpp.api.model.SysUser;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author zpp
 * @date 2019/8/9 14:46
 */
@Data
public class UserDTO implements Serializable {

    private SysUser user;

    /**
     * 角色集合
     */
    private List<SysRole> roles;

    /**
     * 角色id集合
     */
    private List<Integer> roleIds;

    /**
     * 权限标识集合
     */
    private Set<String> permissions;
}
