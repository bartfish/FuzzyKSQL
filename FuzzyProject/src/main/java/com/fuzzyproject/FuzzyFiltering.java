package com.fuzzyproject;


import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfDescription;

import java.util.Arrays;
import java.util.OptionalDouble;

@UdfDescription(name = "fuzzy_filtering", description = "joins fuzzy strings")
public class FuzzyFiltering {

    /// LEVENSTEIN
    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    private int calculateDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }
    /// /LEVENSTEIN

    @Udf(description = "compare 2 strings")
    public int fuzzyfiltering(
            @UdfParameter(value = "V1", description = "the first value") final String v1,
            @UdfParameter(value = "V2", description = "the second value") final String v2) {
        return calculateDistance(v1, v2);
    }

    // select * from ... where fuzzy_filtering(a.string1, b.string1) < 6 (where 6 is a constant value defining on how similar the words are to each other)

}