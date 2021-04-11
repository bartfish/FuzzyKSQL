package com.fuzzyproject.fuzzyudfs.aggregations;


import com.fuzzyproject.functions.Triangular;
import com.fuzzyproject.functions.mixed.AssignLinguistic;
import com.fuzzyproject.functions.mixed.LinguisticDef;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UdfDescription(name = "FUZZY_JOIN", description = "join values which are at the same level of membership function")
public class FJoin {

    private static final Double INCREMENT_PARAM = 0.01;

    @Udf(description = "Check whether value fits linguistic match defined in arguments")
    public static String FJoin(
            @UdfParameter(value = "value1", description = "searchedValue") final Double value1,
            @UdfParameter(value = "spreadvalue1", description = "searchedValue") final Double spreadValue1,
            @UdfParameter(value = "value2", description = "searchedValue") final Double value2,
            @UdfParameter(value = "spreadvalue1", description = "searchedValue") final Double spreadValue2
    ) {

        // create triangular range based on value and spreadvalue
        // the first triangle
        Double leftSide1 = value1 - spreadValue1;
        Double rightSide1 = value1 + spreadValue2;
        // create triangular function 1
        String triangularDefinition1 = "TRIA:" + leftSide1 + ";" + value1 + ";" + rightSide1;

        // create triangular function 2
        Double leftSide2 = value2 - spreadValue2;
        Double rightSide2 = value2 + spreadValue2;
        String triangularDefinition2 = "TRIA:" + leftSide2 + ";" + value2 + ";" + rightSide2;

        System.out.println(triangularDefinition1);
        System.out.println(triangularDefinition2);

        // check if triangular functions meet anywhere
        List<Double> intersectionValues = new ArrayList<Double>();

        // identify which triangle should go first value1 or value2 may not be always ordered
        Double startX = leftSide1;
        Double endX = rightSide2;
        if (value1 > value2) {
            startX = leftSide2;
            endX = rightSide1;
        }

        while (startX <= endX) {

            Double firstResult = Triangular.TriangularFunction(leftSide1, value1, rightSide1, startX);
            Double secondResult = Triangular.TriangularFunction(leftSide2, value2, rightSide2, startX);

            firstResult =  Math.floor(firstResult * 10) / 10;
            secondResult = Math.floor(secondResult * 10) / 10;

            System.out.println("startX: " + startX + " firstResult: " + firstResult + " secondResult: " + secondResult);

            if (firstResult == secondResult) {
                intersectionValues.add(firstResult);
            }

            startX += INCREMENT_PARAM;
        }

        System.out.println(intersectionValues);

        // go from left most side of the first function to the right most of the other and search for a common points
        // if the do, then return membership degree (the common value)
        // otherwise, return membership degree equal to 0
        return "string";
    }

}