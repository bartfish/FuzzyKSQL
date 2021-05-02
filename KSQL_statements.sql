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

select * from AGV_1_STREAM_WU w WHERE (AROUND('GAUS;40;60;', w.TRACTION) > 0.9) EMIT CHANGES;


-- CRISP JOIN
select * from AGV_1_STREAM_wu a inner join AGV_2_STREAM_wu b within 7 days on a.machineState = b.machineState emit changes;
select * from AGV_1_STREAM_wu a inner join AGV_2_STREAM_wu b within 7 days on a.TRACTION = b.TRACTION emit changes;

-- FUZZY JOIN based on linguistic assignments
select 
    a.TRACTION,
    b.TRACTION,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.8) as linguisticA,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) as linguisticB
from AGV_1_STREAM_wu a 
inner join AGV_2_STREAM_wu b 
within 7 days 
on 
ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.8) = ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) 
where ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) != 'none' 
emit changes;


-- FUZZY IS
select * from AGV_1_STREAM_wu a where VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 'normal', 0.8) emit changes;

select * from AGV_1_STREAM_wu a, AGV_2_STREAM_wu b WHERE a.TRACTION < 80 AND b.TRACTION < 75 EMIT CHANGES;

-- FUZZY_AND

select * from AGV_1_STREAM_wu a
where FUZZY_AND(
    GET_MEMBERSHIP_DEGREE('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 'high'),
    GET_MEMBERSHIP_DEGREE('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.LATITUDE, 'low')
) > 0.5 emit changes;

-- FUZZY_OR 

select * from AGV_1_STREAM_wu a
where FUZZY_OR(
    GET_MEMBERSHIP_DEGREE('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 'high'),
    GET_MEMBERSHIP_DEGREE('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.LATITUDE, 'low'),
    AROUND('TR_F;0.1;0.35;0.8;0.99', a.BATTERYPERCENTAGELEFT)
) > 0.7 emit changes;

-- FUZZY GROUP BY - todo
SELECT w.TRACTION, COUNT(*) FROM AGV_1_STREAM_WU w WINDOW TUMBLING (SIZE 1 HOUR) GROUP BY ASSIGN_LING('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION), w.TRACTION EMIT CHANGES;

SELECT 
ASSIGN_LING('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION), COUNT(*) 
FROM AGV_1_STREAM_WU w WINDOW TUMBLING (SIZE 24 HOUR) 
GROUP BY ASSIGN_LING('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION) EMIT CHANGES;

-- MORE NONES IN THE ONE BELOW
SELECT 
ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7), COUNT(*) 
FROM AGV_1_STREAM_WU w WINDOW TUMBLING (SIZE 24 HOUR) 
GROUP BY ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7) EMIT CHANGES;

-- DO NOT DISPLAY NONES - not working
SELECT 
ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7), COUNT(*) 
FROM AGV_1_STREAM_WU w WINDOW TUMBLING (SIZE 24 HOUR) 
WHERE ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7) != 'none'
GROUP BY ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7) EMIT CHANGES;

-- FJOIN - TODO
select 
    a.TRACTION,
    b.TRACTION,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.8) as linguisticA,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) as linguisticB
from AGV_1_STREAM_wu a 
inner join AGV_2_STREAM_wu b 
within 7 days 
on FUZZY_JOIN(a.TRACTION, 20, b.TRACTION, 20) > 0.3 
emit changes;

select 
    a.TRACTION,
    b.TRACTION,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.8) as linguisticA,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) as linguisticB
from AGV_1_STREAM_wu a 
inner join AGV_2_STREAM_wu b 
within 7 days 
on a.machineState = b.machineState 
WHERE FUZZY_JOIN(a.TRACTION, 20, b.TRACTION, 20) > 0.1 
emit changes;


select 
    a.TRACTION,
    b.TRACTION,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.8) as linguisticA,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) as linguisticB
from AGV_1_STREAM_wu a 
join AGV_2_STREAM_wu b 
within 7 days 
on FUZZY_JOIN(a.TRACTION, 20, b.TRACTION, 20) = 0.5
WHERE FUZZY_JOIN(a.TRACTION, 20, b.TRACTION, 20) > 0.1 
emit changes;


-- ASSIGN TEST
select w.TRACTION, ASSIGN_LING('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION) from AGV_1_STREAM_WU w emit changes;

-- EXTENDED_AND

select * from AGV_1_STREAM_wu a
where EXTENDED_AND(
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 'normal', 0.4),
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.LATITUDE, 'normal', 0.4)
) emit changes;

select * from AGV_1_STREAM_wu a
where EXTENDED_AND(
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 'normal', 0.4),
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.LATITUDE, 'normal', 0.4),
    AROUND('TR_F;0.1;0.35;0.8;0.99', a.BATTERYPERCENTAGELEFT) > 0.5
>) emit changes;

-- EXTENDED_OR

select * from AGV_1_STREAM_wu a
where EXTENDED_OR(
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 'normal', 0.4),
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.LATITUDE, 'normal', 0.4)
) emit changes;

select * from AGV_1_STREAM_wu a
where EXTENDED_OR(
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 'normal', 0.4),
    VERIFY_IS('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.LATITUDE, 'normal', 0.4),
    AROUND('TR_F;0.1;0.35;0.8;0.99', a.BATTERYPERCENTAGELEFT) > 0.5
>) emit changes;


-- ALARM STATE DETECTION

-- PREPARING STREAMS COUNT
CREATE TABLE SAFE_COUNT_TABLE AS 
    SELECT     
        MAX(ROWTIME) AS RECORD_TIME, 
        ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) AS STATE_NAME, 
        COUNT(s.TRACTION) AS SAFE_COUNTER
        FROM AGV_1_STREAM_wu s
            WINDOW TUMBLING(SIZE 30 SECONDS)
            WHERE ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) = 'safe'
            GROUP BY ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99)
    EMIT CHANGES; 


CREATE TABLE ALARM_COUNT_TABLE AS 
    SELECT     
        MAX(ROWTIME) AS RECORD_TIME, ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) AS STATE_NAME, COUNT(s.TRACTION) AS ALARM_COUNTER
        FROM AGV_1_STREAM_wu s
            WINDOW TUMBLING(SIZE 30 SECONDS)
            WHERE ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) = 'alarm'
            GROUP BY ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99)
    EMIT CHANGES; 


SELECT MAX(SAFE_COUNTER) FROM SAFE_COUNT_TABLE WINDOW SESSION ( 30 SECONDS ) GROUP BY SAFE_COUNTER EMIT CHANGES;


SELECT * FROM SAFE_COUNT_TABLE s
JOIN ALARM_COUNT_TABLE a ON s.RECORD_TIME = a.RECORD_TIME
EMIT CHANGES;

SELECT MAX(s.SAFE_COUNTER), s.WINDOWEND FROM SAFE_COUNT_TABLE s GROUP BY WINDOWEND EMIT CHANGES;




SELECT GET_STATE_NAME(a.SAFE_COUNTER, b.SAFE_COUNTER) FROM SAFE_COUNT_STREAM a 
    JOIN ALARM_COUNT_STREAM b ON a.RECORD_TIME = b.RECORD_TIME

-- ASSIGN_LING_MD('fitting:TR_F;20;30;40;50', a.TRACTION, 0.8) = ASSIGN_LING_MD('fitting:TR_F;20;30;40;50', b.TRACTION, 0.8) 
-- where ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) != 'none' 
-- ERROR ONES

-- PREPARING STREAMS COUNT
CREATE TABLE SAFE_COUNT_TABLE AS 
    SELECT     
        ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) AS STATE_NAME, 
        COUNT(*) AS SAFE_COUNTER,
        MAX(ROWTIME) AS RECORD_TIME, 
        WINDOWSTART AS SESSION_START,
        WINDOWEND AS SESSION_END,
        WINDOWEND - WINDOWSTART AS SESSION_TIME
        FROM AGV_1_STREAM_wu s
            WINDOW TUMBLING(SIZE 30 SECONDS)
            WHERE ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) = 'safe'
            GROUP BY ROWTIME, ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99)
    EMIT CHANGES;

CREATE STREAM A_TEST 
    AS SELECT ASSIGN_TO_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99), COUNT(s.TRACTION) 
        FROM AGV_1_STREAM_wu s 
            WHERE ASSIGN_TO_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99)
            WINDOW TUMBLING (SIZE 30 SECONDS)
            GROUP BY ASSIGN_TO_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99)
    EMIT CHANGES; 

select GET_STATE_NAME(a, b) from  

CREATE STREAM foo AS
    SELECT     
        COUNT(*), MAX(ROWTIME) AS RECORD_TIME, 
        ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) AS STATE_NAME, 
        COUNT(s.TRACTION) AS SAFE_COUNTER
        FROM AGV_1_STREAM_wu s
            WINDOW TUMBLING(SIZE 30 SECONDS)
            WHERE ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99) = 'safe'
            GROUP BY ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.TRACTION, 0.99)
    EMIT CHANGES; 