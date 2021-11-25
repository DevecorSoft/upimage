# upimage

[![ci/cd](https://github.com/Devecor/upimage/actions/workflows/ci.yml/badge.svg)](https://github.com/Devecor/upimage/actions/workflows/ci.yml)

a tiny server for uploading images based on spring-native and kotlin.

## Install

### Requirements

* [native-image](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#getting-started-native-image-system-requirements)

### Build

```shell
cd path/to/upimage
./gradlew build
./gradlew nativeBuild
```

### Run server

* use args

```shell
$PWD/build/native/nativeBuild/upimage --upimage.host='http://localhost:8080'
```

* use env

```shell
export upimage_host=http://localhost:8080
$PWD/build/native/nativeBuild/upimage
```

## APIs

* post `/upload/image`
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