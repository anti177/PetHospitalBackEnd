package com.example.pethospitalbackend.exception;

import com.example.pethospitalbackend.constant.ErrorConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.AbstractThrowableProblem;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SendMailFailedException extends AbstractThrowableProblem {

  private static final long serialVersionUID = 4775907845387588529L;

  public SendMailFailedException(String message) {

    super(ErrorConstants.DEFAULT_TYPE, message);
  }
}
