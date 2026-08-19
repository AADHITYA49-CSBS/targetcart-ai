---
name: push-feature
description: Safely synchronize a feature branch with main and push to remote. Use when the user asks to push or sync their feature branch.
---

# push-feature

Safely synchronize a feature branch with `main` and push to the remote repository.

## When to use

- User asks to push changes
- Feature branch needs to stay current with main
- Preparing a feature branch for pull request

## Workflow

1. **Verify branch** — confirm you are on a feature branch, not `main`. If on `main`, stop and ask the user.
2. **Inspect status** — run `git status` to ensure there are no uncommitted changes. If there are, commit first or ask the user.
3. **Fetch remote** — run `git fetch origin` to get the latest remote state.
4. **Check divergence** — run `git log --oneline main..HEAD` and `git log --oneline HEAD..origin/main` to see what is ahead and behind.
5. **Rebase onto main** — run `git rebase origin/main` to replay feature commits on top of main.
6. **Handle conflicts** — if rebase encounters conflicts, use the resolve-conflict skill. Do not proceed until conflicts are resolved.
7. **Verify build** — after rebase, run the project build and tests to ensure nothing broke.
8. **Push** — run `git push origin <branch-name>`. If the branch was rebased, use `--force-with-lease` only if explicitly necessary and approved by the user.

## Constraints

- Never push directly to `main`.
- Never force-push without explicit user approval.
- Never push unless explicitly requested.
- Prefer rebase over merge for keeping history linear.
- Always verify the build passes before pushing.
