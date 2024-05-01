# Host upimage by yourself

## docker cli
```shell
docker run -d -p 8080:8080 -e upimage_host="http://localhost:8080" --name upimage devecorsoft/upimage
```

## docker compose

create such a `docker-compose.yml` and run it with `docker compose up -d`
```shell
version: '3.9'
services:
  upimage:
    image: devecorsoft/upimage:2.0.17
    ports:
      - '8080:8080'
    volumes:
      - ~/upimage-home:/root/upimage-home
    environment:
      upimage_host: http://localhost:8080
```

## systemd

WIP

## build native app

WIP
