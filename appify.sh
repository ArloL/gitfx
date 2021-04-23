#!/bin/sh

set -o errexit
set -o nounset
set -o xtrace

cd "$(dirname "$0")" || exit 1

EXECUTABLE=${1}
APPNAME=${2}
SOURCE_DIRECTORY=${3:-}

TARGET_DIRECTORY="target/${APPNAME}.app/Contents/MacOS"

rm -rf "target/${APPNAME}.app"

mkdir -p "${TARGET_DIRECTORY}"

cp "${EXECUTABLE}" "${TARGET_DIRECTORY}/${APPNAME}"
chmod +x "${TARGET_DIRECTORY}/${APPNAME}"

if [ -d "${SOURCE_DIRECTORY}" ]; then
    cp -R "${SOURCE_DIRECTORY}/." "${TARGET_DIRECTORY}"
fi
