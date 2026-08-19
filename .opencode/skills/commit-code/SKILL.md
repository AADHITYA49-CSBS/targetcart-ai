---
name: commit-code
description: Inspect git status and diff, then create well-structured conventional commits. Use when staging and committing changes.
---

# commit-code

Inspect the current state of the repository and create clear, conventional commits.

## When to use

- After completing a logical unit of work
- When the user asks to commit changes
- Before switching tasks or branches

## Workflow

1. **Verify branch** — confirm you are on a feature branch, not `main`. If on `main`, stop and ask the user.
2. **Inspect status** — run `git status` to see all modified, new, and deleted files.
3. **Inspect diff** — run `git diff` (staged and unstaged) to understand exactly what changed.
4. **Review changes** — read the diffs carefully. Ensure no secrets, API keys, or credentials are included.
5. **Stage intentionally** — use `git add` with specific files. Do not use `git add .` unless all changes are intentional.
6. **Write commit message** — follow conventional commit format:
   - `feat:` for new features
   - `fix:` for bug fixes
   - `docs:` for documentation changes
   - `refactor:` for code restructuring
   - `test:` for test additions or fixes
   - `chore:` for tooling, config, or maintenance
   - Keep the subject line under 72 characters.
7. **Commit** — create the commit with the message.
8. **Do not push** — stopping before push is intentional. Use the push-feature skill separately.

## Constraints

- Never commit on `main`.
- Never commit secrets, API keys, or credentials.
- Never commit unless explicitly requested.
- Never use `--amend`, `--force`, or skip hooks unless explicitly requested.
- Inspect before staging — do not stage files blindly.
