package org.zpp.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zpp.api.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 用户id查询角色
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRolesByUserId(Integer userId);
}