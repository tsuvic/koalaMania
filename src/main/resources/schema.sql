CREATE TABLE coara
(
   coara_id INT NOT NULL AUTO_INCREMENT,
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
   PRIMARY KEY(coara_id)
);
CREATE TABLE coaraimage
(
   coaraimage_id INT NOT NULL AUTO_INCREMENT,
   coara_id INT NOT NULL,
   PRIMARY KEY(coaraImage_id)
);