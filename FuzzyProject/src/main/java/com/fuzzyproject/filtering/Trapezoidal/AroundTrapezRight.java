package com.fuzzyproject.filtering.Trapezoidal;

import com.fuzzyproject.functions.Trapezoidal;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(name = "AROUND_TRAPEZ_R", description = "checks with configured trapezoidal function")
public class AroundTrapezRight {

    @Udf(description = "Check whether value fits trapezoidal part (right side of trapez) function defined in arguments")
    public Double AroundTrapezRight(
            @UdfParameter(value = "FUNCTION_DEFINED", description = "type CONST (const for taking configured triangular) OR LET (with let pass 3 additional parameters)") final String functionDefined,
            @UdfParameter(value = "VALUE", description = "the second value") final Double searchedValue,
            @UdfParameter(value = "RIGHT_TOP", description = "right top value") final Double rightTop,
            @UdfParameter(value = "RIGHT", description = "right value") final Double right
            ) {

        if (functionDefined == "LET") {
            return Trapezoidal.TrapezoidalRight(rightTop, right, searchedValue);
        }
        return Trapezoidal.TrapezoidalRight(rightTop, right, searchedValue); // DEFINED CONFIGURED TRAPEZOIDAL RIGHT PART LATER

    }

}