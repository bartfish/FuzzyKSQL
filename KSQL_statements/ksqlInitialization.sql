

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
select * from NEW_AGV_1_STREAM w WHERE (AROUND('TR_L;4000;4900', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK
select * from NEW_AGV_1_STREAM w WHERE (AROUND('TR_R;3900;4500;', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK
select * from NEW_AGV_1_STREAM w WHERE (AROUND('GAUS;4000;1000;', w.CumulativeEnergyConsumption1) > 0.6) EMIT CHANGES; -- OK

select * from AGV_1_STREAM_wu a inner join AGV_2_STREAM_wu b within 7 days on a.TRACTION = b.TRACTION emit changes;

-- LINGUISTIC ASSIGNMENT
select 
    a.CumulativeEnergyConsumption1, 
    ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) as linguisticA,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'low') AS LOW,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'normal') AS NORMAL,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'high') AS HIGH
from NEW_AGV_1_STREAM a 
where ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) != 'none'
emit changes;

-- FUZZY JOIN based on linguistic assignments
select 
    a.CumulativeEnergyConsumption1,
    b.CumulativeEnergyConsumption1,
    ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) as linguisticA,
    ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', b.CumulativeEnergyConsumption1, 0.7) as linguisticB
from NEW_AGV_1_STREAM a 
inner join NEW_AGV_2_STREAM b 
within 7 days 
on 
ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) = ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', b.CumulativeEnergyConsumption1, 0.6) 
where ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', b.CumulativeEnergyConsumption1, 0.7) != 'none' 
emit changes;


--  FUZZY IS
select * from NEW_AGV_2_STREAM a where VERIFY_IS('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'normal', 0.4) emit changes; 

select * from NEW_AGV_1_STREAM a
where FUZZY_AND(
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'high'),
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption3, 'low')
) > 0.5 emit changes;

-- FUZZY_OR 
select * from NEW_AGV_1_STREAM a
where FUZZY_OR(
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'high'),
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption3, 'low'),
    AROUND('TR_F;0;100;250;300', a.MomentaryConsumption1)
) > 0.7 emit changes;

-- FUZZY_GROUPING
SELECT 
ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', w.CumulativeEnergyConsumption3, 0.7) AS STATUS_NAME, COUNT(*) AS STATUS_COUNTER
FROM NEW_AGV_1_STREAM w WINDOW TUMBLING (SIZE 24 HOUR) 
WHERE ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', w.CumulativeEnergyConsumption3, 0.7) != 'none'
GROUP BY ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', w.CumulativeEnergyConsumption3, 0.7) EMIT CHANGES;

