# 🖥️ Client d’optimisation de tournées de véhicules

Ce dépôt contient la **partie cliente** d’une application **client–serveur** développée en **Java**.  
Le client permet à l’utilisateur de :

- **se connecter à un serveur distant** en saisissant une adresse IP et un port, afin d’établir une communication réseau fiable avec le serveur d’optimisation ;

- **sélectionner une région française et des villes** à partir de fichiers de données géographiques locaux, contenant notamment les coordonnées (latitude et longitude) des villes de chaque région ;

- **envoyer des requêtes d’optimisation de tournées** au serveur, en transmettant les paramètres sélectionnés sous forme de messages structurés, afin de calculer un itinéraire optimisé ;

- **recevoir et exploiter les résultats fournis par le serveur**, tels que l’ordre de passage des villes et la distance totale de la tournée, puis les présenter à l’utilisateur de manière lisible, éventuellement sous forme graphique.

La communication entre le client et le serveur repose sur un **protocole réseau TCP**, garantissant un échange fiable des données, et utilise le format **JSON** pour la sérialisation des messages et des données échangées.



## 📁 Structure du projet

L’ensemble du projet est contenu dans le répertoire **`src/`**, qui regroupe le code source Java, les bibliothèques externes nécessaires ainsi que les données géographiques utilisées par l’application. Il contient les répertoires: 

🔹 `libs/ :` Ce dossier contient les bibliothèques externes nécessaires à la manipulation des fichiers JSON.  
Il inclut les fichiers JAR de **Jackson** version 2.17.1 (`jackson-annotations`, `jackson-core`, `jackson-databind`). Ces bibliothèques doivent être ajoutées au **Build Path** du projet dans l’IDE afin de permettre la sérialisation et la désérialisation des données JSON.

🔹 `principale/ :` Ce package contient le **point d’entrée de l’application** (`Main.java`). Il est responsable de l’initialisation et du lancement du super-contrôleur qui gère l'application. Lui aussi contient les packages :
* `principale.controller :` Ce package regroupe les **contrôleurs**, conformément au patron de conception **MVC**. Ils assurent :
    - la gestion des événements utilisateur,
    - la coordination entre les vues et les modèles,
    - la communication avec le serveur distant,
    - la validation des données saisies.

* `principale.model :` Ce package contient les **classes métiers** représentant les données manipulées par l’application et son état, notamment :
    - les villes,
    - les régions,
    - les tournées.

        Les classes du modèle sont indépendantes de l’interface graphique et de la couche réseau.

* `principale.view :` Ce package regroupe les **interfaces graphiques** de l’application. La vue est responsable de l’affichage et des interactions utilisateur, sans contenir de logique métier.

🔹 `utils/ :` Ce package contient les **classes utilitaires** utilisées dans l’ensemble de l’application, par exemple :
- `Const :` qui contient quelques constantes de l'application,
- `StyleFactory :` qui permet d'instancuer un style de route en fonction d'une chaine de caractèez (autoroute, voie rapide, ...).
- `VilleParserFactory :` qui permet de charger un fichier de données d'une ville (seul le formet *json* est implémentée actuellement).

🔹 `villes/ :` Ce répertoire contient l’ensemble des **fichiers JSON** décrivant les données géographiques des villes françaises, organisées par région.  
Chaque fichier JSON contient notamment :
- le nom des villes,
- leur latitude,
- leur longitude.

Ces données sont chargées dynamiquement par le client pour construire les requêtes envoyées au serveur.


## 🧩 Architecture du projet

Le client repose sur une architecture **MVC (Model – View – Controller)** :

- **Model** : données métiers et structures logiques
- **View** : interface graphique et affichage
- **Controller** : gestion des événements, validation, communication réseau

Cette architecture permet une séparation claire des responsabilités, une maintenance facilitée et une évolution aisée du projet.

## ⚙️ Prérequis, installation et démarrage

* ### Prérequis
    * Inclure la bibliothèque **Jackson** au projet pour la manipulation des fichiers Json.
    * Git

* ### Installation
    * Cloner ce dépôt à l'aide de la commande `git clone`.
    * Inclure les bibliothèques Jackson présent dans `src/libs/` dans le Build PAth du projet.

* ### Démarrage 
    Pour lancer le projet, veuillez vous rassurer d'avoir préalablement exécuter **le serveur**. Une fois le serveur exécuté vous pouvez exécuter le fichier **Main.java**.
    