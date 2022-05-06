INSERT INTO GENRE (ID,LIBELLE) VALUES ('1','Action');
INSERT INTO GENRE (ID,LIBELLE) VALUES ('2','Comédie');
INSERT INTO GENRE (ID,LIBELLE) VALUES ('3','Anime');
INSERT INTO GENRE (ID,LIBELLE) VALUES ('4','Horreur');

INSERT INTO STATUT_VISIONNAGE (ID,LIBELLE) VALUES ('1','A voir');
INSERT INTO STATUT_VISIONNAGE (ID,LIBELLE) VALUES ('2','En cours');
INSERT INTO STATUT_VISIONNAGE (ID,LIBELLE) VALUES ('3','Vu');

INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('1','film','Shazam!',3,4,null,null,166);
INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('2','film','Scream 2',2,null,null,null,141);
INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('3','serie','friends',1,null,null,null,null);
INSERT INTO OEUVRE (ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('4','serie','bioman',3,0,null,null,null);

INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (1,1);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (1,2);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (2,1);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (2,4);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (3,2);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (4,3);
INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES (4,1);

INSERT INTO SAISON(ID, NUMERO, NOTE, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (1,'S1',1,2,3);
INSERT INTO SAISON(ID, NUMERO, NOTE, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (2,'S2',1,3,3);
INSERT INTO SAISON(ID, NUMERO, NOTE, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (3,'S1',0,3,4);
INSERT INTO SAISON(ID, NUMERO, NOTE, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (4,'S2',1,3,4);
INSERT INTO SAISON(ID, NUMERO, NOTE, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES (5,'S3',0,3,4);

--select * from oeuvre as o
--    left join saison as s on s.oeuvre_id = o.id;
