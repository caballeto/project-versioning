package com.mokrousov.lab.service;

import com.mokrousov.lab.dao.FilesDAO;
import com.mokrousov.lab.dao.ProgramDAO;
import com.mokrousov.lab.exceptions.InvalidUpdateException;
import com.mokrousov.lab.model.FileVariant;
import com.mokrousov.lab.model.Program;
import com.mokrousov.lab.model.ProgramVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {
  private ProgramDAO programDAO;
  private FilesDAO filesDAO;

  @Autowired
  public VersionServiceImpl(ProgramDAO programDAO, FilesDAO filesDAO) {
    this.programDAO = programDAO;
    this.filesDAO = filesDAO;
  }

  @Override
  public boolean newProgram(String name) throws InvalidUpdateException {
    Program program = programDAO.newProgram(name);
    System.out.println("PROGRAM executing.");
    return program != null;
  }

  @Override
  public boolean removeProgram(String name) throws InvalidUpdateException {
    return programDAO.removeProgram(name);
  }

  @Override
  public boolean newVersion(String programName, String version) throws InvalidUpdateException {
    ProgramVersion pv = programDAO.insertPV(programName, version);
    return pv != null;
  }

  @Override
  public boolean removeVersion(String programName, String version) throws InvalidUpdateException {
    return programDAO.removeProgramVersion(programName, version);
  }

  @Override
  public boolean newFile(String programName, String version, String fileName, String initVersion) throws InvalidUpdateException {
    FileVariant variant = filesDAO.newFile(programName, version, fileName, initVersion);
    return variant != null;
  }

  @Override
  public boolean removeFile(String programName, String version, String fileName, String fileVersion) throws InvalidUpdateException {
    return programDAO.removeFile(programName, version, fileName, fileVersion);
  }

  @Override
  public FileVariant getFile(String programName, String version, String fileName) throws InvalidUpdateException {
    return programDAO.getFile(programName, version, fileName);
  }

  @Override
  public boolean addUsage(FileVariant v1, FileVariant v2) throws InvalidUpdateException {
    programDAO.addUsage(v1, v2);
    return true;
  }

  @Override
  public boolean removeUsage(FileVariant v1, FileVariant v2) throws InvalidUpdateException {
    programDAO.removeUsage(v1, v2);
    return false;
  }

  @Override
  public List<Program> findAllPrograms() throws InvalidUpdateException {
    return programDAO.findAllPrograms();
  }
}
