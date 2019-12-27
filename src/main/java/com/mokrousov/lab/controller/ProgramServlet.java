package com.mokrousov.lab.controller;

import com.mokrousov.lab.handlers.*;
import com.mokrousov.lab.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProgramServlet extends HttpServlet {
  private VersionService service;
  private Map<String, Handler> handlers;

  @Autowired
  public ProgramServlet(VersionService service) {
    this.service = service;

    handlers = new HashMap<>();
    handlers.put("insertFile", new InsertFileHandler());
    handlers.put("deleteFile", new DeleteFileHandler());
    handlers.put("insertPV", new InsertPVHandler());
    handlers.put("deletePV", new DeletePVHandler());
    handlers.put("insertProgram", new InsertProgramHandler());
    handlers.put("deleteProgram", new DeleteProgramHandler());
    handlers.put("insertFU", new InsertFUHandler());
    handlers.put("deleteFU", new DeleteFUHandler());
  }

  @PostMapping("/view")
  protected String doPost(@RequestParam Map<String, String> form, Model model) throws ServletException, IOException {
    String inputType = form.get("type");
    return handlers.get(inputType).handle(form, service, model);
  }

  @GetMapping("/view")
  protected String doGet(Model model) throws ServletException, IOException {
    return Handler.render(model, null, service);
  }
}

