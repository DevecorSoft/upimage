# upimage

[![ci/cd](https://github.com/Devecor/upimage/actions/workflows/ci.yml/badge.svg)](https://github.com/Devecor/upimage/actions/workflows/ci.yml)

a tiny server for uploading images based on spring-boot and kotlin.

## Install

### Requirements

* Java 17

### Build

```shell
cd path/to/upimage
./gradlew build
```

### Run server

* use args

```shell
java -jar upimage-<version>.jar --upimage.host='http://localhost:8080'
```

* use env

```shell
export upimage_host=http://localhost:8080
java -jar upimage-<version>.jar
```

* use by docker compose

```yaml
version: '3.9'
services:
  upimage:
    image: devecor/upimage:2.1.5
    ports:
      - '8080:8080'
    volumes:
      - ~/upimage-home:/root/upimage-home
    environment:
      upimage_host: http://localhost:8080
```

[upimage dockerhub](https://hub.docker.com/r/devecor/upimage/tags)

## APIs

* post `/image`
    * description: upload images by form-data
    * response: link to image
    * example:
        * request
          ```shell
          curl --request POST 'http://localhost:8080/image' \
          --form 'file=@"path/to/image.jpg"'
          ```
        * response
          ```
          http://localhost:8080/1634454401079/image.jpg
          ```

* get `/image`
    * description: download image
    * response: image file
    * example:
        * request
          ```shell
          curl --request GET 'http://localhost:8080/image/image-id/file-name.jpg'
          ```

* post `/actuator/shutdown`
    * description: stop server
    * example:
        * request
          ```shell
          curl --request POST 'http://localhost:8080/actuator/shutdown'
          ```
        * response
          ```json
          {
          "message": "Shutting down, bye..."
          }
          ```

* post `/upload/image`    **Deprecated**
    * description: upload images by form-data
    * response: `![image_name.jpg](link)`
    * example:
        * request
          ```shell
          curl --request POST 'http://localhost:8080/upload/image' \
          --form 'file=@"path/to/image.jpg"'
          ```
        * response
          ```markdown
          ![image.jpg](http://localhost:8080/1634454401079/image.jpg)
          ```

