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

section() {
    local title="$1"
    local pattern="$2"
    local lines
    lines="$(
        printf '%s\n' "${subjects}" | awk -F '\t' -v pattern="${pattern}" '
            $2 ~ pattern {
                subject = $2
                sub(/^[a-z]+(\([^)]*\))?!?: /, "", subject)
                printf "- %s (`%s`)\n", subject, $1
            }
        '
    )"
    if [[ -n "${lines}" ]]; then
        printf '### %s\n\n%s\n\n' "${title}" "${lines}"
    fi
}

printf '## Paróquia Santa Cabrini — %s\n\n' "${tag}"
section "✨ Novidades" '^feat(\(|!|:)'
section "🐛 Correções" '^fix(\(|!|:)'
section "🎨 Melhorias" '^(refactor|perf|style)(\(|!|:)'
section "🧪 Testes e CI" '^(test|ci|build)(\(|!|:)'
section "📚 Documentação e manutenção" '^(docs|chore)(\(|!|:)'
section "Outras mudanças" '^([^a-z]|[a-z]+[^a-z(!:]|(feat|fix|refactor|perf|style|test|ci|build|docs|chore)[^(!:])'

if [[ -n "${GITHUB_REPOSITORY:-}" ]]; then
    if [[ -n "${previous}" ]]; then
        printf '**Changelog completo**: https://github.com/%s/compare/%s...%s\n' \
            "${GITHUB_REPOSITORY}" "${previous}" "${tag}"
    else
        printf '**Histórico**: https://github.com/%s/commits/%s\n' "${GITHUB_REPOSITORY}" "${tag}"
    fi
fi
