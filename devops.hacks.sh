# list topics
sudo docker-compose exec broker kafka-topics --list --zookeeper zookeeper:2181

# delete unnecessary topic
sudo docker-compose exec broker kafka-topics --delete --zookeeper zookeeper:2181 -topic ALARM_TABLE

# run ksqldb server CLI
sudo docker-compose exec ksqldb-cli ksql http://ksqldb-server:8088

# running ksql users data generator 
sudo docker-compose run --rm --name datagen-users ksql-datagen   ksql-datagen     bootstrap-server=broker:9092     quickstart=users     format=json     topic=workshop-u
maxInterval=1000

# checking connectors
curl localhost:8083/connectors