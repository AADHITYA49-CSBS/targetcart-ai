---
name: generate-feature
description: Generate modular code and features following the project's feature-based architecture. Use when creating new controllers, services, repositories, DTOs, entities, or complete feature modules.
---

# generate-feature

Generate modular code and features following the project's feature-based architecture.

## When to use

- Creating new feature modules
- Adding controllers, services, repositories, DTOs, or entities
- Scaffolding new API endpoints
- Building Spring Boot business components

## Workflow

1. **Inspect existing structure** — read the target module directory and neighboring features before writing any code.
2. **Verify ownership** — ensure the new code belongs to a single feature module. Do not spread code across feature boundaries.
3. **Check conventions** — match the package naming, class structure, and coding patterns already present in the codebase.
4. **Generate incrementally** — create one file at a time. Do not generate entire feature trees in one pass.
5. **Respect boundaries** — keep controller, service, repository, DTO, and entity layers separated within the feature.
6. **Avoid cross-feature dependencies** — do not import from another feature's internal packages unless a shared utility is clearly warranted.
7. **Prefer additive changes** — add new files and code rather than rewriting existing implementations.
8. **Validate** — run the relevant build/test commands after creating files.

## Constraints

- Never modify shared configuration without clear justification.
- Never generate fake or mock business functionality unless explicitly requested.
- Keep files focused; split when a class exceeds ~300 lines.
- Use Java 21 features (records, sealed classes, pattern matching) where appropriate but keep code readable.
- Do not wire Spring AI into initial scaffolding unless explicitly instructed.
