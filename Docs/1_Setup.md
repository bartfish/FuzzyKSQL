# Setup overall
Basically, what have to be done to get the working solution based on provided codebase.
1. Setup environment
2. Turn on data generators
3. Open KSQL CLI
4. Configure data streams
5. Get familiar with data processing using KSQL and fuzzy techniques

# Environment setup
Before you start setting everything up, please check if you finished all the setup steps from the README.md.

0. Remember about setting up proper environment.
1. Install JAVA (at least 1.8)
2. Install docker
3. Install docker-compose
4. Go to the main project directory
5. Run all images (also download them if necessary)
    ``` sudo docker-compose up ```
Now all the necessary packages will run. The services which are available after all starts looks as follows:

6. Wait a couple of minutes. Then in another console window type:
    ``` sudo docker-compose ps```
to verify if everything is working properly.

Warning! If any of the containters is not working try to kill running containers and rerun the 5th point.
Usually this helps :) If not, then there might be a problem with some configuration.

6. Run data generator
Go to /AGVProducer/target/ and run the jar with dependencies:
    ``` sudo java -jar Kafka...-with-dependencies.jar ```

7. Open KSQL CLI
To open KSQL CLI you need to open another terminal window and provide the following command.
    ``` sudo docker-compose exec ksqldb-cli ksql http://ksqldb-server:8088 ```

If everything went well, you will see that the KSQL server is in RUNNING mode.

8. Create streams
If there is taken into consideration, the data generator which comes with the source codes (AGVProducer),
then the create streams query will look as follows.

```
CREATE STREAM NEW_AGV_1_STREAM 
(Id INTEGER,
    MomentaryConsumption1 INTEGER,
    MomentaryConsumption2 INTEGER,
    MomentaryConsumption3 INTEGER,
    CumulativeEnergyConsumption1 DOUBLE,
    CumulativeEnergyConsumption2 DOUBLE,
    CumulativeEnergyConsumption3 DOUBLE,
    RawInputMeasurement1 INTEGER,
    RawInputMeasurement2 INTEGER,
    RawInputMeasurement3 INTEGER,
    CycleCounterNoOk INTEGER,
    CycleCounterOk INTEGER,
    Timestamp INTEGER,
    TimestampMilliseconds BIGINT) WITH (KAFKA_TOPIC='AGV_1_DEVICE', VALUE_FORMAT='JSON_SR');

CREATE STREAM NEW_AGV_2_STREAM 
(Id INTEGER,
    MomentaryConsumption1 INTEGER,
    MomentaryConsumption2 INTEGER,
    MomentaryConsumption3 INTEGER,
    CumulativeEnergyConsumption1 DOUBLE,
    CumulativeEnergyConsumption2 DOUBLE,
    CumulativeEnergyConsumption3 DOUBLE,
    RawInputMeasurement1 INTEGER,
    RawInputMeasurement2 INTEGER,
    RawInputMeasurement3 INTEGER,
    CycleCounterNoOk INTEGER,
    CycleCounterOk INTEGER,
    TimestampMilliseconds BIGINT,
Timestamp INTEGER) WITH (KAFKA_TOPIC='AGV_2_DEVICE', VALUE_FORMAT='JSON_SR');

```

9. Have fun!
