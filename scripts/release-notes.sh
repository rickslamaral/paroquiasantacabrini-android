#!/usr/bin/env bash
#
# Builds Markdown release notes from Conventional Commit subjects.
#
# Usage: scripts/release-notes.sh <tag> <target-ref> [<previous-tag>]
#
# Environment:
#   GITHUB_REPOSITORY  owner/name, used for the changelog link (optional)
#
# Commits between <previous-tag> and <target-ref> are grouped by type; without a previous tag the
# whole history up to <target-ref> is used. Merge commits are skipped.

set -euo pipefail

tag="${1:?usage: release-notes.sh <tag> <target-ref> [<previous-tag>]}"
target="${2:?usage: release-notes.sh <tag> <target-ref> [<previous-tag>]}"
previous="${3:-}"

if [[ -n "${previous}" ]]; then
    range="${previous}..${target}"
else
    range="${target}"
fi

subjects="$(git log --no-merges --format='%h%x09%s' "${range}")"

printf '## Paróquia Santa Cabrini — %s\n\n' "${tag}"

# One pass over the subjects. Only POSIX awk features are used (the GitHub runner ships an older
# mawk than a typical workstation), so parentheses are written as bracket expressions.
printf '%s\n' "${subjects}" | awk -F '\t' '
    BEGIN {
        section["feat"] = 1
        section["fix"] = 2
        section["refactor"] = 3; section["perf"] = 3; section["style"] = 3
        section["test"] = 4; section["ci"] = 4; section["build"] = 4
        section["docs"] = 5; section["chore"] = 5
        title[1] = "✨ Novidades"
        title[2] = "🐛 Correções"
        title[3] = "🎨 Melhorias"
        title[4] = "🧪 Testes e CI"
        title[5] = "📚 Documentação e manutenção"
        title[6] = "Outras mudanças"
    }
    NF >= 2 {
        subject = $2
        group = 6
        if (match(subject, /^[a-z]+([(][^)]*[)])?!?: /)) {
            prefix = substr(subject, 1, RLENGTH)
            type = prefix
            sub(/[(!:].*$/, "", type)
            if (type in section) {
                group = section[type]
            }
            subject = substr(subject, RLENGTH + 1)
        }
        items[group] = items[group] "- " subject " (`" $1 "`)\n"
    }
    END {
        for (group = 1; group <= 6; group++) {
            if (group in items) {
                printf "### %s\n\n%s\n", title[group], items[group]
            }
        }
    }
'

if [[ -n "${GITHUB_REPOSITORY:-}" ]]; then
    if [[ -n "${previous}" ]]; then
        printf '**Changelog completo**: https://github.com/%s/compare/%s...%s\n' \
            "${GITHUB_REPOSITORY}" "${previous}" "${tag}"
    else
        printf '**Histórico**: https://github.com/%s/commits/%s\n' "${GITHUB_REPOSITORY}" "${tag}"
    fi
fi
