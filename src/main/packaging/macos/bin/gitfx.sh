#!/bin/sh

set -o errexit
set -o nounset
#set -o xtrace

realpath() { python -c "import os,sys; print(os.path.realpath(sys.argv[1]))" "$0"; }
CONTENTS="$(dirname "$(dirname "$(dirname "$(dirname "$(realpath "$0")")")")")"
open "${CONTENTS}" --args "$@"