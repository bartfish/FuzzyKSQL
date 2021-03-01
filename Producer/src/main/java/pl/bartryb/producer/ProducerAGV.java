package pl.bartryb.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import pl.bartryb.producer.models.AutonomousGuidedVehicle;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.Timer;
import java.util.TimerTask;

public class ProducerAGV {

    public static void KSProducer() throws ExecutionException, InterruptedException {
        try {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer");
            props.put("schema.registry.url", "http://localhost:8081");

            KafkaProducer<String, AutonomousGuidedVehicle> producer = new KafkaProducer<>(props);

            String topic = "AGV_1";
            String key = "testkey";
            // AutonomousGuidedVehicle agv = new AutonomousGuidedVehicle();
            // agv.traction = 5.0; // 0 - 100
            // agv.latitude = 5.00100000000; // 50.0000001
            // agv.longtitute = 5.0; // 50.0000001
            // agv.machineState = "STANDBY"; // STANDBY, WORKING, BROKEN, LOST_CONNECTION, LOST
            // agv.humidity = 5.0; // 0 - 10 (0 means dry, 10 means a lot of water)
            // agv.batteryPercentageLeft = 5.0; // 0 - 100 (battery lasting percentage)

            // ProducerRecord<String, AutonomousGuidedVehicle> record
            //         = new ProducerRecord<String, AutonomousGuidedVehicle>(topic, key, agv);
            // producer.send(record).get();
            // producer.close();

            TimerTask task = new TimerTask() {
                @Override
                public void run()  {
                  // task to run goes here
                //   System.out.println("Hello !!!");
                    AutonomousGuidedVehicle agv = new AutonomousGuidedVehicle();
                    agv.traction = 5.0; // 0 - 100
                    agv.latitude = 5.00100000000; // 50.0000001
                    agv.longtitute = 5.0; // 50.0000001
                    agv.machineState = "STANDBY"; // STANDBY, WORKING, BROKEN, LOST_CONNECTION, LOST
                    agv.humidity = 5.0; // 0 - 10 (0 means dry, 10 means a lot of water)
                    agv.batteryPercentageLeft = 5.0; // 0 - 100 (battery lasting percentage)

                    ProducerRecord<String, AutonomousGuidedVehicle> record
                            = new ProducerRecord<String, AutonomousGuidedVehicle>(topic, key, agv);
                    try {
                        producer.send(record).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
              };
              Timer timer = new Timer();
              long delay = 0;
              long intevalPeriod = 1 * 1000; 
              
              // schedules the task to be run in an interval 
              timer.scheduleAtFixedRate(task, delay,
                                          intevalPeriod);
                                          
        } catch (Exception e) {
            System.out.println("Message: " + e.getMessage() + " StackTrace: " + e.getStackTrace().toString());
        }

    }


}
