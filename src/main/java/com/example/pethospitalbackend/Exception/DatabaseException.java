package com.example.pethospitalbackend.Exception;

import com.example.pethospitalbackend.Constant.ErrorConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.AbstractThrowableProblem;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseException  extends AbstractThrowableProblem {

	private static final long serialVersionUID = 4775907845387588528L;

	public DatabaseException(String message) {

		super(ErrorConstants.DEFAULT_TYPE,message);
	}
}