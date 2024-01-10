# modelsis-backend-Ahmed-Tidiane-GUEYE

J'utilise la version 21 de Java dans ce projet suite à des soucis avec la version 11.

-> Ce projet consiste à créer une api de CRUD pour des produits et type de produit.

-> Stack utilisée:

 Java 21

 Spring Boot 3.2.1

 Spring Rest

 Spring Data

 JPA

 Maven

 Postgres 16

 Lombok

-> Execution:

1.Après avoir cloné le projet (Se placer au niveau d'un repertoire et taper git clone <nom_repository>)

2.Ouvrir le projet avec intelliJ (c'est ce que j'utilise) , laisser le système charger les dépendances.

3.Créer la base de données modelsis (user:postgres, mdp:new_password)
  
  a.Pour changer le mot de passe de l'utilisateur postgres:
  
  b.Ouvrir un terminal et se placer au niveau du repertoire de postgres (dans mon cas )
  
  c.taper la commande psql -U postgres pour se connecter en tant que superutilisateur
  
  d.tapez CREATE DATABASE modelsis;
  
  e.donner à l'utilisateur tous les privilèges sur la base de données modelsis en tapant 
  
  => GRANT ALL PRIVILEGES ON DATABASE modelsis TO postgres;

