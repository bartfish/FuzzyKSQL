package pl.bartryb.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import pl.bartryb.producer.models.AutonomousGuidedVehicle;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerAGV {

    public static void Producer() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer");
        props.put("schema.registry.url", "http://localhost:8081");

        Producer<String, AutonomousGuidedVehicle> producer = new KafkaProducer<String, AutonomousGuidedVehicle>(props);

        String topic = "testjsonschema";
        String key = "testkey";
        AutonomousGuidedVehicle agv = new AutonomousGuidedVehicle();
        agv.traction = 5.0; // 0 - 100
        agv.latitude = 5.00100000000; // 50.0000001
        agv.longtitute = 5.0; // 50.0000001
        agv.machineState = "STANDBY"; // STANDBY, WORKING, BROKEN, LOST_CONNECTION, LOST
        agv.humidity = 5.0; // 0 - 10 (0 means dry, 10 means a lot of water)
        agv.batteryPercentageLeft = 5.0; // 0 - 100 (battery lasting percentage)

        ProducerRecord<String, AutonomousGuidedVehicle> record
                = new ProducerRecord<String, AutonomousGuidedVehicle>(topic, key, agv);
        producer.send(record).get();
        producer.close();
    }


}
