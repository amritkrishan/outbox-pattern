#Command to Build Custom Transformer Application for Kafka Connect
mvn clean install (customtransformer)
docker build -t custom-debezium-connect .

#Command to Run Postgres locally inside a Container (Only when not running studentmicroservice via docker compose)
docker pull postgres
mkdir -p $HOME/docker/volumes/postgres
docker run --rm  --name pg-docker -e POSTGRES_PASSWORD=password -e POSTGRES_USER=docker -e POSTGRES_DB=studentdb -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data postgres
docker exec -it pg-docker bash
psql -d studentdb -U docker

#Docker Command to Start Containers in Detached Mode
docker-compose up -d

#Docker Command to Create Kafka Topic for Student Email Changed
docker exec -t kafka /usr/bin/kafka-topics \
      --create --bootstrap-server :9092 \
      --topic student_email_changed \
      --partitions 1 \
      --replication-factor 1

#Docker Command to Create Kafka Topic for Student Enrolled
docker exec -t kafka /usr/bin/kafka-topics \
      --create --bootstrap-server :9092 \
      --topic student_enrolled \
      --partitions 1 \
      --replication-factor 1

#cURL Command to Link OutBox Table from Postgres to Kafka Connect
curl -X POST \
  http://localhost:8083/connectors/ \
  -H 'content-type: application/json' \
  -d '{
   "name": "student-outbox-connector",
   "config": {
      "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
      "tasks.max": "1",
      "database.hostname": "postgres",
      "database.port": "5432",
      "database.user": "docker",
      "database.password": "password",
      "database.dbname": "studentdb",
      "database.server.name": "pg-outbox-server",
      "tombstones.on.delete": "false",
      "table.whitelist": "public.outbox",
      "transforms": "outbox",
      "transforms.outbox.type": "com.sohan.transform.CustomTransformation"
   }
}' 

#Command to Start Student Microservice Application
mvn spring-boot:run (studentmicroservice)     

#Command to Manually Read Messages from Kafka Consumer
./kafka-console-consumer.sh --bootstrap-server localhost:9092  --topic student_email_changed  --from-beginning
./kafka-console-consumer.sh --bootstrap-server localhost:9092  --topic student_enrolled  --from-beginning

#cURL Command to update student email
curl -X PUT http://localhost:8080/students/1/update-email -H 'content-type: application/json' -d '{"email": "abcd@gmail.com"}'

#cURL Command to enroll new student
curl -X POST   'http://localhost:8080/students/~/enroll' -H 'content-type: application/json' -d '{
    "name": "Amrit Krishan",
    "email": "amrit@gmail.com",
    "address": "Toronto, ON"
}'

docker-compose down
