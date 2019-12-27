package com.mokrousov.lab.model;

import java.util.List;

public class ProgramVersion {
  private int id;
  private String version;
  private List<FileVariant> files;

  public ProgramVersion(int id, String version, List<FileVariant> files) {
    this.id = id;
    this.version = version;
    this.files = files;
  }

  public ProgramVersion(int id, String version) {
    this.id = id;
    this.version = version;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public List<FileVariant> getFiles() {
    return files;
  }

  public void setFiles(List<FileVariant> files) {
    this.files = files;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(version);
    sb.append(files);
    return sb.toString();
  }
}
