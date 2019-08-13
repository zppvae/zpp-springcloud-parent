package org.zpp.api.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String type;

	private String title;

	private String createBy;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	private String remoteAddr;

	private String userAgent;

	private String requestUri;

	private String method;

	private String params;

	private Long time;

	private String exception;

	/**
	 * 服务ID
	 */
	private String serviceId;

	/**
	 * 删除标记
	 */
	private String delFlag;


}
