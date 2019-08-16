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
	 * 角色类型
	 */
	private int roleType;

	private String roleDesc;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	/**
	 * 删除标识
	 */
	private String delFlag;

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
