package com.example.pethospitalbackend.constant;

public final class UserRoleConstants {
  public static final String ROLE_USER = "user";
  public static final String ROLE_ADMIN = "manager";

  private UserRoleConstants() {
    throw new IllegalStateException("Cannot create instance of static constant class");
  }
}
