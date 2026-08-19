---
name: resolve-conflict
description: Analyze and resolve Git merge or rebase conflicts. Use when a merge or rebase fails due to conflicting changes.
---

# resolve-conflict

Analyze and resolve Git merge or rebase conflicts systematically.

## When to use

- `git merge` reports conflicts
- `git rebase` reports conflicts
- A pull or sync operation fails due to conflicts

## Workflow

1. **Identify conflicted files** — run `git status` to see all files with conflict markers.
2. **Categorize each conflict**:
   - **Text conflict** — both sides modified the same lines. Requires manual review.
   - **Delete/modify conflict** — one side deleted a file, the other modified it. Requires a decision: keep, delete, or combine.
   - **Add/add conflict** — both sides added a file with the same name but different content. Requires choosing or merging.
3. **Read the conflict markers** — open each conflicted file and understand both sides of the change.
4. **Resolve logically** — apply the correct resolution based on intent:
   - If changes are independent, combine both.
   - If changes are contradictory, choose the one that matches the intended behavior.
   - If the conflict is ambiguous, stop and ask the user.
5. **Remove conflict markers** — ensure all `<<<<<<<`, `=======`, and `>>>>>>>` markers are removed.
6. **Stage resolved files** — run `git add <file>` for each resolved file.
7. **Continue** — run `git rebase --continue` or `git merge --continue` as appropriate.
8. **Verify** — run the build and tests to confirm the resolution did not break anything.

## Constraints

- Never guess when logical conflicts require user intent — ask.
- Never discard one side blindly without understanding both changes.
- Never leave conflict markers in the code.
- Never skip the build verification after resolving conflicts.
- Prefer understanding intent over mechanical resolution.
