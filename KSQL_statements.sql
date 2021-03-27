

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


-- FUZZY FILTERING - each elastic approach has its constant equivalent, where parameters are defined before the compilation of the solution
    
-- CRISP APPROACH: 

select * from AGV_1_STREAM_WU w WHERE w.TRACTION > 0.8 EMIT CHANGES;
-- FUZZY APPROACH:
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRIANGULAR('LET', w.TRACTION, 50, 80, 70) > 0.6) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRIANGULAR('CONST', w.TRACTION) > 0.6) EMIT CHANGES; -- TO BE IMPLEMENTED AND TESTED

-- AROUND_TRAPEZ
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRAPEZ('LET', w.TRACTION, 20, 40, 50, 60) > 0.4) EMIT CHANGES;

-- AROUND_TRAPEZ_L
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRAPEZ_L('LET', w.TRACTION, 20, 40) > 0.6) EMIT CHANGES;

-- AROUND_TRAPEZ_R
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRAPEZ_R('LET', w.TRACTION, 40, 80) > 0.9) EMIT CHANGES;

-- AROUND_GAUSS
select * from AGV_1_STREAM_WU w WHERE (AROUND_GAUSS('LET', w.TRACTION, 40, 10) > 0.9) EMIT CHANGES;


-- OVERALL AROUND
select * from AGV_1_STREAM_WU w WHERE (AROUND('TRIA;40;60;80', w.TRACTION) > 0.9) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND('TR_F;40;60;80;90', w.TRACTION) > 0.9) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND('TR_L;40;60', w.TRACTION) > 0.9) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND('TR_R;40;60;', w.TRACTION) > 0.9) EMIT CHANGES;

select * from AGV_1_STREAM_WU w WHERE (AROUND('GAUS;40;60;80', w.TRACTION) > 0.9) EMIT CHANGES;

