package pl.bartryb.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import pl.bartryb.producer.models.AutonomousGuidedVehicle;
import pl.bartryb.producer.models.AutonomousGuidedVehicleOther;

import java.util.*;
import java.util.concurrent.ExecutionException;


public class ProducerAGV {

    private static List<String> machineStates = Arrays.asList(new String[] { "STANDBY", "WORKING", "BROKEN", "OFFLINE"});
    private static List<String> weightUnits = Arrays.asList(new String[]
            { "kg", "kilogram", "KG", "kilogr", "gram", "ml", "l"});

    private static void defineStream1(KafkaProducer<String, AutonomousGuidedVehicle> producer, String topic, String key) {
        TimerTask task = new TimerTask() {
            @Override
            public void run()  {

                Random rand = new Random();
                // task to run goes here
                //   System.out.println("Hello !!!");
                AutonomousGuidedVehicle agv = new AutonomousGuidedVehicle();
                agv.traction = rand.nextDouble();; // 0 - 100
                agv.latitude = rand.nextDouble(); // 50.0000001
                agv.longtitute = rand.nextDouble(); // 50.0000001
                agv.machineState = machineStates.get(rand.nextInt(3)); // STANDBY, WORKING, BROKEN, LOST_CONNECTION, LOST
                agv.humidity = rand.nextDouble(); // 0 - 10 (0 means dry, 10 means a lot of water)
                agv.batteryPercentageLeft = rand.nextDouble(); // 0 - 100 (battery lasting percentage)

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
    }

    private static void defineStream2(KafkaProducer<String, AutonomousGuidedVehicleOther> producer, String topic, String key) {
        TimerTask task = new TimerTask() {
            @Override
            public void run()  {

                Random rand = new Random();
                // task to run goes here
                //   System.out.println("Hello !!!");
                AutonomousGuidedVehicleOther agv = new AutonomousGuidedVehicleOther();
                agv.traction = rand.nextDouble();; // 0 - 100
                agv.latitude = rand.nextDouble(); // 50.0000001
                agv.longtitute = rand.nextDouble(); // 50.0000001
                agv.machineState = machineStates.get(rand.nextInt(3)); // STANDBY, WORKING, BROKEN, LOST_CONNECTION, LOST
                agv.humidity = rand.nextDouble(); // 0 - 10 (0 means dry, 10 means a lot of water)
                agv.batteryPercentageLeft = rand.nextDouble(); // 0 - 100 (battery lasting percentage)
                agv.weightValue =  rand.nextDouble();
                agv.weightUnit = weightUnits.get(rand.nextInt(weightUnits.size() - 1 ));

                ProducerRecord<String, AutonomousGuidedVehicleOther> record
                        = new ProducerRecord<String, AutonomousGuidedVehicleOther>(topic, key, agv);
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
        long intevalPeriod = 1 * 1500;

        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay,
                intevalPeriod);
    }


    public static void KSProducer() throws ExecutionException, InterruptedException {
        try {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer");
            props.put("schema.registry.url", "http://localhost:8081");

            KafkaProducer<String, AutonomousGuidedVehicle> producer = new KafkaProducer<>(props);
            KafkaProducer<String, AutonomousGuidedVehicleOther> producerOther = new KafkaProducer<>(props);

            String topic1 = "AGV_1";
            String key1 = "testkey1";

            String topic2 = "AGV_2";
            String key2 = "testkey2";

            defineStream1(producer, topic1, key1);
            defineStream2(producerOther, topic2, key2);

                                          
        } catch (Exception e) {
            System.out.println("Message: " + e.getMessage() + " StackTrace: " + e.getStackTrace().toString());
        }

    }


}
