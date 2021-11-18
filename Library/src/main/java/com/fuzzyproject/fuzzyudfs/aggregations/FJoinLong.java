package com.fuzzyproject.fuzzyudfs.aggregations;


import com.fuzzyproject.functions.Triangular;
import com.fuzzyproject.functions.TriangularLong;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UdfDescription(name = "FUZZY_JOIN_L", description = "join values which are at the same level of membership function")
public class FJoinLong {

    private static final Double INCREMENT_PARAM = 1.0;

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static Double FJoinLong(
            @UdfParameter(value = "value1", description = "searchedValue") final Long value1,
            @UdfParameter(value = "spreadvalue1", description = "searchedValue") final Integer spreadValue1,
            @UdfParameter(value = "value2", description = "searchedValue") final Long value2,
            @UdfParameter(value = "spreadvalue1", description = "searchedValue") final Integer spreadValue2
    ) {

        // create triangular range based on value and spreadvalue
        // the first triangle
        Long leftSide1 = value1 - spreadValue1;
        Long rightSide1 = value1 + spreadValue2;
        // create triangular function 1
        String triangularDefinition1 = "TRIA:" + leftSide1 + ";" + value1 + ";" + rightSide1;

        // create triangular function 2
        Long leftSide2 = value2 - spreadValue2;
        Long rightSide2 = value2 + spreadValue2;
        String triangularDefinition2 = "TRIA:" + leftSide2 + ";" + value2 + ";" + rightSide2;

        System.out.println(triangularDefinition1);
        System.out.println(triangularDefinition2);

        // check if triangular functions meet anywhere
        List<Double> intersectionValues = new ArrayList<Double>();

        // identify which triangle should go first value1 or value2 may not be always ordered
        Long startX = leftSide1;
        Long endX = rightSide2;
        if (value1 > value2) {
            startX = leftSide2;
            endX = rightSide1;
        }

        for(Long startValue = startX; startValue < endX; startValue++) {
            Double firstResult = TriangularLong.TriangularFunction(leftSide1, value1, rightSide2, startValue);
            Double secondResult = TriangularLong.TriangularFunction(leftSide2, value2, rightSide1, startValue);

            System.out.println(firstResult + " " + secondResult);

            if (Double.compare(firstResult, secondResult) == 0) {
                intersectionValues.add(firstResult);
            }
        }
        if (intersectionValues.size() > 0) {
            System.out.println(Collections.max(intersectionValues));
            return Collections.max(intersectionValues);
        }
        return 0.0;
        // go from left most side of the first function to the right most of the other and search for a common points
        // if the do, then return membership degree (the common value)
        // otherwise, return membership degree equal to 0
    }

}