package com.mokrousov.lab.handlers;

import com.mokrousov.lab.exceptions.InvalidUpdateException;
import com.mokrousov.lab.model.FileVariant;
import com.mokrousov.lab.service.VersionService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

public class DeleteFUHandler implements Handler {
  @Override
  public String handle(Map<String, String> request, VersionService service, Model model) throws ServletException, IOException {
    String programName = request.get("programName");
    String version = request.get("programVersion");
    String fileName1 = request.get("fileName1");
    String fileName2 = request.get("fileName2");
    String errorMsg = null;

    try {
      FileVariant f1 = service.getFile(programName, version, fileName1);
      FileVariant f2 = service.getFile(programName, version, fileName2);
      service.removeUsage(f1, f2);
    } catch (InvalidUpdateException e) {
      errorMsg = Handler.handleError(e, LocaleContextHolder.getLocale());
    }

    return Handler.render(model, errorMsg, service);
  }
}
