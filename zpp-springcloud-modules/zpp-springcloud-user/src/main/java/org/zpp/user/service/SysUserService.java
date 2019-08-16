package org.zpp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zpp.api.dto.UserDTO;
import org.zpp.api.model.SysUser;

/**
 * @author zpp
 * @date 2018/12/10 15:25
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户名查询
     * @param username
     * @return
     */
    UserDTO getUserByUsername(String username);

    /**
     * 新增用户
     * @param dto
     * @return
     */
    boolean saveUser(UserDTO dto);
}
