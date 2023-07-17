CREATE DATABASE IF NOT EXISTS board;
USE board;

# USER 
CREATE TABLE IF NOT EXISTS `board`.`User` (
  `user_code` INT NOT NULL AUTO_INCREMENT COMMENT '회원번호',
  `user_email` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(255) NOT NULL COMMENT '회원비밀번호',
  `user_name` VARCHAR(6) NOT NULL COMMENT '회원이름',
  `jwtoken` TEXT NULL COMMENT '토큰',
  PRIMARY KEY (`user_code`, `user_email`),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC) VISIBLE,
  UNIQUE INDEX `user_code_UNIQUE` (`user_code` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = '회원정보';

# BOARD
CREATE TABLE IF NOT EXISTS `board`.`Board` (
  `board_number` INT NOT NULL AUTO_INCREMENT COMMENT '게시글 번호',
  `board_writer_email` VARCHAR(45) NOT NULL,
  `board_writer_name` VARCHAR(6) NOT NULL COMMENT '작성자이름',
  `board_title` VARCHAR(45) NOT NULL COMMENT '게시글제목',
  `board_content` TEXT NOT NULL COMMENT '게시글내용',
  `board_image_url` VARCHAR(511) NULL COMMENT '게시글 이미지',
  `board_write_datetime` DATE NOT NULL,
  `board_blocked` BOOLEAN NOT NULL,
  PRIMARY KEY (`board_number`))
ENGINE = InnoDB
COMMENT = '게시판';

CREATE VIEW board_view AS (
SELECT
B.board_number AS boardNumber,
U.user_email AS boardWriterEmail,
B.board_writer_name AS boardWriterName,
B.board_title AS title,
B.board_content AS boardContent,
B.board_image_url AS boardImageUrl,
B.board_write_datetime AS boardWriteDatetime
From board B, user U
WHERE B.board_writer_email = U.user_email
ORDER BY B.board_number DESC
);

INSERT INTO board(board_writer_email, board_writer_name, board_title, board_content, board_image_url,board_write_date_time, board_blocked) VALUES ('test1@gmail.com', '홍길동1', '제목','내용','aetsdtgd.jpg', DATE_FORMAT(now(), '%Y-%m-%d'), false);
INSERT INTO board(board_writer_email, board_writer_name, board_title, board_content, board_image_url,board_write_date_time, board_blocked) VALUES ('test2@gmail.com', '홍길동2', '제목2','내용2','aetsdtgd.jpg', DATE_FORMAT(now(), '%Y-%m-%d'), false);
INSERT INTO board(board_writer_email, board_writer_name, board_title, board_content, board_image_url,board_write_date_time, board_blocked) VALUES ('test3@gmail.com', '홍길동3', '제목3','내용3','aetsdtgd.jpg', DATE_FORMAT(now(), '%Y-%m-%d'), false);
INSERT INTO board(board_writer_email, board_writer_name, board_title, board_content, board_image_url,board_write_date_time, board_blocked) VALUES ('test3@gmail.com', '홍길동3', '제목3','내용3','aetsdtgd.jpg',  DATE_FORMAT(now(), '%Y-%m-%d'), false);
INSERT INTO User(user_email, user_password, user_name) values ('abc@gmail.com','1234','홍길동');
INSERT INTO User(user_email, user_password, user_name) values ('def@gmail.com','1234','홍길동');
INSERT INTO User(user_email, user_password, user_name) values ('ghi@gmail.com','1234','홍길동');
INSERT INTO User(user_email, user_password, user_name) values ('jkl@gmail.com','1234','홍길동');
INSERT INTO User(user_email, user_password, user_name) values ('mno@gmail.com','1234','홍길동');