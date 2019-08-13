package org.zpp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zpp.api.model.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

	/**
	 * 根据用户id查询角色
	 * @param userId
	 * @return
	 */
	List<SysRole> getRolesByUserId(Integer userId);

}