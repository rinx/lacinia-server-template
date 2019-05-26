CREATE TABLE artist (id SERIAL PRIMARY KEY, name varchar(50) NOT NULL);
CREATE TABLE album (id SERIAL PRIMARY KEY, artist_id INTEGER NOT NULL, name VARCHAR(50) NOT NULL);
CREATE TABLE song (id SERIAL PRIMARY KEY, artist_id INTEGER NOT NULL, album_id INTEGER, name VARCHAR(50) NOT NULL, length INTEGER NOT NULL);

INSERT INTO artist(name) VALUES ('miki imai'), ('maco'), ('takako matsu');
INSERT INTO album(artist_id, name) VALUES (1, 'elfin'), (1, 'corridor'), (2, 'best love maco');
INSERT INTO song(artist_id, album_id, name, length) VALUES (1, 1, 'elfin', 204), (1, 1, 'yasei no kaze', 312), (1, 2, 'inori', 284);
INSERT INTO song(artist_id, album_id, name, length) VALUES (2, 3, 'however', 335);
INSERT INTO song(artist_id, name, length) VALUES (3, 'ashita haru ga kitara', 261);
