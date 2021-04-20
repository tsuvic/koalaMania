CREATE TABLE coara
(
   coara_id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   is_male INT(1) NOT NULL,
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