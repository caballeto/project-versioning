CREATE DATABASE project_versioning;

USE project_versioning;

CREATE TABLE program (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100),
                         last_version VARCHAR(100)
);

CREATE TABLE program_version (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 version VARCHAR(100)
);

CREATE TABLE program_versions (
                                  program_id INT,
                                  version_id INT,
                                  FOREIGN KEY (program_id) REFERENCES program(id) ON DELETE CASCADE,
                                  FOREIGN KEY (version_id) REFERENCES program_version(id) ON DELETE CASCADE,
                                  PRIMARY KEY (program_id, version_id)
);

CREATE TABLE file_version (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(100),
                              variant VARCHAR(100)
);

CREATE TABLE program_version_files (
                                       version_id INT,
                                       file_version_id INT,
                                       FOREIGN KEY (version_id) REFERENCES program_version(id) ON DELETE CASCADE,
                                       FOREIGN KEY (file_version_id) REFERENCES file_version(id) ON DELETE CASCADE,
                                       PRIMARY KEY (version_id, file_version_id)
);

CREATE TABLE usages (
                        file_id1 INT,
                        file_id2 INT,
                        FOREIGN KEY (file_id1) REFERENCES file_version(id) ON DELETE CASCADE,
                        FOREIGN KEY (file_id2) REFERENCES file_version(id) ON DELETE CASCADE
);