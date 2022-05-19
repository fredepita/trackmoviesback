INSERT INTO GENRE (ID,LIBELLE) VALUES ('1','Action');
INSERT INTO GENRE (ID,LIBELLE) VALUES ('2','Comédie');
INSERT INTO GENRE (ID,LIBELLE) VALUES ('3','Anime');
INSERT INTO GENRE (ID,LIBELLE) VALUES ('4','Horreur');

INSERT INTO STATUT_VISIONNAGE (ID,LIBELLE) VALUES ('1','A voir');
INSERT INTO STATUT_VISIONNAGE (ID,LIBELLE) VALUES ('2','En cours');
INSERT INTO STATUT_VISIONNAGE (ID,LIBELLE) VALUES ('3','Vu');

INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('1','film','Shazam!',3,4,'https://image.tmdb.org/t/p/w300_and_h450_bestv2/lhQbFsO6rFoUo3kv5X61G6koiR1.jpg',null,166);
INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('2','film','Scream 2',2,5,'https://image.tmdb.org/t/p/w300_and_h450_bestv2/iFarW5SLjyjuV7YSUUzTV34rINQ.jpg',null,141);
INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('3','serie','friends',1,4,'https://image.tmdb.org/t/p/w300_and_h450_bestv2/f496cm9enuEsZkSPzCwnTESEK5s.jpg',null,null);
INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('4','serie','bioman',3,0,'https://image.tmdb.org/t/p/w300_and_h450_bestv2/1EDUBJzF0FUbtTNphQZMN6XHqBu.jpg',null,null);

INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (1,1);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (1,2);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (2,1);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (2,4);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (3,2);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (4,3);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (4,1);

INSERT INTO SAISON(ID, NUMERO, NB_EPISODES, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (1,'S1',1,2,3);
INSERT INTO SAISON(ID, NUMERO, NB_EPISODES, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (2,'S2',1,3,3);
INSERT INTO SAISON(ID, NUMERO, NB_EPISODES, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (3,'S1',0,3,4);
INSERT INTO SAISON(ID, NUMERO, NB_EPISODES, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (4,'S2',1,3,4);
INSERT INTO SAISON(ID, NUMERO, NB_EPISODES, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (5,'S3',0,3,4);

--INSERT INTO UTILISATEUR(ID, IDENTIFIANT, MOT_DE_PASSE) VALUES (1001,'Id1','MDP1');
--INSERT INTO UTILISATEUR(ID, IDENTIFIANT, MOT_DE_PASSE) VALUES (1002,'Id2','MDP2');
--INSERT INTO UTILISATEUR(ID, IDENTIFIANT, MOT_DE_PASSE) VALUES (1003,'Id3','MDP3');

--INSERT INTO UTILISATEUR_ROLES(UTILISATEUR_ID, ROLES) VALUES (1001,'ROLE_ADMIN');
--INSERT INTO UTILISATEUR_ROLES(UTILISATEUR_ID, ROLES) VALUES (1002,'ROLE_USER');
--INSERT INTO UTILISATEUR_ROLES(UTILISATEUR_ID, ROLES) VALUES (1003,'ROLE_ADMIN');
--INSERT INTO UTILISATEUR_ROLES(UTILISATEUR_ID, ROLES) VALUES (1003,'ROLE_USER');

--
