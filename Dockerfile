FROM oracle/graalvm-ce:20.0.0-java8 as graalvm
# For JDK 11
#FROM oracle/graalvm-ce:20.0.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/cloudbees-kubernetes-probe
WORKDIR /home/app/cloudbees-kubernetes-probe

RUN native-image --no-server -cp target/cloudbees-kubernetes-probe-*.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/cloudbees-kubernetes-probe/cloudbees-kubernetes-probe /app/cloudbees-kubernetes-probe
ENTRYPOINT ["/app/cloudbees-kubernetes-probe"]
