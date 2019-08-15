package org.zpp.common.core.util;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private int code = 200;

	@Getter
	@Setter
	private String msg = "success";


	@Getter
	@Setter
	private T data;

	public R() {
		super();
	}

	public R(T data) {
		super();
		this.data = data;
	}

	public R(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public R(Throwable e) {
		super();
		this.code = -1;
		this.msg = e.getMessage();
	}
}