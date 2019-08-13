package org.zpp.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zpp.api.model.SysUser;

/**
 * @author zpp
 * @date 2018/12/10 15:30
 */
public interface SysUserMapper extends BaseMapper<SysUser>{

    /**
     * 用户名查询
     * @param username
     * @return
     */
    SysUser selectUserByUsername(String username);
}
