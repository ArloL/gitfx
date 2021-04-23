#!/bin/sh
cd -- "$(dirname -- "${0}")" || exit 1
./bin/java -m gitfx/gitfx.GitFX
exit 0
