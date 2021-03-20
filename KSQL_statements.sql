

-- stream 1
CREATE STREAM AGV_1_STREAM_wu (traction DOUBLE, longtitude DOUBLE, latitude DOUBLE, 
batteryPercentageLeft DOUBLE, machineState VARCHAR, 
humidity DOUBLE, weightValue DOUBLE, weightUnit VARCHAR) WITH (K
AFKA_TOPIC='AGV_1', VALUE_FORMAT='JSON_SR');

CREATE STREAM AGV_2_STREAM_wu (traction DOUBLE, longtitude DOUBLE, latitude DOUBLE, 
batteryPercentageLeft DOUBLE, machineState VARCHAR, 
humidity DOUBLE, weightUnit VARCHAR) WITH (KAFKA_TOPIC='AGV_2', VALUE_FORMAT='JSON_SR');


-- joining streams
select 
    a.traction as TRACTION_1,
    b.traction as TRACTION_2,

    a.batteryPercentageLeft,
    b.batteryPercentageLeft,

    a.longtitude,
    b.longtitude,

    a.latitude,
    b.latitude
from AGV_1_STREAM a inner join AGV_2_STREAM b within 7 days on a.machineState = b.machineState emit changes;

-- fuzzy filtering for strings
select * from AGV_1_STREAM_wu a 
inner join AGV_2_STREAM_wu b within 7 days on a.machineState = b.machineState 
WHERE fuzzyfiltering(a.weightUnit, b.weightUnit) < 2 emit changes;


-- FUZZY FILTERING

-- AROUND_TRIANGULAR
    
    -- CRISP APPROACH: 
select * from AGV_1_STREAM_WU w WHERE w.TRACTION > 0.8 EMIT CHANGES;
    -- FUZZY APPROACH:
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRIANGULAR('LET', w.TRACTION, 50, 80, 70) > 0.6) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRIANGULAR('CONST', w.TRACTION, 50, 80, 70) > 0.6) EMIT CHANGES; -- TO BE IMPLEMENTED AND TESTED

-- AROUND_TRAPEZ


-- AROUND_TRAPEZ_L


-- AROUND_TRAPEZ_R


-- AROUND_GAUSS

