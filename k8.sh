#!/usr/bin/env bash

fail_usage () {
  cat << EOF
usage:
  k8 deploy
    builds the artifact and deploys it to kubernetes
    unit tests are skipped when building the artifact
  k8 remove
    removes the deployment
erroneous command:
  $0
EOF
  exit 1
}

exec_fail_wrapper () {
  if [[ -z "$1" ]]; then fail_usage $@;fi
  local command=$1

  eval ${command} || { fail_usage $@; }
}

deploy () {
  exec_fail_wrapper "mvn clean install -DskipTests"
  exec_fail_wrapper "docker build -t spring/spring-data-jpa ."
  exec_fail_wrapper "kubectl apply -f deployment.yaml"
}

remove_deployment () {
  exec_fail_wrapper "kubectl delete -f ./deployment.yaml"
}

case $1 in
  deploy)
    deploy
    ;;
  remove)
    remove_deployment
    ;;
  *)
    fail_usage
    ;;
esac

popd
exit 0
