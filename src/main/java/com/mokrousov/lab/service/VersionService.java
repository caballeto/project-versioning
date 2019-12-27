package com.mokrousov.lab.service;

import com.mokrousov.lab.exceptions.InvalidUpdateException;
import com.mokrousov.lab.model.FileVariant;
import com.mokrousov.lab.model.Program;

import java.util.List;

public interface VersionService {
  boolean newProgram(String name) throws InvalidUpdateException;

  boolean removeProgram(String name) throws InvalidUpdateException;

  boolean newVersion(String programName, String version) throws InvalidUpdateException;

  boolean removeVersion(String programName, String version) throws InvalidUpdateException;

  boolean newFile(String programName, String version, String fileName, String fileVersion) throws InvalidUpdateException;

  boolean removeFile(String programName, String version, String fileName, String fileVersion) throws InvalidUpdateException;

  FileVariant getFile(String programName, String version, String fileName) throws InvalidUpdateException;

  boolean addUsage(FileVariant v1, FileVariant v2) throws InvalidUpdateException;

  boolean removeUsage(FileVariant v1, FileVariant v2) throws InvalidUpdateException;

  List<Program> findAllPrograms() throws InvalidUpdateException;
}
