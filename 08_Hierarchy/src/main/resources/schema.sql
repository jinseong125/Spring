DROP DATABASE IF EXISTS db_hierarchy;
CREATE DATABASE IF NOT EXISTS db_hierarchy;
USE db_hierarchy;

DROP TABLE IF EXISTS tbl_bbs;
CREATE TABLE IF NOT EXISTS tbl_bbs (
	bbs_id      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	content     VARCHAR(100),
	state       INT DEFAULT 1,  # 0:삭제, 1:정상
  depth       INT,  # 0:게시글, 1:답글, 2:답답글, ...
  group_id    INT,  # 게시글과 게시글에 달린 모든 답글 모임
  group_order INT  # 게시글에 달린 답글의 정렬 순서
);