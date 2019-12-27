package com.mokrousov.lab.exceptions;

public class InvalidUpdateException extends Exception {
  private int code = -1;
  private Object[] args;

  public InvalidUpdateException(int code, Object... args) {
    this.code = code;
    this.args = args;
  }

  public InvalidUpdateException(String message) {
    super(message);
  }

  public InvalidUpdateException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidUpdateException(Throwable cause) {
    super(cause);
  }

  public int getCode() {
    return code;
  }

  public Object[] getArgs() {
    return args;
  }
}
