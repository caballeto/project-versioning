package com.mokrousov.lab.model;

import java.util.List;

public class Program {
  private int id;
  private String name;
  private List<ProgramVersion> versions;

  public Program(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Program(int id, String name, List<ProgramVersion> versions) {
    this.id = id;
    this.name = name;
    this.versions = versions;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ProgramVersion> getVersions() {
    return versions;
  }

  public void setVersions(List<ProgramVersion> versions) {
    this.versions = versions;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(" ");
    sb.append(versions);
    return sb.toString();
  }
}
