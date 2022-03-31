#!/bin/sh

set -o errexit
set -o nounset
#set -o xtrace

app_realpath() {
	SOURCE=$1
	while [ -h "$SOURCE" ]; do
		DIR=$(dirname "$SOURCE")
		SOURCE=$(readlink "$SOURCE")
		[ "${SOURCE}" = "${SOURCE#/}" ] && SOURCE=$DIR/$SOURCE
	done
	SOURCE_DIR="$( cd -P "$( dirname "$SOURCE" )" >/dev/null 2>&1 && pwd )"
	echo "${SOURCE_DIR%%"${SOURCE_DIR#*.app}"}"
}

APP_PATH="$(app_realpath "${0}")"
if [ -z "$APP_PATH" ]; then
	echo "Unable to determine app path from symlink : ${0}"
	exit 1
fi
open "${APP_PATH}" --args "$(readlink -f "${1}")"
exit $?
