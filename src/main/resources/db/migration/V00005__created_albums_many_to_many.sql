CREATE TABLE IF NOT EXISTS albums
(
   id INT NOT NULL,
   title VARCHAR(300) NOT NULL,
   release_date DATE,
   in_collection BOOLEAN NOT NULL,
   deluxe BOOLEAN,
   remaster BOOLEAN,
   format VARCHAR(46),
   music INT,
   artist VARCHAR(45) NOT NULL,
   master_release BOOLEAN DEFAULT TRUE,
   PRIMARY KEY (id),
   UNIQUE(music)
);

CREATE TABLE IF NOT EXISTS drummers_to_album (
   drummer_id INT NOT NULL,
   album_id INT NOT NULL,
   FOREIGN KEY (album_id) REFERENCES albums(id),
   FOREIGN KEY (drummer_id) REFERENCES drummers(id),
   UNIQUE(album_id, drummer_id)
);