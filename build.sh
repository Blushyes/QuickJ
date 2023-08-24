docker rm -f quickj
docker build . -t quickj
docker run -d -p 8888:8888 --name quickj quickj