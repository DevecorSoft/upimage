# Try upimage with docker

## Requirements

* You have `Docker` installed on your machine. If case not, you can follow [docker official doc](https://docs.docker.com/get-docker/)

## Docker cli

```shell
docker run -it -p 8080:8080 -e upimage_host="http://localhost:8080" devecor/upimage
```

Now you can upload a picture on a new terminal session:

```shell
curl --request POST 'http://localhost:8080/image' \
--form 'file=@"path/to/image.jpg"'
```

Then you have an url as the response:

`http://localhost:8080/image/5c90e119-72cb-4458-8d63-c58d5471c2bf/image.jpg`

Feel free to open this url on your browser!

## Dokcer compose

1. please make sure you have docker compose plugin installed:
   
   ```shell
   docker compose -h
   ```

   If case not, you can just follow [docker docs](https://docs.docker.com/compose/install/).
   By the way, if you are using `Homebrew`, it's so easy to install docker compose: `brew install docker-compose`

2. Create your `docker-compose.yml` file with such content:

```yaml
version: '3.9'
services:
  upimage:
    image: devecor/upimage:2.0.15
    ports:
      - '8080:8080'
    volumes:
      - ~/upimage-home:/root/upimage-home
    environment:
      upimage_host: http://localhost:8080
```

3. Run upimage with docker compose

```shell
docker compose up
```

Now you can upload a picture on a new terminal session:

```shell
curl --request POST 'http://localhost:8080/image' \
--form 'file=@"path/to/image.jpg"'
```

Then you have an url as the response:

`http://localhost:8080/image/5c90e119-72cb-4458-8d63-c58d5471c2bf/image.jpg`
