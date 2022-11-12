call git add *
call git commit -m "avance"
call git push
call mvnw clean install -DskipTests
call docker build -t danaxar/data-microservice .
call docker push danaxar/data-microservice