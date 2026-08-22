# TeamMatch AI - Backend (Project Management)

API backend du module de gestion de projets pour TeamMatch AI (Priorité 3).

## Fonctionnalités

- Créer un projet
- Mettre à jour un projet
- Supprimer un projet
- Récupérer un projet par ID
- Lister les projets

## Statuts de projet supportés

- DRAFT
- ACTIVE
- COMPLETED

## Exigences de personnalité et de disponibilité

Chaque projet inclut deux sections supplémentaires, utilisées par le microservice de recommandation IA (non implémenté ici — seules les données qu'il consomme le sont) :

**Personality Requirements** (scores 0-100, modèle Big Five) :
- extraversion, agreeableness, conscientiousness, neuroticism, openness

**Availability Requirements** :
- requiredFrom (date ISO, ex: `2026-09-01`)
- requiredUntil (date ISO, ex: `2026-12-31`, ne peut pas être antérieure à requiredFrom)

Ces champs sont obligatoires à la création/modification d'un projet et validés côté backend :
- chaque score de personnalité doit être compris entre 0 et 100 (`@Min`/`@Max`)
- `requiredFrom` et `requiredUntil` doivent être des dates valides (`@NotNull`)
- `requiredUntil` ne peut pas être antérieure à `requiredFrom` (contrainte personnalisée `@ValidDateRange`)

### Dataset pour le microservice IA

Le endpoint `GET /api/projects/{projectId}/ai-payload` renvoie le projet au format attendu par le microservice de recommandation, avec les traits de personnalité en clés capitalisées :

```json
{
  "projectId": "...",
  "title": "...",
  "requiredSkills": ["..."],
  "requiredExperience": "...",
  "requiredNumberOfTeamMembers": 3,
  "teamLeadRequirement": true,
  "personalityRequirements": {
    "Extraversion": 80,
    "Agreeableness": 70,
    "Conscientiousness": 90,
    "Neuroticism": 40,
    "Openness": 80
  },
  "availabilityRequirements": {
    "requiredFrom": "2026-09-01",
    "requiredUntil": "2026-12-31"
  }
}
```

## Stack technique

- Java 21
- Spring Boot 3
- Spring Data JPA + Flyway (migrations)
- PostgreSQL
- Maven, Lombok, Bean Validation

## Lancer le projet en local

### 1. Démarrer PostgreSQL

Le plus simple est d'utiliser Docker Compose (utilise les identifiants du fichier `.env`) :

```bash
docker compose up -d
```

Cela démarre une base PostgreSQL sur `localhost:5432` avec :
- base : `teammatch`
- utilisateur : `postgres`
- mot de passe : `postgres123`

### 2. Démarrer l'application

```bash
mvn spring-boot:run
```

Flyway crée automatiquement le schéma et insère les données de démonstration au premier démarrage (migrations V1 à V3). L'API écoute sur `http://localhost:8080`.

## Configuration

Les identifiants de connexion à PostgreSQL sont définis dans le fichier `.env` à la racine (utilisé par Docker Compose) et peuvent être surchargés via variables d'environnement pour Spring Boot :

```bash
export DB_URL="jdbc:postgresql://localhost:5432/teammatch"
export DB_USERNAME="postgres"
export DB_PASSWORD="postgres123"
```

> Le fichier `.env` est ignoré par Git (`.gitignore`) : évitez de committer de vrais secrets en production.

## CORS

Les origines autorisées par défaut sont `http://localhost:3000`, `http://localhost:5173`, `http://127.0.0.1:3000` et `http://127.0.0.1:5173` (voir `CorsConfig.java`). Surchargeable via la propriété `app.cors.allowed-origins`.

## Points d'API

- `POST /api/projects`
- `GET /api/projects`
- `GET /api/projects/{projectId}`
- `PUT /api/projects/{projectId}`
- `DELETE /api/projects/{projectId}`
- `GET /api/projects/{projectId}/ai-payload` — dataset formaté pour le microservice de recommandation IA

## Repo associé

Le frontend (React + TypeScript) vit dans un dépôt séparé : `teammatch-frontend`.
