package org.zpp.api.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class SysDept extends Model<SysDept> {

	private static final long serialVersionUID = 1L;

	private Integer deptId;

	private String name;

	private Integer sort;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	private Integer parentId;

	/**
	 * 是否删除  -1：已删除  0：正常
	 */
	private String delFlag;

}
