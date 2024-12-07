package dataparsingutils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrugProgramCostsData {
    public DrugProgramCostsData(int year, int month, int numberOfPatients, double refund) {
        this.year = year;
        this.month = month;
        this.numberOfPatients = numberOfPatients;
        this.refund = refund;
    }
    @JsonProperty("year")
    private int year;

    @JsonProperty("month")
    private int month;

    @JsonProperty("number-of-patients")
    private int numberOfPatients;

    @JsonProperty("refund")
    private double refund;
}
