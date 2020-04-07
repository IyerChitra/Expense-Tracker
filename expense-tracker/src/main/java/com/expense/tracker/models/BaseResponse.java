package com.expense.tracker.models;

public class BaseResponse<T> {

	private T data;
	private String errorCode;
	private String errorMessage;
	
	
	public BaseResponse (T data, String errorCode, String errorMessage){
		this.data = data;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		
	}
}
