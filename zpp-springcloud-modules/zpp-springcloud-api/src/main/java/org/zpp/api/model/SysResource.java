package org.zpp.api.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class SysResource extends Model<SysResource> {

	private static final long serialVersionUID = 1L;


	private Integer resourceId;

	private String name;

	/**
	 * 菜单权限标识
	 */
	private String permission;

	private Integer parentId;

	private String icon;

	private Integer sort;

	private String type;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	/**
	 * 删除标识
	 */
	private String delFlag;

	/**
	 * 页面url路径
	 */
	private String path;


}
