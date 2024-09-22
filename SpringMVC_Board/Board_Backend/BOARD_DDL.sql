drop schema bbs;
create schema bbs;
use bbs;

-- 사용자 정보 테이블
drop table if exists member;
create table member
(
    mno         bigint primary key auto_increment,
    id          varchar(50)  not null unique,
    password    varchar(100) not null,
    name        varchar(30)  not null,
    email       varchar(50),
    status      varchar(1) default 'y' check (status in ('y', 'n')),
    create_date datetime  default now(),
    modify_date datetime  default now()
);

CREATE INDEX idx_member_id ON member (id);

insert into member values(default, 'test1', '1212', '홍길동', 'test1@email.com', 'y', default, default);
insert into member values(default, 'test2', '1212', '박길동', 'test2@email.com', 'y', default, default);
insert into member values(default, 'admin', '1212', '이길동', 'amdin@email.com', 'y', default, default);
insert into member values(default, 'admin2', '1212', '최길동', 'admin@email.com', 'y', default, default);
commit;

update member set password = '$2a$10$Vh2CxL8dZ9tJUyWe0VD4yufQpcqeHcBP8hqUCui8QQ1FAJRT8iTh.' where id = 'test1';
update member set password = '$2a$10$Vh2CxL8dZ9tJUyWe0VD4yufQpcqeHcBP8hqUCui8QQ1FAJRT8iTh.' where id = 'test2';
update member set password = '$2a$10$Vh2CxL8dZ9tJUyWe0VD4yufQpcqeHcBP8hqUCui8QQ1FAJRT8iTh.' where id = 'admin';
update member set password = '$2a$10$Vh2CxL8dZ9tJUyWe0VD4yufQpcqeHcBP8hqUCui8QQ1FAJRT8iTh.' where id = 'admin2';

commit;

select * from member;


-- 사용자 권한 테이블
drop table if exists member_auth;
create table member_auth
(
    id varchar(50) not null,       -- 사용자 id
    authority char(50) not null, -- 권한 문자열 role_admin, role_manager, role_member,
    primary key (id, authority),   -- 복합키
    constraint fk_authorities_users foreign key (id) references member (id)
);

-- 사용자 권한 추가
insert into member_auth(id, authority)
values ('test1', 'role_member'),
       ('test2', 'role_member'),
       ('test1', 'role_manager'),
       ('admin', 'role_member'),
       ('admin', 'role_manager'),
       ('admin', 'role_admin'),
       ('admin2', 'role_member'),
       ('admin2', 'role_manager'),
       ('admin2', 'role_admin');

select * from member_auth order by authority;


-- 게시판 관련 시작
drop table if exists board_category;
create table board_category
(
    type varchar(20),
    name varchar(100),
    level int,
    order_no int,
    constraint pk_board_category primary key(type)
);

-- 일반적으로 code는 약어를 사용하나 가독성을 위해 길게 표현함 notice -> n,  cmm1 -> c1
-- notice, plain, cmm1, cmm2, cmm3, question, tip, buy, sell
insert into board_category (type, name, level, order_no) values('notice', '공지', 0, 1);
insert into board_category (type, name, level, order_no) values('plain', '일반글', 3, 2);
insert into board_category (type, name, level, order_no) values('question', '질문', 3, 3);
insert into board_category (type, name, level, order_no) values('tip', 'tip', 3, 4);
insert into board_category (type, name, level, order_no) values('buy', '삽니다', 3, 5);
insert into board_category (type, name, level, order_no) values('sell', '팝니다', 3, 6);
insert into board_category (type, name, level, order_no) values('cmm1', '커뮤니티a', 3, 7);
insert into board_category (type, name, level, order_no) values('cmm2', '커뮤니티b', 3, 8);
insert into board_category (type, name, level, order_no) values('cmm3', '커뮤니티c', 3, 9);
insert into board_category (type, name, level, order_no) values('nboard', '공지사항', 3, 10);


-- 게시판 시작
drop table if exists board;
create table board
(
    bno         bigint auto_increment primary key,
    member_mno  bigint,
    board_category_type varchar(20),
    title       varchar(1000) not null,
    content     varchar(5000),
    read_count  int default 0,
    status      varchar(1) default 'y' check (status in ('y', 'n')),
    create_date    datetime default current_timestamp,
    modify_date  datetime default current_timestamp,
    constraint fk_board_writer foreign key(member_mno) references member(mno) on update cascade on delete cascade,
    constraint fk_board_category foreign key(board_category_type) references board_category(type) on update cascade on delete cascade
);

insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'notice','[공지] 클린한 게시판 환경을 만들어주세요.','깨끗한 게시판 환경 유지에 협조 바랍니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'plain','안녕하세요? 처음 가입한 개발자입니다.','잘 부탁드립니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'question','[질문] 질문 있습니다.','자바 어렵나요?');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'plain','오늘 식사메뉴 추천드립니다.','돈까스 드세요.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'plain','내일 식사메뉴 추천드립니다.','냉면 어떠신가요?');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'plain','모레 식사메뉴 추천드립니다.','스파게티 좋네요.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'plain','다음주 식사메뉴 추천드립니다.','아무거나 드세요');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.1','공지 내용입니다.1');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.2','공지 내용입니다.2');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.3','공지 내용입니다.3');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.4','공지 내용입니다.4');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.5','공지 내용입니다.5');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.6','공지 내용입니다.6');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.7','공지 내용입니다.7');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.8','공지 내용입니다.8');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.9','공지 내용입니다.9');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.10','공지 내용입니다.10');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.11','공지 내용입니다.11');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.12','공지 내용입니다.12');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.13','공지 내용입니다.13');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.14','공지 내용입니다.14');
insert into board (bno, member_mno, board_category_type, title, content) values(0,1,'nboard','공지사항 게시글 입니다.15','공지 내용입니다.15');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm1','커뮤니티 a글입니다. 1','커뮤니티 a활동 글입니다. 1');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm1','커뮤니티 a글입니다. 2','커뮤니티 a활동 글입니다. 2');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm1','커뮤니티 a글입니다. 3','커뮤니티 a활동 글입니다. 3');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm1','커뮤니티 a글입니다. 4','커뮤니티 a활동 글입니다. 4');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'cmm1','커뮤니티 a글입니다. 5','커뮤니티 a활동 글입니다. 5');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'cmm2','커뮤니티 b글입니다. 1','커뮤니티 b활동 글입니다. 1');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'cmm2','커뮤니티 b글입니다. 2','커뮤니티 b활동 글입니다. 2');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'cmm2','커뮤니티 b글입니다. 3','커뮤니티 b활동 글입니다. 3');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'cmm2','커뮤니티 b글입니다. 4','커뮤니티 b활동 글입니다. 4');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'cmm2','커뮤니티 b글입니다. 5','커뮤니티 b활동 글입니다. 5');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm3','커뮤니티 c글입니다. 1','커뮤니티 c활동 글입니다. 1');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm3','커뮤니티 c글입니다. 2','커뮤니티 c활동 글입니다. 2');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm3','커뮤니티 c글입니다. 3','커뮤니티 c활동 글입니다. 3');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm3','커뮤니티 c글입니다. 4','커뮤니티 c활동 글입니다. 4');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'cmm3','커뮤니티 c글입니다. 5','커뮤니티 c활동 글입니다. 5');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'tip','[팁] 팁글입니다.','java는 쉽습니다. 객체만 아세요.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'tip','[팁] 팁글입니다.','c언어는 어렵습니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'tip','[팁] 팁글입니다.','프로그래밍은 생각보다 쉽습니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'tip','[팁] 팁글입니다.','개발일은 어렵습니다. 코딩만 안합니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'tip','[팁] 팁글입니다.','문서작업은 귀찮습니다. 이게 개발일의 실체입니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 삼성 노트북 팔아요.','삼성 노트북 팝니다. 터치 됩니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 아이폰 팔아요.','아이폰15 팝니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 애플 맥북 노트북 팝니다.','m2 모델입니다. 맥북 게임용으로 잘써요');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'buy','[구매] 애플 노트북 삽니다.','맥북 게임용으로 삽니다. 이거 게임 잘돌아가나요?');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 삼성 노트북 삽니다','게임용으로 삽니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 애플 아이폰 삽니다','아이폰14사요. 30만원에 네고합니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'buy','[구매] 삼성 갤럭시 삽니다.','갤럭시 삽니다. 아무 기종이나 상관없어요.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 애플 노트북 팔아요.','노트북 맥북 최신입니다. 게임하시면 안됩니다');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 삼성 신형 갤럭시북 노트북 팔아요','새로나온 삼성 갤럭시북입니다. 이거 가성비 괜찮습니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 삼성 노트북 팔아요.','삼성 노트북 팝니다. 터치 됩니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 아이폰 팔아요.','아이폰15 팝니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 애플 맥북 노트북 팝니다.','m2 모델입니다. 맥북 게임용으로 잘써요');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 애플 노트북 삽니다.','맥북 게임용으로 삽니다. 이거 게임 잘돌아가나요?');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'buy','[구매] 삼성 노트북 삽니다','게임용으로 삽니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 애플 아이폰 삽니다','아이폰14사요. 30만원에 네고합니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 삼성 갤럭시 삽니다.','갤럭시 삽니다. 아무 기종이나 상관없어요.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 애플 노트북 팔아요.','노트북 맥북 최신입니다. 게임하시면 안됩니다');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 삼성 신형 갤럭시북 노트북 팔아요','새로나온 삼성 갤럭시북입니다. 이거 가성비 괜찮습니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 삼성 노트북 팔아요.','삼성 노트북 팝니다. 터치 됩니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 아이폰 팔아요.','아이폰15 팝니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 애플 맥북 노트북 팝니다.','m2 모델입니다. 맥북 게임용으로 잘써요');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 애플 노트북 삽니다.','맥북 게임용으로 삽니다. 이거 게임 잘돌아가나요?');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'buy','[구매] 삼성 노트북 삽니다','게임용으로 삽니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 애플 아이폰 삽니다','아이폰14사요. 30만원에 네고합니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'buy','[구매] 삼성 갤럭시 삽니다.','갤럭시 삽니다. 아무 기종이나 상관없어요.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 애플 노트북 팔아요.','노트북 맥북 최신입니다. 게임하시면 안됩니다');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 삼성 신형 갤럭시북 노트북 팔아요','새로나온 삼성 갤럭시북입니다. 이거 가성비 괜찮습니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 삼성 노트북 팔아요.','삼성 노트북 팝니다. 터치 됩니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'sell','[판매] 아이폰 팔아요.','아이폰15 팝니다.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 애플 맥북 노트북 팝니다.','m2 모델입니다. 맥북 게임용으로 잘써요');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 애플 노트북 삽니다.','맥북 게임용으로 삽니다. 이거 게임 잘돌아가나요?');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'buy','[구매] 삼성 노트북 삽니다','게임용으로 삽니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 애플 아이폰 삽니다','아이폰14사요. 30만원에 네고합니다. ');
insert into board (bno, member_mno, board_category_type, title, content) values(0,2,'buy','[구매] 삼성 갤럭시 삽니다.','갤럭시 삽니다. 아무 기종이나 상관없어요.');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 애플 노트북 팔아요.','노트북 맥북 최신입니다. 게임하시면 안됩니다');
insert into board (bno, member_mno, board_category_type, title, content) values(0,3,'sell','[판매] 삼성 신형 갤럭시북 노트북 팔아요','새로나온 삼성 갤럭시북입니다. 이거 가성비 괜찮습니다.');


select * from board;

drop table if exists board_attach_file;
create table board_attach_file
(
    fno               bigint auto_increment primary key,
    board_bno               bigint not null, -- 게시글 번호, fk
    original_filename varchar(200),
    renamed_filename  varchar(200),
    content_type      varchar(56),     -- content-type
    size              bigint,          -- 파일의 크기
    create_date       datetime default now(),
    constraint fk_board_bo foreign key (board_bno) references board (bno) on delete cascade
);

select * from board_attach_file;

insert into board_attach_file (fno, board_bno, original_filename, renamed_filename, create_date) values(0, 1, '첨부파일2','test_file2', default);
insert into board_attach_file (fno, board_bno, original_filename, renamed_filename, create_date) values(0, 2, '첨부파일','test_file', default);
insert into board_attach_file (fno, board_bno, original_filename, renamed_filename, create_date) values(0, 2, '첨부파일','test_file', default);
insert into board_attach_file (fno, board_bno, original_filename, renamed_filename, create_date) values(0, 3, '첨부파일','test_file', default);
insert into board_attach_file (fno, board_bno, original_filename, renamed_filename, create_date) values(0, 3, '첨부파일','test_file', default);
insert into board_attach_file (fno, board_bno, original_filename, renamed_filename, create_date) values(0, 3, '첨부파일','test_file', default);


-- reply 관련 테이블

create table board_reply
(
    rno         bigint primary key auto_increment,
    board_bno   bigint,
    member_mno  bigint,
    content     varchar(2000),
    status      varchar(1) default 'y' check (status in ('y', 'n')),
    create_date datetime   default current_timestamp,
    modify_date datetime   default current_timestamp,
    foreign key (board_bno) references board (bno) on delete cascade,
    foreign key (member_mno) references member (mno) on update cascade on delete cascade
);

insert into board_reply (rno, board_bno, member_mno, content) values(0, 1, 2, '안녕하세요.');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 1, 3, '댓글 테스트 입니다. 1');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 1, 3, '댓글 테스트 입니다. 2');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 1, 3, '댓글 테스트 입니다. 3');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 1, 3, '댓글 테스트 입니다. 4');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 2, 2, '안녕하세요.');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 2, 3, '반갑습니다.');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 3, 3, '안녕하세요.');
insert into board_reply (rno, board_bno, member_mno, content) values(0, 3, 2, '반갑습니다.');

commit;

select * from board_reply;