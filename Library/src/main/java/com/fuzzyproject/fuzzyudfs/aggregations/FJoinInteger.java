package com.fuzzyproject.fuzzyudfs.aggregations;


import com.fuzzyproject.functions.Triangular;
import com.fuzzyproject.functions.mixed.AssignLinguistic;
import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@UdfDescription(name = "FUZZY_JOIN_INT", description = "join values which are at the same level of membership function")
public class FJoinInteger {

    private static final Double INCREMENT_PARAM = 1.0;

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static Double FJoinInteger(
            @UdfParameter(value = "value1", description = "searchedValue") Integer value1,
            @UdfParameter(value = "spreadvalue1", description = "searchedValue") final Integer spreadValue1,
            @UdfParameter(value = "value2", description = "searchedValue") Integer value2,
            @UdfParameter(value = "spreadvalue1", description = "searchedValue") final Integer spreadValue2
    ) {

        // convert to 2 decimal places

        // create triangular range based on value and spreadvalue
        // the first triangle
        Integer leftSide1 = value1 - spreadValue1;
        Integer rightSide1 = value1 + spreadValue2;
        // create triangular function 1
        String triangularDefinition1 = "TRIA:" + leftSide1 + ";" + value1 + ";" + rightSide1;

        // create triangular function 2
        Integer leftSide2 = value2 - spreadValue2;
        Integer rightSide2 = value2 + spreadValue2;
        String triangularDefinition2 = "TRIA:" + leftSide2 + ";" + value2 + ";" + rightSide2;

        System.out.println(triangularDefinition1);
        System.out.println(triangularDefinition2);

        // check if triangular functions meet anywhere
        List<Double> intersectionValues = new ArrayList<Double>();

        // identify which triangle should go first value1 or value2 may not be always ordered
        Integer startX = leftSide1;
        Integer endX = rightSide2;
        if (value1 > value2) {
            startX = leftSide2;
            endX = rightSide1;
        }

        final int multiplicationFactor = 1000;

        Integer startXx = startX; // * multiplicationFactor;
        Integer endXx = endX; // * multiplicationFactor;

        Integer lSide1 = leftSide1; // * multiplicationFactor;
        Integer rSide1 = rightSide1; // * multiplicationFactor;
        Integer v1 = value1; // * multiplicationFactor;

        Integer lSide2 = leftSide2; // * multiplicationFactor;
        Integer rSide2 = rightSide2; // * multiplicationFactor;
        Integer v2 = value2; // * multiplicationFactor;

        System.out.println("lSide1: " + lSide1 + " rSide1: " + rSide1 + " v1: " + v1 + " startXx: " + startXx);

        for(Integer startValue = startXx.intValue(); startValue < endXx; startValue++) {
            Double firstResult = Triangular.TriangularFunction(Double.valueOf(lSide1), Double.valueOf(v1), Double.valueOf(rSide1), startValue.doubleValue());
            Double secondResult = Triangular.TriangularFunction(Double.valueOf(lSide2), Double.valueOf(v2), Double.valueOf(rSide2), startValue.doubleValue());

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