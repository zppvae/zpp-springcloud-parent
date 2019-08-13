package org.zpp.api.vo;

import lombok.Data;
import org.zpp.api.model.SysRole;
import org.zpp.api.model.SysUser;

import java.util.List;

/**
 * @author zpp
 * @date 2019/8/9 14:40
 */
@Data
public class UserVO extends SysUser {

    /**
     * 角色集合
     */
    private List<SysRole> roles;
}
