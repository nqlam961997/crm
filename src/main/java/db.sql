CREATE
DATABASE IF NOT EXISTS crm_app;

USE
crm_app;

CREATE TABLE IF NOT EXISTS roles
(
    id
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    name
    VARCHAR
(
    50
) NOT NULL,
    description VARCHAR
(
    100
),
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS users
(
    id
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    email
    VARCHAR
(
    100
) NOT NULL,
    password VARCHAR
(
    100
) NOT NULL,
    fullname VARCHAR
(
    100
) NOT NULL,
    avatar VARCHAR
(
    100
),
    role_id INT NOT NULL,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS status
(
    id
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    name
    VARCHAR
(
    50
) NOT NULL,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS jobs
(
    id
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    name
    VARCHAR
(
    50
) NOT NULL,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS tasks
(
    id
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    name
    VARCHAR
(
    50
) NOT NULL,
    start_date DATE,
    end_date DATE,
    user_id INT NOT NULL,
    job_id INT NOT NULL,
    status_id INT NOT NULL,
    PRIMARY KEY
(
    id
)
    );


ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (user_id) REFERENCES users (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (job_id) REFERENCES jobs (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (status_id) REFERENCES status (id)  ON DELETE CASCADE;

INSERT INTO roles( name, description ) VALUES ("ROLE_ADMIN", "Quản trị hệ thống");
INSERT INTO roles( name, description ) VALUES ("ROLE_MANAGER", "Quản lí");
INSERT INTO roles( name, description ) VALUES ("ROLE_USER", "Nhân viên");

INSERT INTO status( name ) VALUES ("Chưa hoàn thành");
INSERT INTO status( name ) VALUES ("Đang thực hiện");
INSERT INTO status( name ) VALUES ("Đã hoàn thành");

INSERT INTO jobs(name, start_date, end_date)
VALUES('text1', '2022-08-02', '2022-09-02');

INSERT INTO jobs(name, start_date, end_date)
VALUES('text2', '2022-08-02', '2022-09-04');

INSERT INTO jobs(name, start_date, end_date)
VALUES('text3', '2022-08-02', null);

INSERT INTO users(email, password, fullname, avatar, role_id)
VALUES('text1@gmail.com', 'password', 'text1', 'https://picsum.photos/200/300', 1);

INSERT INTO users(email, password, fullname, avatar, role_id)
VALUES('text2@gmail.com', 'password', 'text2', 'https://picsum.photos/200/300', 2);

INSERT INTO users(email, password, fullname, avatar, role_id)
VALUES('text3@gmail.com', 'password', 'text3', 'https://picsum.photos/200/300', 3);

INSERT INTO tasks(name, start_date, end_date, user_id, job_id, status_id)
VALUES('task1', '2022-08-02 00:00:01', '2022-09-04 07:12:04', 1, 2, 3);

INSERT INTO tasks(name, start_date, end_date, user_id, job_id, status_id)
VALUES('task2', '2022-08-02 00:00:01', '2022-09-04 07:12:04', 1, 2, 2);

INSERT INTO tasks(name, start_date, end_date, user_id, job_id, status_id)
VALUES('task3', '2022-08-02 00:00:01', '2022-09-04 07:12:04', 3, 1, 1);
