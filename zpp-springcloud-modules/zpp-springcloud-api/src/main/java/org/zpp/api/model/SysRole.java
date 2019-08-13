package org.zpp.api.model;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class SysRole extends Model<SysRole> {

	private static final long serialVersionUID = 1L;

	private Integer roleId;

	private String roleName;

	/**
	 * 角色标识
	 */
	private String roleCode;

	private String roleDesc;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	private String delFlag;

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
