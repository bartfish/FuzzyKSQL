package pl.bartryb.producer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class AutonomousGuidedVehicle {

    @JsonProperty
    public Integer CRC;

    @JsonProperty
    public Integer Id;

    @JsonProperty
    public Integer MomentaryConsumption1;

    @JsonProperty
    public Integer MomentaryConsumption2;

    @JsonProperty
    public Integer MomentaryConsumption3;

    @JsonProperty
    public Double CumulativeEnergyConsumption1;

    @JsonProperty
    public Double CumulativeEnergyConsumption2;

    @JsonProperty
    public Double CumulativeEnergyConsumption3;

    @JsonProperty
    public Integer RawInputMeasurement1;

    @JsonProperty
    public Integer RawInputMeasurement2;

    @JsonProperty
    public Integer RawInputMeasurement3;

    @JsonProperty
    public Integer CycleCounterNoOk;

    @JsonProperty
    public Integer CycleCounterOk;

    @JsonProperty
    public DateTime Timestamp;

    @JsonProperty
    public long TimestampSeconds;

}
