# TargetCart-AI — Agent Instructions

## Project Overview

Asynchronous abandoned-cart recovery and personalized e-commerce marketing engine. Built as a learning project for transitioning from Spring Boot to Spring AI development.

## Tech Stack

| Layer        | Technology                                          |
|------------- |-----------------------------------------------------|
| Backend      | Java 21, Spring Boot 3.x, Spring Data JPA, Spring AI ChatClient |
| Frontend     | React.js, Vite, Tailwind CSS                        |
| Database     | MySQL                                               |
| Build        | Maven (backend), npm (frontend)                     |
| Infra        | Docker, Docker Compose                              |
| Deploy       | Render                                              |
| VCS          | GitHub                                              |

## Architecture

- **Modular feature-based layout** — each feature owns its own controller, service, repository, DTO, and entity files. No monolithic service classes.
- Frontend, backend, database, documentation, and infrastructure must stay clearly separated.
- Spring AI is added **only after** the core Spring Boot business workflow is working.
- React connects to real backend APIs; mock data is temporary only.

## Development Rules

- **Phased build**: repo foundation → Spring Boot business system → Spring AI integration → React frontend → Dockerize → deploy.
- Prefer **additive changes** over destructive rewrites.
- Inspect existing files before modifying.
- Never commit secrets, API keys, or credentials.
- Never work directly on `main`; use feature branches.
- Do not commit or push unless explicitly requested.
- Run build/tests after meaningful changes.
- Do not generate fake business functionality unless explicitly asked.
- Keep the project explainable to a fresher for technical interviews.

## Commands

Commands will be added as the project scaffolding is created. Typical pattern:

```bash
# Backend
cd backend && mvn spring-boot:run
cd backend && mvn test
cd backend && mvn package

# Frontend
cd frontend && npm install
cd frontend && npm run dev
cd frontend && npm run build
cd frontend && npm run lint

# Docker
docker compose up --build
docker compose down
```

## Project Structure (target)

```
targetcart-ai/
├── backend/          # Spring Boot application
│   ├── src/main/java/com/targetcart/
│   │   ├── config/
│   │   └── modules/
│   │       └── <feature>/
│   │           ├── controller/
│   │           ├── service/
│   │           ├── repository/
│   │           ├── dto/
│   │           └── entity/
│   └── src/main/resources/
├── frontend/         # React + Vite + Tailwind
├── docs/             # Project documentation
├── docker-compose.yml
├── Dockerfile        # (or per-service Dockerfiles)
├── pom.xml
└── AGENTS.md
```

## Key Conventions

- **No giant files** — keep classes focused; split when a file exceeds ~300 lines.
- **Feature isolation** — avoid cross-feature dependencies unless a shared utility is clearly warranted.
- **Shared config is minimal** — don't touch shared configs without good reason.
- **Test what you build** — write tests alongside features, not as an afterthought.
- **MySQL locally** — run via Docker Compose, not a local install.

## Agent Gotchas

- There is no production Spring Boot starter yet — this repo is greenfield. Build incrementally, do not generate the entire application at once.
- The backend uses **Java 21** — use modern features (records, sealed classes, pattern matching) where appropriate but keep code readable for interview explanations.
- Spring AI ChatClient integration comes later in the roadmap; do not wire it into the initial scaffold.
- The `feature/project-foundation` branch exists as the starting point for initial setup.
