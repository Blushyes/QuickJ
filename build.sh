docker rm -f quick-spring
docker build . -t quick-spring
docker run -d -p 8888:8888 --name quick-spring quick-spring