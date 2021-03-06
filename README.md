# Calcul des cotisations Chèque Emploi Service (CES)

Le but de l'application est de calculer les montants à payer pour la
déclaration "Chèque Emploi Service (CES)" des gens maisons.

L'application permet de saisir les heures faites par la personne et 
calcul automatiquement le montant de la cotisation en fonction des différents taux à application 
et du montant horaire brut.

Sources du projet sur Github : https://github.com/gronono/cotisation-ces

## Étape 0

* Cloner le projet : `git clone git@github.com:gronono/cotisation-ces.git`
* L'importer dans IntelliJ
* Lancer une compilation maven : `./mvnw clean package`
* Lancer le programme CotisationCesApplication : `./mvnw spring-boot:run`
* Ouvrir la page http://localhost:8080
* Constater que le message 'Hello' s'affiche

## Étape 1

Implémenter l'algorithme de calcul du montant de la cotisation:
- salaire brut = salaire horaire brut * nb d'heures
- congés payés = 10% du salaire brut
- salaire brut total = salaire brut + congés payés
- retenue CSS = taux CSS * salaire brut total
- salaire net payé = salaire brut total - retenue CSS
- salaire horaire net = salaire net payé / nb d'heures

où 
- le salaire horaire brut et nb d'heures sont saisis par l'utilisateur
- taux CCS est 7,7725 %

En sortie, on veut à la fois le salaire net payé et le salaire horaire net.

Écrire un test unitaire pour vérifier que la méthode fonctionne correctement. 

Exemple :
Si le salaire horaire est salaire horaire = 915,42 F/H pour 8 heures travaillés, on a
* salaire brut = 915,42 F/H x 8 H = 7 323,4 F
* congés payés = 10 % x 7 323,4 F = 732 F
* salaire brut total = 7 323,4 F + 732 F = 8 055 F
* retenue CCS =7,7725% x 8 055 F = 626 F
* salaire net payé = 8 055 F - 626 F = 7 429 F
* salaire horaire net : 7 429 F / 8 h = 928,62 F

## Étape 2

Écrire une interface (IHM) permettant de saisir les deux données en entrée et d'afficher les deux résultats calculés

## Étape 3

Mettre en place un base de données pour persister les données. Le projet contient déjà le nécessaire pour utiliser une base de données de type H2.

On stockera les deux taux (celui des congés payés et celui de la retenu CCS) à appliquer dans la base.

Modifier le code pour lire les taux à partir de la base.

Dans les étapes suivants, les données saisies par l'utilisateur doivent être persistées.

Le driver de la base de données H2Database est déjà présent dans le classpath.
Vous pouvez l'utiliser pour créer une base de données mémoire :
```java
Connection conn = DriverManager.getConnection("jdbc:h2:mem:mydb", "sa", "");
```
Vous pouvez aussi accéder à la console H2 sur l'adresse `http://localhost:8080/h2-console`.

## Étape 4

On veut pouvoir gérer l'historique d'un employé de maison.

Modifier l'application pour 
- pouvoir déclarer un employé (nom, prénom, numéro CAFAT, salaire horaire brut),
- pouvoir saisir pour un mois donnée, le nombre d'heures effectuées par l'employé
- afficher l'historique d'un employé par mois (avec le salaire net payé et le salaire horaire net)

## Étape 5

Le salaire horaire brut d'un employé évolue en fonction du temps. Prévoir un mécanisme permettant :
- d'historiser le changement du montant horaire brut
- de récupérer le montant hoaire brut effectif à une date donnée.

Réaliser les impacts dans le code.

## Étape 6

Le taux CSS peut changer à la date du 1er janvier de chaque année.

Historiser ce taux et impacter les calculs pour utiliser le bon taux.
