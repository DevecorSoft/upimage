# build jenkins

## run jenkins by docker

```shell
docker run \
-u devecor\
-d \
-p 8080:8080 \
-v jenkins-data:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
jenkinsci/blueocean
```