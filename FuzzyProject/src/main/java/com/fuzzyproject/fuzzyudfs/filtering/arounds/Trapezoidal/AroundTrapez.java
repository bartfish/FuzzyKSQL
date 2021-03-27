package com.fuzzyproject.fuzzyudfs.filtering.arounds.Trapezoidal;

import com.fuzzyproject.functions.Trapezoidal;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

@UdfDescription(name = "AROUND_TRAPEZ", description = "checks with configured trapezoidal function")
public class AroundTrapez {

    @Udf(description = "Check whether value fits trapezoidal function defined in arguments")
    public Double AroundTrapez(
            @UdfParameter(value = "FUNCTION_DEFINED", description = "type CONST (const for taking configured triangular) OR LET (with let pass 3 additional parameters)") final String functionDefined,
            @UdfParameter(value = "VALUE", description = "the second value") final Double searchedValue,
            @UdfParameter(value = "LEFT", description = "Left value") final Double left,
            @UdfParameter(value = "LEFT_TOP", description = "Left value") final Double leftTop,
            @UdfParameter(value = "RIGHT_TOP", description = "Right value") final Double rightTop,
            @UdfParameter(value = "RIGHT", description = "Right value") final Double right
            ) {

        if (functionDefined == "LET") {
            return Trapezoidal.TrapezoidalFunction(left, leftTop, rightTop, right, searchedValue);
        }
        return Trapezoidal.TrapezoidalFunction(left, leftTop, rightTop, right, searchedValue); // DEFINED CONFIGURED TRAPEZOIDAL LATER

    }

}