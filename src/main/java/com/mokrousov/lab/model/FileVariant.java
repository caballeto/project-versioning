package com.mokrousov.lab.model;

public class FileVariant {
  private int id;
  private String name;
  private String variant;

  public FileVariant(int id, String name, String variant) {
    this.id = id;
    this.name = name;
    this.variant = variant;
  }

  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return name + ":" + variant;
  }
}
