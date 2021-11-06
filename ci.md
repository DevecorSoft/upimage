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

## configure multiple branch pipeline

## enable webhooks on github

http://host:port/github-webhook/

## install build monitor view

`Manage Jenkins` -> `Manage Plugins` -> `available`
