package org.zpp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String username;

	private String password;

	/**
	 * 随机盐
	 */
	@JsonIgnore
	private String salt;

	private LocalDateTime createTime;


	private LocalDateTime updateTime;

	private Integer deptId;

}
