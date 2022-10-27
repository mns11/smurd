ALTER TABLE drummer_x_user DROP CONSTRAINT drummer_x_user_user_id_fkey;
ALTER TABLE tg_user DROP CONSTRAINT tg_user_pkey;
ALTER TABLE tg_user ALTER COLUMN chat_id TYPE INT USING chat_id::integer;
ALTER TABLE drummer_x_user ALTER COLUMN user_id TYPE INT USING user_id::integer;
ALTER TABLE tg_user ADD PRIMARY KEY (chat_id);
ALTER TABLE drummer_x_user ADD FOREIGN KEY (user_id) REFERENCES tg_user(chat_id);