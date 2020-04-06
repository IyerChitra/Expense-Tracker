package com.expense.tracker.models;

public class BaseResponse<T> {

  private T data;
  private String errorCode;
  private String errorDesc;

  public BaseResponse(T data, String errorCode, String errorDesc){
      this.data = data;
      this.errorCode = errorCode;
      this.errorDesc = errorDesc;
  }
}
