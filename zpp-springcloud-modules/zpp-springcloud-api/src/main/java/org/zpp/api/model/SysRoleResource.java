package org.zpp.api.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
public class SysRoleResource extends Model<SysRoleResource> {

	private static final long serialVersionUID = 1L;

	private Integer roleId;

	private Integer resourceId;
}
