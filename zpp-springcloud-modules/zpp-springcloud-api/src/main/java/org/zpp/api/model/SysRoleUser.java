package org.zpp.api.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
public class SysRoleUser extends Model<SysRoleUser> {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private Integer roleId;

}
