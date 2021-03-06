package com.mokrousov.lab.handlers;

import com.mokrousov.lab.exceptions.InvalidUpdateException;
import com.mokrousov.lab.service.VersionService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class InsertFileHandler implements Handler {
  @Override
  public String handle(Map<String, String> request, VersionService service, Model model) throws ServletException, IOException {
    String programName = request.get("programName");
    String version = request.get("programVersion");
    String fileName = request.get("fileName");
    String initVersion = request.get("initVersion");
    String errorMsg = null;

    try {
      service.newFile(programName, version, fileName, initVersion);
    } catch (InvalidUpdateException e) {
      errorMsg = Handler.handleError(e, LocaleContextHolder.getLocale());
    }

    return Handler.render(model, errorMsg, service);
  }
}
