Ce projet se découpe en deux parties:


Serveur
====================


Client
====================
Cette partie traite de la facon dont sont relié et communiques les différentes classes du programme
A tout moment, un utilisateur peut acceder a l'accueil ou se déconnecter.

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

Cette permet a un developpeur de voir les applications qu'il a ajouté, il peut egalement les mettre a jour, ou en ajouter d'autre en cliquant sur le bouton "nouvelle application

Peripherique
-----------------
Apres avoir cliqué sur le bouton Voir périphérique dans l'accueil, l'utilisateur peut voir ses peripheriques, il peut a partir de cette page, voir les applications qui sont installer sur chacun d'entre eux, les supprimer, voir quelles autre applications il peut ajouter dessus. Il peut aussi voir les droits de ses applications et également les supprimer.

Application
-------------------
Cette page permet de voir les informations (comptatibilités, versions), les avis des usagers, on peut l'acheter, donné son avis (seulement si on l'a possede).

Achat
-----------
Apres avoir cliqué sur le bouton Acheter (ou gratuit dans le cas ou il n'y a rien a payé), on accede a cette page, ou on peut installer une application sur un certain peripherique, parmis ceux qui sont compatibles.

ToutVoir
------------

Cette page est a usage multiple: elle permet de lister les applications qui sont recommandées pour l'utilsateur connecté, de voir toutes les applications, tous les peripheriques, et tous les systemes d'exploitations, a partir de cette page, on peut acceder a la page d'une application ou d'un peripherique, pour l'obtenir. On accede a cette page en cliquant sur "Toute les applications" ou "Voir plus".

ModifierCompte
------------------
Pour modifier son adresse e-mail, son mot de passe et voir ses moyens de paiements.

Recherche
-----------
Accessible depuis n'importe quelle page, elle permet en passant par le fichier ClavierListener, de rechercher une application ou un peripherique en le recherchant par son nom ou par son tag

Droits
------------------
Pour voir les droits d'une application

ConnexionClient
---------------
Ce fichier permet de s'authentifier sur le serveur, d'envoyer des requetes et de recevoir les requetes, elle traite et renvoit les resultats dans un objet SqlData, ce fichier ne n'est pas accessible pour les usagers.

SqlData
-----------
Objet qui permet d'acceder au resultat d'une requete, ce fichier ne n'est pas accessible pour les usagers

