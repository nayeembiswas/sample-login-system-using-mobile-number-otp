package com.example.demo.response;

import java.io.Serializable;


import lombok.Data;

/**
 * @Since June 23, 2021
 * @Author Md. Chabed alam - 601
 * @Project ibcs-bof-erp
 * @version   1.0.0
 */

@Data
public class CommonResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private boolean status;
	
	private String message;
	
	private String messageBn;
	
	private Object data;
	
	
	
	
}
