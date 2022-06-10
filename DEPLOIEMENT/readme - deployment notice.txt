Pre requis:
- java 8
- maven installé
- git installe
- postgres installe avec pgadmin4
    2 bdd:
        track_movies
        track_movies_dev
    1 user bdd:
        login: userjava
        mdp: userjava
    tous les droits pour ce user sur les 2 BDD

NOTE: ce repertoire deploiement n'a rien a faire dans le github. Nous l'avons mis là dans le cadre du projet pour
simplifier et regrouper les informations en un seul endroit. Normalement, ces fichiers et descriptions d'arborescence seraient
dans une doc d'installation a destination des equipes de prod

Les étapes pour déployer le projet:
1) recuperer le projet dans git
- lancer une invite de commande windows (cmd)
- cloner le projet git dans un répertoire
	exemple: dans c:\Projet_Epita\
	lancer la commande:
		git clone https://github.com/fredepita/trackmoviesback.git

2) preparer le repertoire de deploiement
    - prendre celui disponible dans le repertoire cloné et qui contient les scripts et properties
        c:\Projet_Epita\trackmoviesback\DEPLOIEMENT
    - ou en creer un nouveau
        - preparer un repertoire de deploiement dans lequel on crée les sous répertoires suivant: bin, jet, log et properties
            exemple:
                c:\intall_trackmovie_back\bin
                c:\intall_trackmovie_back\jar
                c:\intall_trackmovie_back\log
                c:\intall_trackmovie_back\properties
        - copier dans bin le fichier start_local.bat fournit dans c:\Projet_Epita\DEPLOIEMENT\bin
        - copier dans properties les fichier fournit dans c:\Projet_Epita\DEPLOIEMENT\properties:
            application-prod.properties
            logback-spring.xml
3) creer le jar
    - se positionner dans le répertoire du projet trackmoviesback que l'on vient de cloner et où se trouve le pom.xml
        exemple: c:\Projet_Epita\trackmoviesback
    - lancer la commande de packaging
        mvn clean install -P prod -DskipTests
4) deployer le jar
    - recuperer le fichier trackmoviesback-0.0.1-SNAPSHOT.jar qui se trouve dans le repertoire target:
	    exemple: C:\tmp\trackmoviesback\target\trackmoviesback-0.0.1-SNAPSHOT.jar

    - le copier dans le repertoire jar du repertoire de deploiement:
        exemple dans : c:\intall_trackmovie_back\jar

5) lancer l'application back
    - ouvrir une invite de commande windows, se positionner dans le répertoire bin:
        c:\intall_trackmovie_back\bin

        et executer le .bat fournit: start_local.bat

6) pour initialiser la base, executer les scripts présent dans le repertoire DEPLOIEMENT\initDB du projet
    0 - import static data.sql   -> crée les données de référence statiques
    1 - import users.sql  -> crée des users exemples
    2 - import film serie for users.sql  -> crée film et serie exemple rattaché aux user

    Le script "export film - serie from db.sql" est là si jamais on veut regénérer le script "2 - import film serie for users.sql"
    à partir de données présentes dans la base.
    A noter que si un user a été ajouté dans la BDD avec de nouvelles oeuvres, il faut ajouter à la main
    dans le script "1 - import users.sql" le nouvel utilisateur pour etre cohérent avec le script "2 - import film serie for users.sql"


