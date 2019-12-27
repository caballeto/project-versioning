package com.mokrousov.lab.dao;

import com.mokrousov.lab.exceptions.InvalidUpdateException;
import com.mokrousov.lab.model.FileVariant;
import com.mokrousov.lab.model.Program;
import com.mokrousov.lab.model.ProgramVersion;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class FilesDAO {
  private static final String INSERT_FILE = "INSERT INTO file_version (name, variant) VALUES (?, ?)";
  private static final String INSERT_FILE_RELATION = "INSERT INTO program_version_files (version_id, file_version_id) VALUES (%s, %s)";

  private ProgramDAO programDAO = new ProgramDAO();

  public FileVariant newFile(String programName, String pVersion, String fileName, String fileVersion) throws InvalidUpdateException {
    Program program = programDAO.getProgramByName(programName);

    if (program == null) {
      throw new InvalidUpdateException(12);
    }

    ProgramVersion programVersion = ProgramDAO.getProgramVersion(program.getVersions(), pVersion);

    if (programVersion == null) {
      throw new InvalidUpdateException(21, pVersion);
    }

    if (programVersion.getFiles().stream().anyMatch(e -> e.getName().equals(fileName))) {
      throw new InvalidUpdateException(33, fileName, fileVersion);
    }

    FileVariant fileVariant = insertFile(pVersion, fileName, fileVersion);
    insertFileRelation(programVersion.getId(), fileVariant.getId());
    return fileVariant;
  }

  private void insertFileRelation(int pVersionId, int fileId) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()) {
      int rows = stmt.executeUpdate(String.format(INSERT_FILE_RELATION, pVersionId, fileId));
      if (rows == 0) {
        throw new InvalidUpdateException(40);
      }
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }

  private FileVariant insertFile(String variant, String fileName, String fileVersion) throws InvalidUpdateException {
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(INSERT_FILE, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, fileName);
      stmt.setString(2, fileVersion);
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      if (!rs.next())
        throw new InvalidUpdateException(33, fileName, fileVersion);
      int id = rs.getInt(1);
      return new FileVariant(id, fileName, variant);
    } catch (SQLException e) {
      throw new InvalidUpdateException(e);
    }
  }
}
