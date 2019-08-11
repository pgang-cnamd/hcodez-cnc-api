#!/usr/bin/env bash

function usage() {
  printf "Usage:\n\n"
  printf "./docker.sh [<tag_version>] [<push>]\n"
}

if [[ "$1" == "usage" ]]; then
  usage
fi

TAG_VERSION=$1; shift
PUSH=$1; shift

function build() {
  docker build -t cnc-api-dev .
}

function buildWithTag() {
  docker build -t cnc-api:"${TAG_VERSION}" .
}

function push() {
  local img_id="$(docker images --format \{\{.ID\}\} cnc-api:"${TAG_VERSION}")"
  docker tag "${img_id}" cezarmathe/cnc-api:"${TAG_VERSION}"
  docker push cezarmathe/cnc-api:"${TAG_VERSION}"
}

if [[ -z "${TAG_VERSION}" ]]; then
  printf "Building..\n"
  build
else
  printf "Building with tag %s.." "${TAG_VERSION}"
  buildWithTag
fi

if [[ ! -z "${PUSH}" ]]; then
  printf "Pushing..\n"
  push
fi

