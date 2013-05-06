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

La page depuis laquelle un developpeur ou un simple clientse connecte, avec son adresses e-mail, et son mot de passe, elle ouvre l'accès à la page d'accueil.

Accueil
---------------
Cette page fait la transition entre la plupart des pages, elle se découpe en 3 parties principales:
*les informations du client
*les applications recommandées
*les meilleurs applications(mieux notés)
