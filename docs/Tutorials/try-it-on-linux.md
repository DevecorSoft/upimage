# Try it on linux server

## Ubuntu

1. make sure you have java17 installed

```shell
java --version
```

if case not, you can install it as follows:

```shell
sudo apt update
sudo apt install openjdk-17-jdk
```

2. Download upimage.jar

[Here](https://github.com/DevecorSoft/upimage/releases) you can find the latest artifact.

And you can download it via `curl` or `wget`:
```shell
curl -L https://github.com/DevecorSoft/upimage/releases/download/v2.0.17/upimage-2.0.17.jar > upimage-2.0.17.jar
```
```shell
wget https://github.com/DevecorSoft/upimage/releases/download/v2.0.17/upimage-2.0.17.jar
```

3. Run upimage service along with upimge_host

There are two options to specify your own upimge_host:

* use args:
```shell
java -jar upimage-2.0.15.jar --upimage.host='http://localhost:8080'
```

* use env:
```shell
export upimage_host=http://localhost:8080
java -jar upimage-2.0.15.jar
```

4. Upload a picture

```shell
curl --request POST 'http://localhost:8080/image' \
--form 'file=@"path/to/image.jpg"'
```

Then you have an url as the response:

`http://localhost:8080/image/5c90e119-72cb-4458-8d63-c58d5471c2bf/image.jpg`

Feel free to open this url on your browser!
