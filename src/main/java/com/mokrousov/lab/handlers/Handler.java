package com.mokrousov.lab.handlers;

import com.mokrousov.lab.exceptions.InvalidUpdateException;
import com.mokrousov.lab.internationalization.ErrorFormatter;
import com.mokrousov.lab.model.Program;
import com.mokrousov.lab.service.VersionService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface Handler {
  String handle(Map<String, String> request, VersionService service, Model model) throws ServletException, IOException;

  static String handleError(InvalidUpdateException e, Locale locale) {
    if (!ErrorFormatter.isCustom(e.getCode())) return e.getMessage();
    return ErrorFormatter.format(e.getCode(), e.getArgs(), locale);
  }

  static String render(Model model, String errorMsg, VersionService service) throws ServletException, IOException {
    List<Program> programs = null;

    try {
      programs = service.findAllPrograms();
    } catch (InvalidUpdateException e) {
      errorMsg = handleError(e, LocaleContextHolder.getLocale());
    }

    model.addAttribute("programs", programs);
    model.addAttribute("error", errorMsg);
    return "view";
  }
}
