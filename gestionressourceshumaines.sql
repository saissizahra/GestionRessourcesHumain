CREATE DATABASE gestionressourceshumaines;

USE gestionressourceshumaines;


CREATE TABLE employe (
  id INT(11) NOT NULL AUTO_INCREMENT,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  telephone VARCHAR(15) DEFAULT NULL,
  salaire DECIMAL(10, 2) NOT NULL,
  solde INT(11) NOT NULL,
  role ENUM('ADMIN', 'EMPLOYE') NOT NULL,
  poste ENUM('INGENIEURE_ETUDE_ET_DEVELOPPEMENT', 'TEAM_LEADER', 'PILOTE') NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE holiday (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_employe INT(11) NOT NULL,
  startdate DATE DEFAULT NULL,
  enddate DATE DEFAULT NULL,
  type ENUM('CONGE_PAYE', 'CONGE_NON_PAYE', 'CONGE_MALADIE') NOT NULL,
  PRIMARY KEY (id),
  KEY id_employe (id_employe),
  FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE CASCADE
);


CREATE TABLE login (
  id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO login (username, password) VALUES ('user', 'user');
