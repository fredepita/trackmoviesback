Pre requis:
- java 8
- maven installé
- git installe

Les étapes pour installer le projet et packager
1) recuperer le projet dans git
- lancer une invite de commande windows (cmd)
- cloner le projet git dans un répertoire
	exemple: dans c:\Projet_Epita\
	lancer la commande:
		git clone https://github.com/fredepita/trackmoviesback.git

2) preparer le repertoire de deploiement
    - prendre celui disponible dans le repertoire cloné et qui contient les scripts et properties
        c:\Projet_Epita\DEPLOIEMENT
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