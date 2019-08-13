package org.zpp.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zpp.api.model.SysResource;

import java.util.List;

public interface SysResourceMapper extends BaseMapper<SysResource> {


	List<SysResource> selectResourcesByRoleId(Integer roleId);


	List<String> selectPermissionsByRoleIds(String roleIds);
}