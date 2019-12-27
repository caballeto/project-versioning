package com.mokrousov.lab.dao;

import com.mokrousov.lab.exceptions.InvalidUpdateException;
import com.mokrousov.lab.model.FileVariant;
import com.mokrousov.lab.model.Program;
import com.mokrousov.lab.model.ProgramVersion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProgramDAO {
  private static final String BY_NAME = "SELECT * FROM program WHERE name = '%s'";
  private static final String SELECT_PROGRAMS = "SELECT * FROM program";
  private static final String SELECT_VERSIONS = "SELECT * FROM program_version" +
    " INNER JOIN program_versions pv ON program_version.id = pv.version_id" +
    " WHERE program_id = %s";
  private static final String SELECT_FILE_ID = "SELECT * FROM usages WHERE file_id2 = %s";
  private static final String SELECT_FILES = "SELECT * FROM file_version" +
    " INNER JOIN program_version_files pvf on file_version.id = pvf.file_version_id" +
    " WHERE version_id = %s";
  private static final String SELECT_PV = "SELECT * FROM program_version WHERE version = '%s' " +
    "AND id IN (SELECT version_id FROM program_versions WHERE program_id = %s)";

  private static final String SELECT_FILE_PV = "SELECT * FROM file_version" +
    " INNER JOIN program_version_files pvf on file_version.id = pvf.file_version_id" +
    " WHERE version_id = %s AND name = '%s'";

  private static final String INSERT_PROGRAM_RELATION = "INSERT INTO program_versions (program_id, version_id) VALUES (%s, %s)";
  private static final String INSERT_PV = "INSERT INTO program_version (version) VALUES (?)";
  private static final String INSERT_PROGRAM = "INSERT INTO program (name) VALUES (?)";

  private static final String DELETE_PROGRAM = "DELETE FROM program WHERE name = '%s'";
  private static final String DELETE_PV = "DELETE FROM program_version WHERE version = '%s' AND " +
    "id IN (SELECT version_id FROM program_versions WHERE program_id = %s);\n";
  private static final String DELETE_FILE = "DELETE FROM file_version WHERE id = %s";
  private static final String INSERT_USAGE = "INSERT INTO usages (file_id1, file_id2) VALUES (%s, %s)";
  private static final String DELETE_USAGE = "DELETE FROM usages WHERE file_id1 = %s AND file_id2 = %s";

  public List<Program> findAllPrograms() throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      List<Program> programs = toPrograms(stmt.executeQuery(SELECT_PROGRAMS));

      for (Program program : programs) {
        program.setVersions(toVersions(stmt.executeQuery(
          String.format(SELECT_VERSIONS, program.getId())
        )));
      }

      for (Program program : programs) {
        for (ProgramVersion version : program.getVersions()) {
          version.setFiles(toFiles(stmt.executeQuery(
            String.format(SELECT_FILES, version.getId())
          )));
        }
      }

      return programs;
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  public Program getProgramByName(String programName) {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      Program program = toProgram(stmt.executeQuery(
        String.format(BY_NAME, programName)
      ));

      program.setVersions(toVersions(stmt.executeQuery(
        String.format(SELECT_VERSIONS, program.getId())
      )));

      for (ProgramVersion version : program.getVersions()) {
        version.setFiles(toFiles(stmt.executeQuery(
          String.format(SELECT_FILES, version.getId())
        )));
      }

      return program;
    } catch (SQLException | InvalidUpdateException e) {
      return null;
    }
  }

  public ProgramVersion getProgramVersion(String programName, String version) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      Program program = toProgram(stmt.executeQuery(String.format(BY_NAME, programName)));
      return toProgramVersion(stmt.executeQuery(String.format(SELECT_PV, version, program.getId())));
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  private ProgramVersion toProgramVersion(ResultSet rs) throws InvalidUpdateException, SQLException {
    if (!rs.next())
      throw new InvalidUpdateException(20);
    int id = rs.getInt("id");
    String version = rs.getString("version");
    return new ProgramVersion(id, version);
  }

  public ProgramVersion insertPV(String programName, String pv) throws InvalidUpdateException {
    Program program = getProgramByName(programName);

    if (program == null)
      throw new InvalidUpdateException(12, programName);

    ProgramVersion programVersion = getProgramVersion(program.getVersions(), pv);
    if (programVersion != null) {
      throw new InvalidUpdateException(21, programVersion.getVersion());
    }

    ProgramVersion insertedPV = insertPV(pv);
    insertProgramRelation(program.getId(), insertedPV.getId());
    return insertedPV;
  }

  private ProgramVersion insertPV(String pv) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(INSERT_PV, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, pv);
      int affected = stmt.executeUpdate();
      if (affected == 0)
        throw new InvalidUpdateException(22);

      ResultSet rs = stmt.getGeneratedKeys();
      rs.next();
      int id = rs.getInt(1);
      return new ProgramVersion(id, pv);
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  private void insertProgramRelation(int programId, int pVersionId) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      int rows = stmt.executeUpdate(String.format(INSERT_PROGRAM_RELATION, programId, pVersionId));
      if (rows == 0) {
        throw new InvalidUpdateException(40);
      }
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  private Program toProgram(ResultSet rs) throws SQLException, InvalidUpdateException {
    if (!rs.next())
      throw new InvalidUpdateException(11);
    int id = rs.getInt("id");
    String name = rs.getString("name");
    return new Program(id, name);
  }

  private List<ProgramVersion> toVersions(ResultSet rs) throws SQLException {
    List<ProgramVersion> versions = new ArrayList<>();
    while (rs.next()) {
      int id = rs.getInt("id");
      String version = rs.getString("version");
      versions.add(new ProgramVersion(id, version));
    }

    return versions;
  }

  private List<FileVariant> toFiles(ResultSet rs) throws SQLException {
    List<FileVariant> variants = new ArrayList<>();
    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      String variant = rs.getString("variant");
      variants.add(new FileVariant(id, name, variant));
    }

    return variants;
  }

  private static List<Program> toPrograms(ResultSet rs) throws SQLException {
    List<Program> programs = new ArrayList<>();
    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      programs.add(new Program(id, name));
    }

    return programs;
  }

  public static ProgramVersion getProgramVersion(List<ProgramVersion> versions, String version) {
    for (ProgramVersion pversion : versions) {
      if (pversion.getVersion().equals(version)) {
        return pversion;
      }
    }
    return null;
  }

  public Program newProgram(String name) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(INSERT_PROGRAM, Statement.RETURN_GENERATED_KEYS)) {

      if (getProgramByName(name) != null)
        throw new InvalidUpdateException(10, name);

      stmt.setString(1, name);
      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      rs.next();
      int id = rs.getInt(1);
      return new Program(id, name);
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  public boolean removeProgram(String name) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      int affected = stmt.executeUpdate(String.format(DELETE_PROGRAM, name));
      if (affected != 1) {
        throw new InvalidUpdateException(12, name);
      }
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
    return true;
  }

  public boolean removeProgramVersion(String name, String version) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      Program program = getProgramByName(name);

      if (program == null)
        throw new InvalidUpdateException(12, name);

      int affected = stmt.executeUpdate(String.format(DELETE_PV, version, program.getId()));
      if (affected != 1) {
        throw new InvalidUpdateException(23, version, name);
      }
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
    return true;
  }

  public boolean removeFile(String programName, String version, String fileName, String variant) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      FileVariant file = getFile(programName, version, fileName);

      if (isUsed(file, conn))
        throw new InvalidUpdateException(30, fileName);

      int affected = stmt.executeUpdate(String.format(DELETE_FILE, file.getId()));
      if (affected != 1) {
        throw new InvalidUpdateException(31, fileName, variant);
      }
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
    return true;
  }

  private boolean isUsed(FileVariant variant, Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
      ResultSet rs = stmt.executeQuery(String.format(SELECT_FILE_ID, variant.getId()));
      return rs.next();
    }
  }

  public FileVariant getFile(String programName, String version, String fileName) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      ProgramVersion pv = getProgramVersion(programName, version);
      if (pv == null)
        throw new InvalidUpdateException(23, version, programName);

      return toFile(stmt.executeQuery(String.format(SELECT_FILE_PV, pv.getId(), fileName)));
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  private FileVariant toFile(ResultSet rs) throws SQLException {
    if (!rs.next())
      return null;
    int id = rs.getInt("id");
    String name = rs.getString("name");
    String variant = rs.getString("variant");
    return new FileVariant(id, name, variant);
  }

  public void addUsage(FileVariant v1, FileVariant v2) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      int affected = stmt.executeUpdate(String.format(INSERT_USAGE, v1.getId(), v2.getId()));
      if (affected != 1) {
        throw new InvalidUpdateException(32, v1.getName(), v2.getName());
      }
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  public void removeUsage(FileVariant v1, FileVariant v2) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      int affected = stmt.executeUpdate(String.format(DELETE_USAGE, v1.getId(), v2.getId()));
      if (affected != 1) {
        throw new InvalidUpdateException(41, v1.getName(), v2.getName());
      }
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }
}
