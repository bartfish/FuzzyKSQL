package pl.bartryb.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.util.RandomData;
import org.apache.commons.lang.time.StopWatch;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jboss.netty.util.internal.ThreadLocalRandom;
import pl.bartryb.producer.models.AutonomousGuidedVehicle;
import pl.bartryb.producer.models.AutonomousGuidedVehicleOther;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;

import org.apache.commons.math3.random.RandomDataImpl;

public class ProducerAGV {

    private static long getRandomYearMillisecond(LocalDate min, LocalDate max) {
        Date d1 = Date.from(min.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date d2 = Date.from(max.atStartOfDay(ZoneId.systemDefault()).toInstant());
        RandomDataImpl randomData = new RandomDataImpl();

        return randomData.nextLong(d1.getTime(), d2.getTime());
    }

    private static List<String> machineStates = Arrays.asList(new String[] { "STANDBY", "WORKING", "BROKEN", "OFFLINE"});
    private static List<String> weightUnits = Arrays.asList(new String[]
            { "kg", "kilogram", "KG", "kilogr", "kil", "kilo", "kilog", "gram", "ml", "mili", "l", "liters"});

    private static void printObjectAsJson(Object agv) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(agv);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);
        System.out.println(agv);
    }

    private static int generalCounter = 0;

    private static LocalDate minDate = LocalDate.of(2020, 7, 1);
    private static LocalDate maxDate = LocalDate.of(2021, 7, 1);

    private static void defineStream1(KafkaProducer<String, AutonomousGuidedVehicle> producer, String topic, String key, StopWatch timeWatch) {
        TimerTask task = new TimerTask() {
            @Override
            public void run()  {
                Random rand = new Random();
                // task to run goes here
                //   System.out.println("Hello !!!");
                AutonomousGuidedVehicle agv = new AutonomousGuidedVehicle();
                agv.CumulativeEnergyConsumption1 = rand.nextDouble() * 5000; // 0 - 100
                agv.CumulativeEnergyConsumption2 = rand.nextDouble() * 10000; // 0 - 100
                agv.CumulativeEnergyConsumption3 = rand.nextDouble() * 5000; // 0 - 100
                agv.MomentaryConsumption1 = rand.nextInt(300);
                agv.MomentaryConsumption2 = rand.nextInt(1000);
                agv.MomentaryConsumption3 = rand.nextInt(500);
                agv.RawInputMeasurement1 = rand.nextInt(1000);
                agv.RawInputMeasurement2 = rand.nextInt(500);
                agv.RawInputMeasurement3 = rand.nextInt(10000);
                agv.Id = rand.nextInt(2);
                agv.TimestampSeconds = rand.nextInt(86400); //getRandomYearMillisecond(minDate, maxDate);
                
                agv.CycleCounterOk = ++generalCounter;

                ProducerRecord<String, AutonomousGuidedVehicle> record
                        = new ProducerRecord<String, AutonomousGuidedVehicle>(topic, key, agv);
                try {
                    producer.send(record).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
//                printObjectAsJson(agv);
//                System.out.println(agv);
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 500;

        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay,
                intevalPeriod);
    }

    private static void defineStream2(KafkaProducer<String, AutonomousGuidedVehicleOther> producer, String topic, String key, StopWatch timeWatch) {
        TimerTask task = new TimerTask() {
            @Override
            public void run()  {

                Random rand = new Random();
                // task to run goes here
                AutonomousGuidedVehicleOther agv = new AutonomousGuidedVehicleOther();
                agv.CumulativeEnergyConsumption1 = rand.nextDouble() * 5000; // 0 - 100
                agv.CumulativeEnergyConsumption2 = rand.nextDouble() * 10000; // 0 - 100
                agv.CumulativeEnergyConsumption3 = rand.nextDouble() * 5000; // 0 - 100
                agv.MomentaryConsumption1 = rand.nextInt(300);
                agv.MomentaryConsumption2 = rand.nextInt(1000);
                agv.MomentaryConsumption3 = rand.nextInt(500);
                agv.RawInputMeasurement1 = rand.nextInt(1000);
                agv.RawInputMeasurement2 = rand.nextInt(500);
                agv.RawInputMeasurement3 = rand.nextInt(10000);
                agv.Id = rand.nextInt(2);
                agv.TimestampSeconds = rand.nextInt(86400); //getRandomYearMillisecond(minDate, maxDate);
                agv.CycleCounterOk = ++generalCounter;

//                printObjectAsJson(agv);

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
        long intevalPeriod = 1 * 1000;

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

            String topic1 = "AGV1_DEVICE";
            String key1 = "testkey1";

            String topic2 = "AGV2_DEVICE";
            String key2 = "testkey2";

            StopWatch timeWatch = new StopWatch();
            timeWatch.start();

            defineStream1(producer, topic1, key1, timeWatch);
            defineStream2(producerOther, topic2, key2, timeWatch);

                                          
        } catch (Exception e) {
            System.out.println("Message: " + e.getMessage() + " StackTrace: " + e.getStackTrace().toString());
        }

    }


}
