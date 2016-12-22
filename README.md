# Commands
mvn clean package

mvn wildfly-swarm:run -Dswarm.project.stage=development@tallion

# URL
http://localhost:8080/rest/swagger.json

# Chrome Plugins
https://augury.angular.io/

https://github.com/mshauneu/chrome-swagger-ui

# Info
http://ksoong.org/wildfly-swarm

# Debug
java -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=y -Dswarm.project.stage=development@coloso -jar target/reelbook-backend-swarm.jar

# Heroku
sudo git push heroku master

sudo heroku logs --tail

http://reelbook-backend.herokuapp.com/rest/test/text