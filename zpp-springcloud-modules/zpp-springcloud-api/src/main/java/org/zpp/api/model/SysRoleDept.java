package org.zpp.api.model;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
public class SysRoleDept extends Model<SysRoleDept> {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer roleId;

	private Integer deptId;

}
