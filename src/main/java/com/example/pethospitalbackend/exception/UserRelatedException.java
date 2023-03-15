package com.example.pethospitalbackend.exception;

import com.example.pethospitalbackend.constant.ErrorConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.AbstractThrowableProblem;


/**
 * AlreadyExistsException
 *
 * @author yyx
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserRelatedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 4775907845387588528L;

    public UserRelatedException(String message) {

        super(ErrorConstants.DEFAULT_TYPE,message);
    }
}
