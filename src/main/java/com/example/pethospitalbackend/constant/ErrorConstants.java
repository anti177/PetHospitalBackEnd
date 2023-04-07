package com.example.pethospitalbackend.constant;

import java.net.URI;

/**
 * ErrorConstants
 *
 * @author yyx
 */
public final class ErrorConstants {
  public static final String USER_HAS_ALREADY_REGISTER = "邮箱已经注册过";
  public static final URI DEFAULT_TYPE = null;

  private ErrorConstants() {
    throw new IllegalStateException("Cannot create instance of static constant class");
  }
}
