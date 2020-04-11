package com.expense.tracker.controller.advice;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FailureResponseAdvice {

  @ExceptionHandler(value = {Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public BaseResponse<Object> failureResponseHandler() {
    return new BaseResponse<>(null, "TECHNICAL_ERROR", "");
  }

  @ExceptionHandler(value = ExpenseTrackerException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse<Object> expenseTrackerException() {
    return new BaseResponse<>(null, "TECHNICAL_ERROR", "");
  }

  @ExceptionHandler(value = {UserNotFoundException.class, WalletNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseResponse<Object> resourceNotFoundException(){
    return new BaseResponse<>(null, "NOT_FOUND", "");
  }
}
