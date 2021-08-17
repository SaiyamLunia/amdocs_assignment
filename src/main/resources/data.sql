DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  status VARCHAR(15) NOT NULL
);

INSERT INTO USER (username, password, status) VALUES
  ('Jack', 'password1', 'Activated'),
  ('Bill', 'password2', 'Deactivated'),
  ('Jonny', 'password3', 'Activated');