Puissance 4
===========

Instructions
------------

### Installation

1. Télécharger l'archive `connect4.jar`.
2. Dans une fenêtre de terminal, entrer la commande `java -jar connect4.jar`
   dans le répertoire où l'archive JAR est située.

(La version 8 du JRE ou supérieure est recommandée.)

Rapport
-------

### Choix de conception
Notre projet suit l'architecture des patrons GRASP. En effet, on y retrouve l'architecture MVC comportant :
- model avec les classes `Rules`, `Player`, `Grid` et `Checker`
- view avec les classes `Connect4Window`, `MainWindow`, `RessourceLoader` et `SettingsWindow`
- controller avec la classe `Game`


### Variantes

#### 5-in-a-row

Nous avons décidé de mettre en place cette variante à l'aide d'une nouvelle
fonction dans `Rules` permettant la modification du nombre de colonnes de la
grille et du nombre de jetons alignés nécessaires. Le chargement d'une nouvelle
image contenant une grille de 9 par 6 est également nécessaire.

#### Autres variantes
Nous n'avons malheureusement pas pu coder les variantes Pop 10 et Power par faute de temps. Cependant leur conception est disponible dans leur  diagramme de classe de conception.
