package org.zpp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zpp.api.model.SysResource;

import java.util.List;

public interface SysResourceService extends IService<SysResource> {

	/**
	 * 根据角色id查询资源
	 * @param roleId
	 * @return
	 */
	List<SysResource> getResourcesByRoleId(Integer roleId);

}