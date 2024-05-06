package com.project.urban.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResourceNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1649400644426728338L;

	private String status;
	private String message;
	private Object data;

//	public String getStatus() {
//		return status;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public Object getData() {
//		return data;
//	}
//	public ResourceNotFoundException(String status, String message, Object data) {
//		super();
//		this.status = status;
//		this.message = message;
//		this.data = data;
//	}

}
