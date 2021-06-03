docker build -t comments-api . 
docker rm -f commentsApi
docker run -it -d -p 8080:8080 --restart=always --log-opt max-size=10k --log-opt max-file=1 --name=commentsApi  comments-api:latest
