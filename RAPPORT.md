Rapport
=======

Choix de conception
-------------------

Notre projet suit l'architecture MVC, ainsi que les règles de conception GRASP.
On retrouve en effet les packages suivant :
- `model` avec les classes `Rules`, `Player`, `Grid` et `Checker`
- `view` avec les classes `Connect4Window`, `MainWindow`, `RessourceLoader` et
  `SettingsWindow`
- `controller` avec la classe `Game`

Variantes
---------

### 5-in-a-Row

Nous avons décidé de mettre en place cette variante à l'aide d'une nouvelle
fonction dans `Rules` permettant la modification du nombre de colonnes de la
grille et du nombre de jetons alignés nécessaires. Le chargement d'une nouvelle
image contenant une grille de 9 par 6 est également nécessaire.

### Autres variantes

Nous n'avons malheureusement pas pu coder les variantes `Pop Out`, `Pop 10` et
`Power Up`, faute de temps. Cependant leur conception est disponible dans leur
diagramme de classe de conception respectif.
