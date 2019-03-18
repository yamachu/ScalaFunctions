#!/bin/sh -e

filter="$CUSTOM_JQ_FILTER"

if [ -z "$GITHUB_EVENT_PATH" ]; then
  echo "\$GITHUB_EVENT_PATH" not found
  exit 1
fi

expected="$1"
actual=$(jq -r "$filter" "$GITHUB_EVENT_PATH")

echo "$actual" | grep -Eq "^$expected$" || {
  echo "filter not match, actual: \"$actual\", expected: \"$expected\""
  exit 78
}