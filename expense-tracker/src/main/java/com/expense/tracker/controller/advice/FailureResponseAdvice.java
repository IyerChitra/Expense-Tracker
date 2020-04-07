package com.expense.tracker.controller.advice;

import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.constants.ExpenseTrackerConstants;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.UserNotFoundException;
import com.expense.tracker.exceptions.WalletNotFoundException;
import com.expense.tracker.models.BaseResponse;
import com.expense.tracker.models.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FailureResponseAdvice {



  @ExceptionHandler(value = ExpenseTrackerException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity<BaseResponse> expenseTrackerException() {
	  System.out.println("Expense Tracker Handler called...... Lets see. ");// added @ResponseStatus, still the same error that cannot convert from Base Response.
    return new ResponseEntity(new BaseResponse<>(null, ExpenseTrackerConstants.Technical_Error, ErrorCode.TECHNICAL_ERROR.getErrorDesc()), null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {UserNotFoundException.class, WalletNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseResponse<Object> resourceNotFoundException(){
    return new BaseResponse<>(null, ExpenseTrackerConstants.User_Not_Found_Error, ErrorCode.USER_NOT_FOUND.getErrorDesc());
  }
  
  @ExceptionHandler(value = {Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public BaseResponse<Object> failureResponseHandler() {
    return new BaseResponse<>(null, "TECHNICAL_ERROR", "");
  }
}
