echo "解析pom.xml"
pom_xml_head10=$(head -n 10 pom.xml)
project_name=$(echo "$pom_xml_head10" | sed -n 's/.*<artifactId>\(.*\)<\/artifactId>.*$/\1/p')
version=$(echo "$pom_xml_head10" | sed -n 's/.*<version>\(.*\)<\/version>.*$/\1/p')

echo "项目名：$project_name"
echo "版本号：$version"

if [ ! -f Dockerfile ]; then
  echo "生成Dockerfile"
  sed -e "s/{{projectName}}/${project_name}/" -e "s/{{version}}/${version}/" Dockerfile.template | cat > Dockerfile
fi

echo "删除原有容器"
docker rm -f "${project_name}"

echo "构建镜像"
docker build . -t "${project_name}"

echo "启动容器"
docker run -d -p 8888:8888 --name "${project_name} ${project_name}"