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

* ~~post /upload/image~~    **Deprecated**
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
