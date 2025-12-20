# 🚀 Space Invaders - Projet Design Patterns

> **Module** : Design Patterns
> **Année Universitaire** : 2025 - 2026
> **Langage** : Java (JavaFX)

---

## 👥 Membres du Groupe


* **Srasra Youssef GrpB**
* **Trabelsi Hadil GrpB**

---

## 📝 Description du Projet

Ce projet est une réimplémentation complète du jeu d'arcade **Space Invaders**. L'objectif principal est la mise en pratique de l'architecture logicielle orientée objet et l'utilisation pertinente de **Design Patterns** pour résoudre des problèmes de conception courants (gestion des états, hiérarchie d'objets, création d'entités et extension dynamique).

Le jeu propose une gestion fluide des niveaux, des bonus (PowerUps) via des décorateurs, et une structure d'objets hiérarchique.

---

## 🏗️ Architecture & Design Patterns

Le projet est structuré autour de **4 Design Patterns majeurs** situés dans le package `tn.client.space_invaders.patterns` :

### 1. State Pattern (Patron État)
* **Localisation :** `patterns.state`
* **Problème :** Gérer les différentes phases du jeu (Menu, En Jeu, Pause, Victoire, Game Over) sans utiliser de multiples conditions complexes (`if/else`).
* **Solution :** Le contexte (`Game`) délègue le comportement à un objet état actuel.
* **Implémentation :** Permet de passer fluidement de l'écran `MenuState` à `PlayState` ou `WinState`.

### 2. Composite Pattern (Patron Composite)
* **Localisation :** `patterns.composite` & `model.GameComponent`
* **Problème :** Traiter de manière uniforme les objets simples (un ennemi, un projectile) et les groupes d'objets (une escouade d'ennemis, le niveau entier).
* **Solution :** Utilisation de l'interface `GameComponent` (dans `model`) qui permet de manipuler une feuille ou un nœud composite de la même façon (ex: appeler `update()` ou `draw()` sur tout le niveau d'un coup).

### 3. Factory Pattern (Patron Fabrique)
* **Localisation :** `patterns.factory`
* **Problème :** Instancier des objets complexes (Ennemis, Projectiles, PowerUps) sans coupler le code client aux classes concrètes.
* **Solution :** Une classe Factory centrale gère la création des entités avec leurs configurations par défaut.

### 4. Decorator Pattern (Patron Décorateur)
* **Localisation :** `patterns.decorator`
* **Problème :** Ajouter dynamiquement des responsabilités ou des capacités au vaisseau du joueur (Bouclier, Tir Rapide, Vitesse) sans modifier la classe `Player` originale ni créer une explosion de sous-classes.
* **Solution :** Les PowerUps "enveloppent" l'entité joueur pour modifier son comportement (ex: changer la cadence de tir ou l'apparence) au moment de l'exécution.

---

## 📂 Structure du Projet

Voici l'arborescence exacte du code source :

```text
src/main/java/tn/client/space_invaders/
├── controller/
│   └── InputHandler.java       # Gestion des entrées clavier
├── core/
│   ├── Game.java               # Classe principale (Context du State Pattern)
│   └── GameConfig.java         # Singleton de configuration
├── main/
│   └── Main.java               # Point d'entrée de l'application
├── model/
│   ├── GameComponent.java      # Interface (Component du Composite)
│   ├── GameObject.java         # Classe de base
│   ├── Level.java              # Gestion des niveaux
│   ├── PowerUp.java            # Entité bonus
│   └── Projectile.java         # Gestion des tirs
├── patterns/
│   ├── composite/              # Implémentation Composite
│   ├── decorator/              # Implémentation Decorator (PowerUps)
│   ├── factory/                # Implémentation Factory (Création entités)
│   └── state/                  # Implémentation State (Menu, Play, Win...)
├── services/
│   ├── GameLogger.java         # Service de logs
│   └── SoundManager.java       # Gestionnaire de sons
└── view/
    ├── Assets.java             # Chargement des ressources
    ├── HUD.java                # Affichage Tête Haute (Score, Vies)
    └── SpaceBackground.java    # Gestion du fond animé
```
## 🎮 Commandes de Jeu

Les contrôles sont gérés par le module `InputHandler` et sont configurables via le Singleton `GameConfig`. Voici la configuration par défaut :

| Action | Touche (Défaut) |
| :--- | :--- |
| **Se Déplacer** | `←` (Gauche) / `→` (Droite) |
| **Tirer** | `Espace` |
| **Pause** | `Echap` (ESC) |
| **Valider / Démarrer** | `Entrée` |

---

## 🛠️ Installation et Lancement

1.  **Prérequis :**
    * **JDK 17** ou version supérieure.
    * **Maven** (pour la gestion des dépendances JavaFX).
    * Un IDE (IntelliJ IDEA recommandé ou Eclipse).

2.  **Cloner le dépôt :**
    ```bash
    git clone [https://github.com/VOTRE_USER/NOM_DU_PROJET.git](https://github.com/VOTRE_USER/NOM_DU_PROJET.git)
    ```

3.  **Compiler et Lancer :**
    * Ouvrez le projet dans votre IDE.
    * Laissez Maven télécharger les dépendances.
    * Localisez la classe principale : `src/main/java/tn/client/space_invaders/main/Main.java`.
    * Exécutez la méthode `main()`.

---
