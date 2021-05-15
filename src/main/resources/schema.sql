CREATE TABLE koala
(
   koala_id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   sex INT(1) NOT NULL,
   birthdate DATE,
   is_alive INT(1) NOT NULL,
　　deathdate DATE,
   zoo VARCHAR(50) NOT NULL,
   mother VARCHAR(50) NOT NULL,
   father VARCHAR(50) NOT NULL,
   details VARCHAR(300) NOT NULL,
   feature VARCHAR(50) NOT NULL,
   PRIMARY KEY(koala_id)
);
CREATE TABLE koalaimage
(
   koalaimage_id INT NOT NULL AUTO_INCREMENT,
   koala_id INT NOT NULL,
   PRIMARY KEY(koalaImage_id)
);