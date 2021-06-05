

-- STREAMS INITIALIZATION
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
Timestamp INTEGER) WITH (KAFKA_TOPIC='NEW_AGV_1', VALUE_FORMAT='JSON_SR');

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
Timestamp INTEGER) WITH (KAFKA_TOPIC='NEW_AGV_2', VALUE_FORMAT='JSON_SR');

-- FUZZY APPROACH:

-- AROUND_TRIANGULAR
select * from NEW_AGV_1_STREAM w WHERE (AROUND_TRIANGULAR('LET', w.CumulativeEnergyConsumption1, 1000, 5000, 4500) > 0.8) EMIT CHANGES; -- OK
-- select * from AGV_1_STREAM_WU w WHERE (AROUND_TRIANGULAR('CONST', w.TRACTION) > 0.6) EMIT CHANGES; -- TO BE IMPLEMENTED AND TESTED

-- AROUND_TRAPEZ
select * from NEW_AGV_1_STREAM w WHERE (AROUND_TRAPEZ('LET', w.CumulativeEnergyConsumption1, 1000, 3500, 4500, 5000) > 0.6) EMIT CHANGES; -- OK

-- AROUND_TRAPEZ_L
select * from NEW_AGV_1_STREAM w WHERE (AROUND_TRAPEZ_L('LET', w.CumulativeEnergyConsumption1, 4000, 4900) > 0.9) EMIT CHANGES; -- OK

-- AROUND_TRAPEZ_R
select * from NEW_AGV_1_STREAM w WHERE (AROUND_TRAPEZ_R('LET', w.CumulativeEnergyConsumption1, 3900, 4500) > 0.9) EMIT CHANGES; -- OK

-- AROUND_GAUSS
select * from NEW_AGV_1_STREAM w WHERE (AROUND_GAUSS('LET', w.CumulativeEnergyConsumption1, 4000, 1000) > 0.9) EMIT CHANGES; -- OK

select * from NEW_AGV_1_STREAM w WHERE (AROUND('TRIA;1000;4500;5000', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK
select * from NEW_AGV_1_STREAM w WHERE (AROUND('TR_F;500;3700;4700;5000', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK
select * from NEW_AGV_1_STREAM w WHERE (AROUND('TR_L;40;60', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK
select * from NEW_AGV_1_STREAM w WHERE (AROUND('TR_R;40;60;', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK
select * from NEW_AGV_1_STREAM w WHERE (AROUND('GAUS;40;60;', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK