package com.example.pethospitalbackend.exception;

import com.example.pethospitalbackend.constant.ErrorConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.AbstractThrowableProblem;


/**
 *UserMailNotRegisterOrPasswordWrongException
 *
 * @author yyx
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserMailNotRegisterOrPasswordWrongException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 4775907845387588528L;

	public UserMailNotRegisterOrPasswordWrongException (String message) {

		super(ErrorConstants.DEFAULT_TYPE,message);
	}
}
