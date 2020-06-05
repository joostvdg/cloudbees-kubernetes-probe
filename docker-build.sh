#!/bin/sh
docker build . -t cloudbees-kubernetes-probe
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 8080:8080 cloudbees-kubernetes-probe"
