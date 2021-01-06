
# Basic elastic search sink for kafka

curl -X "POST" "http://localhost:8083/connectors/" \
     -H "Content-Type: application/json" \
     -d '{
  "name": "sink_elastic_DEFAULT",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "connection.url": "http://elasticsearch:9200",
    "type.name": "ksql-workshop",
    "tasks.max": "1",
    "schema.ignore": "true",
    "topics": "WORSHOP_USERS",
    "write.method": "upsert"
  }
}'


curl -X "POST" "http://localhost:8083/connectors/" \
     -H "Content-Type: application/json" \
     -d '{
  "name": "sink_elastic_DEFAULT1",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "connection.url": "http://elasticsearch:9200",
    "type.name": "workshop-u",
    "schema.ignore": "true",
    "topics": "workshop-users",
    "write.method": "upsert"
  }
}'

CREATE SINK CONNECTOR SINK_ELASTIC_TEST_01 WITH (
  'connector.class' = 'io.confluent.connect.elasticsearch.ElasticsearchSinkConnector',
  'connection.url'  = 'http://elasticsearch:9200',
  'key.converter'   = 'org.apache.kafka.connect.storage.StringConverter',
  'type.name'       = '_doc',
  'topics'          = 'workshop-u',
  'key.ignore'      = 'true',
  'schema.ignore'   = 'false'
);