#!/bin/sh

set -o errexit
set -o nounset
set -o xtrace

cd "$(dirname "$0")" || exit 1

EXECUTABLE=${1}
APPNAME=${2}
RESOURCE_DIRECTORY=${3:-}

CONTENTS="target/${APPNAME}.app/Contents"

rm -rf "${CONTENTS}"

mkdir -p "${CONTENTS}/MacOS"
mkdir -p "${CONTENTS}/Resources"

cp "${EXECUTABLE}" "${CONTENTS}/MacOS/${APPNAME}"
chmod +x "${CONTENTS}/MacOS/${APPNAME}"

if [ -d "${RESOURCE_DIRECTORY}" ]; then
    cp -R "${RESOURCE_DIRECTORY}/." "${CONTENTS}/Resources"
fi
