-- 1

-- AROUND_TRIANGULAR
select * from NEW_AGV_1_STREAM w WHERE (AROUND_TRIANGULAR('LET', w.CumulativeEnergyConsumption1, 1000, 5000, 4500) > 0.8) EMIT CHANGES; -- OK
-- select * from AGV_1_STREAM_WU w WHERE (AROUND_TRIANGULAR('CONST', w.TRACTION) > 0.6) EMIT CHANGES; -- TO BE IMPLEMENTED AND TESTED

-- AROUND_TRAPEZ
select * from NEW_AGV_1_STREAM w WHERE (AROUND_TRAPEZ('LET', w.CumulativeEnergyConsumption1, 1000, 3500, 4500, 5000) > 0.8) EMIT CHANGES; -- OK

-- 2

--2.1
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


--2.2 fuzzy
select 
    a.CumulativeEnergyConsumption1, 
    ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) as linguisticA,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'low') AS LOW,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'normal') AS NORMAL,
    GET_MEMBERSHIP_DEGREE('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 'high') AS HIGH
from NEW_AGV_1_STREAM a 
where 
ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) == 'high'
emit changes;


-- 2.2 crisp
select 
    a.CumulativeEnergyConsumption1
from NEW_AGV_1_STREAM a 
where 
a.CumulativeEnergyConsumption1 >= 4200 AND a.CumulativeEnergyConsumption1 <= 4700
emit changes;


-- 3 - fuzzy
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
ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', a.CumulativeEnergyConsumption1, 0.7) = ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F
;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', b.CumulativeEnergyConsumption1, 0.6) 
where ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', b.CumulativeEnergyConsumption1, 0.7) != 'none' 
emit changes;


-- 3 - crisp
select 
    a.CumulativeEnergyConsumption1,
    b.CumulativeEnergyConsumption1
from NEW_AGV_1_STREAM a 
inner join NEW_AGV_2_STREAM b within 7 days on a.CumulativeEnergyConsumption1 = b.CumulativeEnergyConsumption1 
where 
a.CumulativeEnergyConsumption1 > 500 and a.CumulativeEnergyConsumption1 < 5000 
and 
b.CumulativeEnergyConsumption1 > 500 and b.CumulativeEnergyConsumption1 < 5000
emit changes;

-- 4 - fuzzy grouping
SELECT 
w.CumulativeEnergyConsumption3,
ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', w.CumulativeEnergyConsumption3, 0.7) AS STATUS_NAME, COUNT(*) AS STATUS_COUNTER
FROM NEW_AGV_1_STREAM w WINDOW TUMBLING (SIZE 24 HOUR) 
WHERE ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', w.CumulativeEnergyConsumption3, 0.7) != 'none'
GROUP BY ASSIGN_LING_MD('low:TR_F;500;1500;2000;2500/normal:TR_F;2100;2400;3000;3500/high:TR_F;3900;4200;4500;5000', w.CumulativeEnergyConsumption3, 0.7), w.CumulativeEnergyConsumption3 EMIT CHANGES;

-- 4 - crisp grouping
SELECT 
w.CumulativeEnergyConsumption3, COUNT(*) AS STATUS_COUNTER
FROM NEW_AGV_1_STREAM w WINDOW TUMBLING (SIZE 24 HOUR) 
WHERE w.CumulativeEnergyConsumption3 > 500 and w.CumulativeEnergyConsumption3 < 5000
GROUP BY w.CumulativeEnergyConsumption3 
EMIT CHANGES;



-- 5 - FUZZY
SELECT 
    a.ID,
    b.ID,
    FUZZY_JOIN(a.CumulativeEnergyConsumption3, 1000.0, b.CumulativeEnergyConsumption3, 1000.0) AS MEMBERSHIP_DEGREE,
    a.CumulativeEnergyConsumption3,
    b.CumulativeEnergyConsumption3
FROM NEW_AGV_1_STREAM a 
JOIN NEW_AGV_2_STREAM b WITHIN 7 DAYS ON a.ID = b.ID
WHERE FUZZY_JOIN(a.CumulativeEnergyConsumption3, 1000.0, b.CumulativeEnergyConsumption3, 1000.0) > 0.7
EMIT CHANGES;

-- crisp approach here is impossible
-- 6 - fuzzy - STATE DETECTION
CREATE TABLE THE_STATE_DETECTION AS
SELECT     
      TIMESTAMPTOSTRING(WINDOWSTART,'yyyy-MM-dd HH:mm:ss','Europe/London') AS WINDOW_START_TS,  
      ASSIGN_LING_MD('safe:TR_F;500;1500;2000;2500/alarm:TR_F;3900;4200;4500;5000', s.CumulativeEnergyConsumption1, 0.99) AS STATE_NAME,
      COUNT(ASSIGN_LING_MD('safe:TR_F;500;1500;2000;2500/alarm:TR_F;3900;4200;4500;5000', s.CumulativeEnergyConsumption1, 0.99)) AS SAFE_COUNTER
    FROM NEW_AGV_1_STREAM s 
    WINDOW HOPPING(SIZE 1 MINUTE, ADVANCE BY 10 SECONDS)            
    WHERE ASSIGN_LING_MD('safe:TR_F;500;1500;2000;2500/alarm:TR_F;3900;4200;4500;5000', s.CumulativeEnergyConsumption1, 0.99) != 'none'
    GROUP BY ASSIGN_LING_MD('safe:TR_F;500;1500;2000;2500/alarm:TR_F;3900;4200;4500;5000', s.CumulativeEnergyConsumption1, 0.99)
    HAVING COUNT(ASSIGN_LING_MD('safe:TR_F;500;1500;2000;2500/alarm:TR_F;3900;4200;4500;5000', s.CumulativeEnergyConsumption1, 0.99)) > 3
   EMIT CHANGES;

SELECT 
    STATE_NAME,
    (CASE WHEN (STATE_NAME = 'safe' AND SAFE_COUNTER >= 4) THEN 'SAFE STATE TURNED ON!' ELSE '' END) AS SAFE_MESSAGE,
    (CASE WHEN (STATE_NAME = 'alarm' AND SAFE_COUNTER >= 4) THEN 'ALARM STATE TURNED ON!' ELSE '' END) AS ALARM_MESSAGE,
    WINDOW_START_TS,
    SAFE_COUNTER
    FROM THE_STATE_DETECTION
    EMIT CHANGES; 
