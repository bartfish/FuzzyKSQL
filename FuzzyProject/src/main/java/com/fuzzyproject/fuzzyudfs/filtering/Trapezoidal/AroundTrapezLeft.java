package com.fuzzyproject.fuzzyudfs.filtering.Trapezoidal;

import com.fuzzyproject.functions.Trapezoidal;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(name = "AROUND_TRAPEZ_L", description = "checks with configured trapezoidal function")
public class AroundTrapezLeft {

    @Udf(description = "Check whether value fits trapezoidal (left side of trapez) function defined in arguments")
    public Double AroundTrapezLeft(
            @UdfParameter(value = "FUNCTION_DEFINED", description = "type CONST (const for taking configured triangular) OR LET (with let pass 3 additional parameters)") final String functionDefined,
            @UdfParameter(value = "VALUE", description = "the second value") final Double searchedValue,
            @UdfParameter(value = "LEFT", description = "Left value") final Double left,
            @UdfParameter(value = "LEFT_TOP", description = "Left value") final Double leftTop
            ) {

        if (functionDefined == "LET") {
            return Trapezoidal.TrapezoidalLeft(left, leftTop, searchedValue);
        }
        return Trapezoidal.TrapezoidalLeft(left, leftTop, searchedValue); // DEFINED CONFIGURED TRAPEZOIDAL LEFT PART LATER

    }

}