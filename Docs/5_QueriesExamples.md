
## Filtering/Searching
```sql
-- AROUND_TRIANGULAR
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRIANGULAR('LET', w.TRACTION, 50, 80, 70) > 0.6) EMIT CHANGES;

-- AROUND_TRAPEZ
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRAPEZ('LET', w.TRACTION, 20, 40, 50, 60) > 0.4) EMIT CHANGES;

-- AROUND_TRAPEZ_L
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRAPEZ_L('LET', w.TRACTION, 20, 40) > 0.6) EMIT CHANGES;

-- AROUND_TRAPEZ_R
select * from AGV_1_STREAM_WU w WHERE (AROUND_TRAPEZ_R('LET', w.TRACTION, 40, 80) > 0.9) EMIT CHANGES;

-- AROUND_GAUSS
select * from AGV_1_STREAM_WU w WHERE (AROUND_GAUSS('LET', w.TRACTION, 40, 10) > 0.9) EMIT CHANGES;

-- OVERALL AROUND
select * from AGV_1_STREAM_WU w WHERE (AROUND('TRIA;40;60;80', w.TRACTION) > 0.6) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND('TR_F;40;60;80;90', w.TRACTION) > 0.6) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND('TR_L;40;60', w.TRACTION) > 0.6) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND('TR_R;40;60;', w.TRACTION) > 0.6) EMIT CHANGES;
select * from AGV_1_STREAM_WU w WHERE (AROUND('GAUS;40;60;', w.TRACTION) > 0.6) EMIT CHANGES;
```

## Fuzzy AND & OR
```sql
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
```

## Linguistic assignment
```sql
-- #1
SELECT 
ASSIGN_LING('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION), COUNT(*) 
FROM AGV_1_STREAM_old w WINDOW TUMBLING (SIZE 24 HOUR) 
GROUP BY 
ASSIGN_LING('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION)
EMIT CHANGES;

-- #2
SELECT 
ASSIGN_LING('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1), 
a.CumulativeEnergyConsumption1 FROM NEW_AGV_1_STREAM a EMIT CHANGES

-- #2
SELECT 
a.CumulativeEnergyConsumption1,
b.CumulativeEnergyConsumption1,
ASSIGN_LING('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1),
ASSIGN_LING('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', b.CumulativeEnergyConsumption1)
FROM NEW_AGV_1_STREAM a 
INNER JOIN NEW_AGV_2_STREAM b 
WITHIN 7 DAYS 
ON 
ASSIGN_LING('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1) = ASSIGN_LING('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', b.CumulativeEnergyConsumption1)
EMIT CHANGES;
```

## Fuzzy join using linguistic assignment
```sql
-- FUZZY JOIN based on linguistic assignments
select 
    a.TRACTION,
    b.TRACTION,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.7) as linguisticA,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.7) as linguisticB
from AGV_1_STREAM_wu a 
inner join AGV_2_STREAM_wu b 
within 7 days 
on 
ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.7) = ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.6) 
where ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.7) != 'none' 
emit changes;
```

## Fuzzy join using intersection of triangles
```sql
select 
    a.TRACTION,
    b.TRACTION,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', a.TRACTION, 0.8) as linguisticA,
    ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', b.TRACTION, 0.8) as linguisticB
from AGV_1_STREAM_wu a, AGV_2_STREAM_wu a
where FUZZY_JOIN(a.TRACTION, 20, b.TRACTION, 20) > 0.3 
emit changes;
```


## Fuzzy grouping using linguistic assignment
```sql
-- FUZZY_GROUP BY
-- MORE NONES IN THE ONE BELOW
SELECT 
ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7), COUNT(*) 
FROM AGV_1_STREAM_WU w WINDOW TUMBLING (SIZE 24 HOUR) 
GROUP BY ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7) EMIT CHANGES;

-- DO NOT DISPLAY NONES
SELECT 
ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7) AS STATUS_NAME, COUNT(*) AS STATUS_COUNTER
FROM AGV_1_STREAM_WU w WINDOW TUMBLING (SIZE 24 HOUR) 
WHERE ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7) != 'none'
GROUP BY ASSIGN_LING_MD('low:TR_F;20;30;40;50/normal:TR_F;40;50;60;70/high:TR_F;50;80;90;100', w.TRACTION, 0.7) EMIT CHANGES;
```

## Finding the membership degree of specific linguistic value
```sql
select 
    a.CumulativeEnergyConsumption1, 
    ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) as linguisticA,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'low') AS LOW,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'normal') AS NORMAL,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'high') AS HIGH
from NEW_AGV_1_STREAM a 
where 
ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) = 'high'
emit changes;
```

## State detection using KSQL only
```sql
---------- ALARM STATE VS SAFE STATE
CREATE TABLE ALARM_ANALYSIS AS
    SELECT     
        TIMESTAMPTOSTRING(WINDOWSTART,'yyyy-MM-dd HH:mm:ss','Europe/London') AS WINDOW_START_TS,  
        ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.WHEELSTEMPERATURE, 0.99) AS STATE_NAME,
        COUNT(ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.WHEELSTEMPERATURE, 0.99)) AS SAFE_COUNTER
        FROM AGV_1_STREAM_WU s 
            WINDOW TUMBLING(SIZE 1 MINUTE)
            WHERE ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.WHEELSTEMPERATURE, 0.99) != 'none'
            GROUP BY ASSIGN_LING_MD('safe:TR_F;20;30;70;80/alarm:TR_F;40;50;80;90', s.WHEELSTEMPERATURE, 0.99) 
            HAVING COUNT(s.WHEELSTEMPERATURE) > 3
    EMIT CHANGES; 

SELECT 
    STATE_NAME,
    (CASE WHEN (STATE_NAME = 'safe' AND SAFE_COUNTER >= 4) THEN 'SAFE STATE TURNED ON!' ELSE '' END) AS SAFE_MESSAGE,
    (CASE WHEN (STATE_NAME = 'alarm' AND SAFE_COUNTER >= 4) THEN 'ALARM STATE TURNED ON!' ELSE '' END) AS ALARM_MESSAGE,
    WINDOW_START_TS,
    SAFE_COUNTER
    FROM ALARM_ANALYSIS
    EMIT CHANGES;
```

## Plans for improvements

* Implementation of the state detection done on Kafka Streams API instead of KSQL only
* Changing all bruteforce/high resource usage (not optimal complexities) to more efficient approaches