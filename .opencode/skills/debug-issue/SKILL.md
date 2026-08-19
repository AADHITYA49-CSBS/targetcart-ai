---
name: debug-issue
description: Systematically debug issues in the codebase. Use when investigating errors, failures, or unexpected behavior in tests, builds, or runtime.
---

# debug-issue

Systematically debug issues in the codebase using a structured, repeatable approach.

## When to use

- Tests are failing
- Build errors occur
- Runtime exceptions or unexpected behavior
- API endpoints returning wrong results
- Database or integration issues

## Workflow

1. **Reproduce** — run the failing command or test in isolation to confirm the issue.
2. **Read the error** — capture the full stack trace or error output. Identify the exact file, line, and exception type.
3. **Inspect context** — read the file at the error location and its immediate imports/callers. Understand what the code is trying to do.
4. **Check recent changes** — use `git diff` or `git log` to see if the issue correlates with a recent change.
5. **Form a hypothesis** — state the likely cause before changing anything.
6. **Fix minimally** — apply the smallest change that addresses the root cause. Do not refactor unrelated code.
7. **Verify** — re-run the failing command to confirm the fix. Run the full test suite if the change is non-trivial.
8. **Check for regressions** — ensure the fix does not break other tests or features.

## Constraints

- Never modify code you do not understand. Read it first.
- Never guess at a fix. Form a hypothesis and test it.
- Never overwrite changes blindly — inspect diffs before applying edits.
- Prefer additive fixes over destructive rewrites.
- If the issue requires architectural changes, document the finding and ask the user before proceeding.
