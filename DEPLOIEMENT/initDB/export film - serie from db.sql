SET client_encoding = 'WIN1252';
copy
(
	select sql_txt from (
		SELECT 'DELETE FROM SAISON;' as sql_txt,0,-1
		UNION
		SELECT 'DELETE FROM OEUVRE_GENRE;' as sql_txt,1,-1
		UNION
		SELECT 'DELETE FROM OEUVRE;' as sql_txt,2,-1
		UNION
		SELECT concat('INSERT INTO OEUVRE (UTILISATEUR_ID,TYPE_OEUVRE,TITRE,STATUT_VISIONNAGE_ID,NOTE,CREATEURS,ACTEURS,URL_AFFICHE,URL_BANDE_ANNONCE,DUREE) VALUES ('
					  ,UTILISATEUR_ID,','
					  ,'''',TYPE_OEUVRE,''','
					  ,'''',TITRE,''','				  
					  ,'',COALESCE(CAST(STATUT_VISIONNAGE_ID AS TEXT),'null'),','
					  ,'',COALESCE(CAST(NOTE AS TEXT),'null'),','				  
					  ,'''',COALESCE(CREATEURS,null),''','
					  ,'''',COALESCE(ACTEURS,null),''','				  
					  ,'''',COALESCE(URL_AFFICHE,null),''','				  
					  ,'''',COALESCE(URL_BANDE_ANNONCE,null),''','				  
					  ,'',COALESCE(CAST(DUREE AS TEXT),'null'),''
				,');') as sql_txt, 3 , oeuvre.id
		FROM oeuvre 
		UNION
		SELECT concat('INSERT INTO OEUVRE_GENRE(OEUVRE_ID, GENRE_ID) VALUES ('
		,COALESCE(CAST(OEUVRE_ID AS TEXT),'null'),','				  
		,COALESCE(CAST(GENRE_ID AS TEXT),'null'),''	
		,');') as sql_txt,4 , oeuvre_genre.oeuvre_id
		FROM OEUVRE_GENRE
		UNION
		SELECT concat('INSERT INTO SAISON(NUMERO, NB_EPISODES, STATUT_VISIONNAGE_ID,OEUVRE_ID) VALUES ('
		,'''',NUMERO,''','	
		,'',COALESCE(CAST(NB_EPISODES AS TEXT),'null'),','				  
		,'',COALESCE(CAST(STATUT_VISIONNAGE_ID AS TEXT),'null'),','
		,COALESCE(CAST(OEUVRE_ID AS TEXT),'null'),''				  
		,');') as sql_txt, 5 , saison.id
		FROM SAISON
		order by 2,3
	) t 
)
to 'c:\temp\init_db_oeuvre.sql' ;