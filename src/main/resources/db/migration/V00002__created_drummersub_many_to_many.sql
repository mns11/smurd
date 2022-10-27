-- Add PRIMARY KEY FOR tg_user
ALTER TABLE tg_user ADD PRIMARY KEY (chat_id);

-- Ensure that the tables with these names are removed before creating a new one.
DROP TABLE IF EXISTS drummer_sub;
DROP TABLE IF EXISTS drummer_x_user;

CREATE TABLE drummer_sub (
   id INT,
   name VARCHAR(100),
   last_release_id INT,
   PRIMARY KEY (id)
);

CREATE TABLE drummer_x_user (
   drummer_sub_id INT NOT NULL,
   user_id VARCHAR(100) NOT NULL,
   FOREIGN KEY (user_id) REFERENCES tg_user(chat_id),
   FOREIGN KEY (drummer_sub_id) REFERENCES drummer_sub(id),
   UNIQUE(user_id, drummer_sub_id)
);