package com.fuzzyproject.utils;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(name = "GET_STATE_NAME", description = "Get state name based on 2 values. Returns 'alarm' or 'safe'")
public class GetStateName {

    private static final String ALARM_STATE_NAME = "ALARM";
    private static final String SAFE_STATE_NAME = "SAFE";
    private static final Integer DIFFERENCE_FACTOR = 3;

    @Udf(description = "Compare 2 integers and define the state type -> alarm state or safe state")
    public static String GetStateName(
            @UdfParameter(value = "alarmStateCount", description = "alarm counter: integer") final Integer alarmCounter,
            @UdfParameter(value = "safeStateCount", description = "safe state counter: integer") final Integer safeCounter
    ) {
        Integer difference = alarmCounter - safeCounter;
        System.out.println(difference);
        if (difference >= DIFFERENCE_FACTOR) { // if alarmCounter is greater than safeCounter, then the alarm state should be on
            return ALARM_STATE_NAME;
        } else if (difference <= -1*DIFFERENCE_FACTOR) {
            return SAFE_STATE_NAME;
        }
        return "";
    }
}