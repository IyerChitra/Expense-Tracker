package com.expense.tracker.controller.advice;

import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.UserNotFoundException;
import com.expense.tracker.exceptions.WalletNotFoundException;
import com.expense.tracker.models.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FailureResponseAdvice {

  private static final Logger logger = LoggerFactory.getLogger(FailureResponseAdvice.class);

  @ExceptionHandler(value = {UserNotFoundException.class, WalletNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public BaseResponse<Object> resourceNotFoundException(ExpenseTrackerException e) {
    logger.error("Failure Response Advice :: { }", e);
    return new BaseResponse<>(null, e.getErr().name(), e.getErr().getErrorDesc());
  }

  @ExceptionHandler(value = {Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public BaseResponse<Object> failureResponseHandler() {
    return new BaseResponse<>(
        null, ErrorCode.TECHNICAL_ERROR.name(), ErrorCode.TECHNICAL_ERROR.getErrorDesc());
  }
}
