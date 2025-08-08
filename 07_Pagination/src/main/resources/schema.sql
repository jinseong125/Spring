DROP DATABASE IF EXISTS db_pagination;
CREATE DATABASE IF NOT EXISTS db_pagination;
USE db_pagination;

DROP TABLE IF EXISTS tbl_user;
CREATE TABLE tbl_bbs (
  bbs_id INT AUTO_INCREMENT PRIMARY KEY,
  content TEXT NOT NULL,
  state INT DEFAULT 1,
  depth INT DEFAULT 0,
  group_id INT,
  group_order INT
);
