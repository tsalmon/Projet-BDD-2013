Ce projet se découpe en deux parties:


Serveur
====================


Client
====================
Cette partie traite de la facon dont sont relié et communiques les différentes classes du programme


Client
----------
Main du programme, contient les variables et fonctions primitives du programme, toute classe qui cherche a utiliser les elements de cette classe passe par une instance
Client génére un JFrame dans lequel on va successivement, au gré des pages, ajouter et enlever les JPanel
En partie Client ouvre la fenetre de connexion

Fenetre
--------------
Cette classe est uniquement appelé depuis Client pour générer le JFrame cité ci-haut

MenuConnexion
----------------

La page depuis laquelle un developpeur ou un simple client se connecte, avec son adresses e-mail, et son mot de passe, elle ouvre l'accès à la page d'accueil.

Accueil
---------------
Cette page fait la transition entre la plupart des pages, elle se découpe en 3 parties principales:
* Les informations du client
* Les applications recommandées
* Les meilleurs applications(mieux notés)

Si l'utilisateur est un developpeur, il a accès a un bouton spécial (Bienvenue), qui lui permet d'acceder a son profil de developpeur, pour les autres usagers, Bienvenue n'est qu'un simple label.

Developpeur
-----------------

Cette permet a un developpeur de voir les applications qu'il a ajouté, il peut egalement les mettre a jour, ou en ajouter d'autre en cliquant sur le bouton "nouvelle application"