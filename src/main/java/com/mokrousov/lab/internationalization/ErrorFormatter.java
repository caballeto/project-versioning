package com.mokrousov.lab.internationalization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ErrorFormatter {
  private static final String BUNDLE_BASE = "text";
  private static Map<Integer, String> errorCodes;

  // 1* - program
  // 2* - program versions
  // 3* - files
  // 4* - relations
  // -1 - other
  static {
    errorCodes = new HashMap<>();

    errorCodes.put(10, "view.error.program.exists");
    errorCodes.put(11, "view.error.program.notfound");
    errorCodes.put(12, "view.error.program.notexist");

    errorCodes.put(20, "view.error.pv.notfound");
    errorCodes.put(21, "view.error.pv.exists");
    errorCodes.put(22, "view.error.pv.insert");
    errorCodes.put(23, "view.error.pv.notexist");

    errorCodes.put(30, "view.error.file.used");
    errorCodes.put(31, "view.error.file.notexist");
    errorCodes.put(32, "view.error.file.uses");
    errorCodes.put(33, "view.error.file.exists");

    errorCodes.put(40, "view.error.relation.insert");
    errorCodes.put(41, "view.error.relation.notexist");
  }

  public static boolean isCustom(int code) {
    return errorCodes.containsKey(code);
  }

  public static String format(int code, Object[] args, Locale locale) {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE, locale);
    return String.format(bundle.getString(errorCodes.get(code)), args);
  }
}
