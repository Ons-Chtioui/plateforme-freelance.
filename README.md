# plateforme-freelance.


[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue.svg)](https://www.postgresql.org/)


> Plateforme moderne de mise en relation entre **Freelances** et **Entrepreneurs** développée avec Spring Boot, JPA/Hibernate et Thymeleaf.

---

##  Table des Matières

- [À Propos](#-à-propos)
- [Fonctionnalités](#-fonctionnalités)
- [Technologies](#-technologies)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Utilisation](#-utilisation)
- [Captures d'Écran](#-captures-décran)
- [API Documentation](#-api-documentation)
- [Contribution](#-contribution)


---

##  À Propos

**FreelanceHub** est une application web complète permettant aux freelances de trouver des missions correspondant à leurs compétences et aux entrepreneurs de recruter les meilleurs talents.

### Objectifs du Projet

- Simplifier la mise en relation freelances/entrepreneurs
- Système de matching intelligent basé sur les compétences
- Gestion complète du cycle de candidature
- Interface moderne et responsive

---

##  Fonctionnalités

### 👤 Pour les Freelances

-  **Création de profil** avec photo et CV
-  **Matching intelligent** : Missions correspondant à vos compétences
-  **Postulation facile** avec message de motivation
-  **Suivi des candidatures** avec badges colorés (En attente, Acceptée, Refusée)
-  **Gestion des compétences** : Ajout/suppression dynamique

### 💼 Pour les Entrepreneurs

-  **Création d'entreprise** obligatoire
-  **Publication de missions** avec sélection multiple de compétences
-  **Gestion des candidatures** : Consultation des profils complets
-  **Acceptation/Refus** des candidatures
-  **Téléchargement des CV** des candidats

### 🔐 Pour les Administrateurs

- **Dashboard statistiques** complètes
-  **Gestion des utilisateurs** (Freelances, Entrepreneurs)
-  **Modération des entreprises**
-  **Gestion des missions** (activation/désactivation)
-  **Gestion des compétences**

---

##  Technologies

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
  - Spring MVC
  - Spring Data JPA
  - Spring Web
- **Hibernate** (ORM)
- **PostgreSQL** / MySQL
- **Lombok** (réduction boilerplate)

### Frontend
- **Thymeleaf** (moteur de templates)
- **Tailwind CSS** (framework CSS)
- **Font Awesome** (icônes)
- **JavaScript** (interactivité)

### Outils
- **Maven** (gestion dépendances)
- **Git** (contrôle de version)

---

##  Architecture

### Structure MVC

```
┌─────────────┐
│  CONTROLLER │ ← Gère les requêtes HTTP
└──────┬──────┘
       │
┌──────▼──────┐
│   SERVICE   │ ← Logique métier
└──────┬──────┘
       │
┌──────▼──────┐
│ REPOSITORY  │ ← Accès aux données
└──────┬──────┘
       │
┌──────▼──────┐
│  DATABASE   │ ← PostgreSQL
└─────────────┘
```

### Schéma de Base de Données

<img width="1132" height="763" alt="diagramme de classe" src="https://github.com/user-attachments/assets/561cd9ed-ce3b-4bd7-9a49-bbe4d37cecc1" />



**Tables principales** :
- `utilisateur` : Comptes utilisateurs (Admin, Entrepreneur, Freelance)
- `profil` : Informations détaillées freelance (photo, CV, bio)
- `entreprise` : Informations entreprises
- `mission` : Offres de missions
- `competence` : Compétences techniques
- `candidature` : Postulations aux missions

---

##  Installation

### Prérequis

-  **Java JDK 17+** installé
-  **PostgreSQL 14+** installé et démarré
-  **Maven 3.6+** installé
-  **Git** installé

### Étapes d'installation

#### 1. Cloner le repository

```bash
git clone https://github.com/Ons-Chtioui/plateforme-freelance.git
cd plateforme-freelance
```

#### 2. Créer la base de données

```bash
# Se connecter à PostgreSQL
psql -U postgres

# Créer la base de données
CREATE DATABASE freelance_platform;

# Quitter psql
\q
```



#### 4. Configurer l'application

Éditez le fichier `src/main/resources/application.properties` :

```properties
# Base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/freelance_platform
spring.datasource.username=votre_username
spring.datasource.password=votre_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Upload fichiers
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

#### 5. Créer les dossiers d'upload

```bash
mkdir -p uploads/photos
mkdir -p uploads/cv
chmod -R 755 uploads
```

#### 6. Compiler et lancer

```bash
# Compiler
mvn clean install

# Lancer l'application
mvn spring-boot:run
```

#### 7. Accéder à l'application

Ouvrez votre navigateur : **http://localhost:8080**

---

## ⚙️ Configuration

### Fichier `application.properties`

```properties
# Port serveur (défaut: 8080)
server.port=8080

# Base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/freelance_platform
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Upload fichiers
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
spring.servlet.multipart.location=${java.io.tmpdir}

# Logging
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Variables d'environnement (Production)

```bash
# Base de données
export DB_URL=jdbc:postgresql://prod-server:5432/freelance_platform
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password


# Serveur
export SERVER_PORT=8080
```

---

## 🎮 Utilisation

### Comptes de Test

| Rôle | Email | Mot de passe |
|------|-------|--------------|
| **Admin** | admin@freelancehub.com | admin123 |
| **Freelance** | alice.martin@example.com | password123 |
| **Entrepreneur** | contact@techsolutions.com | password123 |

### Scénarios d'utilisation

####  Scénario 1 : Inscription Freelance

1. Allez sur `/register`
2. Remplissez le formulaire (email, password, nom, prénom)
3. Sélectionnez rôle **Freelance**
4. Validez → Compte créé + Profil automatique
5. Connectez-vous
6. Complétez votre profil (photo, CV, compétences)

####  Scénario 2 : Publication Mission (Entrepreneur)

1. Connectez-vous en tant qu'entrepreneur
2. **Première fois** : Créez votre entreprise obligatoirement
3. Cliquez "Nouvelle Mission"
4. Remplissez : titre, description, budget
5. Sélectionnez compétences requises (checkboxes multiples)
6. Publiez → Mission visible aux freelances

####  Scénario 3 : Postulation (Freelance)

1. Connectez-vous en tant que freelance
2. Menu "Missions" → Liste des missions **matchées** selon vos compétences
3. Cliquez sur une mission
4. Bouton "Postuler" (si pas déjà fait)
5. Ajoutez un message de motivation (optionnel)
6. Validez → Candidature envoyée

####  Scénario 4 : Gestion Candidatures (Entrepreneur)

1. Dashboard → Cliquez sur une de vos missions
2. "Voir les candidats"
3. Consultez profils complets :
   - Photo
   - Nom, email, téléphone
   - Biographie
   - Compétences
   - CV PDF téléchargeable
4. Boutons "Accepter" ou "Refuser"
5. Statut mis à jour → Freelance notifié

---

## 📸 Captures d'Écran

### Dashboard Freelance
![Dashboard Freelance](docs/screenshots/freelance-dashboard.png)
<img width="1912" height="947" alt="Capture d&#39;écran 2026-05-21 184704" src="https://github.com/user-attachments/assets/fea013ab-15bf-40ae-98e8-5e7eee79c8d7" />


### Liste Missions Matchées
<img width="1756" height="897" alt="Capture d&#39;écran 2026-05-21 184618" src="https://github.com/user-attachments/assets/283c4b6b-e308-4cd3-94f2-0e2445cfd6b1" />


### Gestion Candidatures Entrepreneur

<img width="1918" height="1000" alt="Capture d&#39;écran 2026-05-21 184520" src="https://github.com/user-attachments/assets/6e3d81df-4d8f-4082-83de-850c1e79c7fe" />
<img width="1907" height="992" alt="Capture d&#39;écran 2026-05-21 184459" src="https://github.com/user-attachments/assets/936ea7d3-00ec-4342-9920-bb2afccd5275" />

### Admin Panel

<img width="1905" height="937" alt="Capture d&#39;écran 2026-05-21 184253" src="https://github.com/user-attachments/assets/5e4ccba3-54f7-42f9-b51c-6c50c9c5adbd" />


---

## API Documentation

### Endpoints Principaux

#### Authentification
```
GET  /login              # Afficher formulaire connexion
POST /login              # Authentifier utilisateur
GET  /register           # Afficher formulaire inscription
POST /register           # Créer compte
GET  /logout             # Déconnexion
```

#### Freelance
```
GET  /freelance/dashboard                  # Dashboard
GET  /freelance/missions                   # Missions matchées
GET  /freelance/mission/{id}               # Détail mission
POST /freelance/postuler/{id}              # Postuler
GET  /freelance/mes-candidatures           # Mes candidatures
GET  /freelance/profil                     # Mon profil
POST /freelance/profil/upload-photo        # Upload photo
POST /freelance/profil/upload-cv           # Upload CV
```

#### Entrepreneur
```
GET  /entrepreneur/dashboard               # Dashboard
GET  /entrepreneur/creer-entreprise        # Formulaire entreprise
POST /entrepreneur/entreprise              # Créer entreprise
GET  /entrepreneur/missions/ajouter        # Formulaire mission
POST /entrepreneur/missions                # Publier mission
GET  /entrepreneur/mission/{id}/candidatures  # Candidatures mission
POST /entrepreneur/candidature/{id}/accepter  # Accepter candidature
POST /entrepreneur/candidature/{id}/refuser   # Refuser candidature
```

#### Admin
```
GET  /admin/dashboard                      # Dashboard admin
GET  /admin/utilisateurs                   # Tous les utilisateurs
GET  /admin/entreprises                    # Toutes les entreprises
GET  /admin/missions                       # Toutes les missions
GET  /admin/competences                    # Toutes les compétences
POST /admin/mission/{id}/desactiver        # Désactiver mission
POST /admin/utilisateur/{id}/supprimer     # Supprimer utilisateur
```

---

##  Structure du Projet

```
plateforme-freelance/
│
├── src/
│   ├── main/
│   │   ├── java/org/example/projet/
│   │   │   ├── Entites/                 # Entités JPA
│   │   │   │   ├── Utilisateur.java
│   │   │   │   ├── Profil.java
│   │   │   │   ├── Entreprise.java
│   │   │   │   ├── Mission.java
│   │   │   │   ├── Candidature.java
│   │   │   │   ├── Competence.java
│   │   │   │   └── RoleType.java
│   │   │   │
│   │   │   ├── Repository/              # Repositories JPA
│   │   │   │   ├── UtilisateurRepository.java
│   │   │   │   ├── ProfilRepository.java
│   │   │   │   ├── EntrepriseRepository.java
│   │   │   │   ├── MissionRepository.java
│   │   │   │   ├── CandidatureRepository.java
│   │   │   │   └── CompetenceRepository.java
│   │   │   │
│   │   │   ├── Services/                # Services métier
│   │   │   │   ├── IUserService.java
│   │   │   │   ├── UserServiceImpl.java
│   │   │   │   ├── IProfilService.java
│   │   │   │   ├── ProfilServiceImpl.java
│   │   │   │   ├── IMissionService.java
│   │   │   │   ├── MissionServiceImpl.java
│   │   │   │   ├── ICandidatureService.java
│   │   │   │   ├── CandidatureServiceImpl.java
│   │   │   │   ├── IEntrepriseService.java
│   │   │   │   ├── EntrepriseServiceImpl.java
│   │   │   │   ├── ICompetenceService.java
│   │   │   │   └── CompetenceServiceImpl.java
│   │   │   │
│   │   │   ├── Controleur/              # Controllers MVC
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── FreelanceController.java
│   │   │   │   ├── EntrepreneurController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   └── FileController.java
│   │   │   │
│   │   │   └── PlateformeFreelancesApplication.java
│   │   │
│   │   └── resources/
│   │       ├── templates/               # Templates Thymeleaf
│   │       │   ├── auth/
│   │       │   │   ├── login.html
│   │       │   │   └── register.html
│   │       │   ├── freelance/
│   │       │   ├── entrepreneur/
│   │       │   └── admin/
│   │       │
│   │       ├── static/                  # Ressources statiques
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── images/
│   │       │
│   │       └── application.properties
│   │
│   └── test/                            # Tests unitaires
│
├── uploads/                             # Fichiers uploadés (gitignored)
│   ├── photos/
│   └── cv/
│
├── docs/                                # Documentation
│   ├── screenshots/
│   └── documentation-complete.pdf
│
├── 
├── pom.xml                              # Configuration Maven
├── .gitignore
├── README.md
└── LICENSE
```

---

## Sécurité

### Important pour la Production

**Ce projet est une version éducative.** Pour une utilisation en production, implémentez :

1. **Hashage des mots de passe**
   ```java
   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }
   ```

2. **Spring Security**
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   ```

3. **Validation CSRF**
4. **HTTPS obligatoire**
5. **Gestion des sessions sécurisées**
6. **Limitation du taux de requêtes** (rate limiting)
7. **Validation stricte des entrées**

---

## Tests

### Lancer les tests

```bash
# Tests unitaires
mvn test

# Tests avec couverture
mvn clean test jacoco:report
```


---

##  Déploiement

### Déploiement sur Heroku

```bash
# Installer Heroku CLI
heroku login

# Créer l'application
heroku create nom-app-freelance

# Ajouter PostgreSQL
heroku addons:create heroku-postgresql:hobby-dev

# Déployer
git push heroku main

# Initialiser la BDD
heroku pg:psql < init-database.sql
```

### Déploiement sur AWS

```bash
# Créer EC2 instance
# Installer Java 17, PostgreSQL
# Cloner le projet
# Configurer application.properties
# Lancer avec mvn spring-boot:run
```

---

##  Contribution

Les contributions sont les bienvenues ! Voici comment contribuer :

1. **Forkez** le projet
2. **Créez** une branche (`git checkout -b feature/AmazingFeature`)
3. **Committez** vos changements (`git commit -m 'Add AmazingFeature'`)
4. **Pushez** vers la branche (`git push origin feature/AmazingFeature`)
5. **Ouvrez** une Pull Request

### Règles de contribution

- Code formaté selon les standards Java
- Commentaires en français
- Tests unitaires pour nouvelles fonctionnalités
- Documentation mise à jour

---

##  Roadmap

### Version 2.0 (Prochainement)

- [ ] Pagination des listes (missions, candidatures)
- [ ] Système de notifications en temps réel
- [ ] Chat entre freelances et entrepreneurs
- [ ] Système de reviews/notes
- [ ] Paiement intégré (Stripe)
- [ ] Recherche avancée avec filtres
- [ ] API REST complète
- [ ] Application mobile (React Native)

### Version 1.5

- [ ] Spring Security complet
- [ ] BCrypt pour mots de passe
- [ ] Système de récupération mot de passe
- [ ] Confirmation email inscription
- [ ] Export PDF des profils

---



##  Auteurs

- **Ons Chtioui Garbaa* - *Développement initial* - (https://github.com/Ons-Chtioui)

---

## 🙏 Remerciements

- [Spring Boot](https://spring.io/projects/spring-boot) pour le framework
- [Thymeleaf](https://www.thymeleaf.org/) pour le moteur de templates
- [Tailwind CSS](https://tailwindcss.com/) pour le design
- [Font Awesome](https://fontawesome.com/) pour les icônes

---

##  Contact

Pour toute question ou suggestion :

- 📧 Email: chtiouions1@gmail.com
- 💼 LinkedIn: https://www.linkedin.com/in/ons-chtioui-921736242/

---
  comment ajouter des image
