package dataparsingutils;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public int getMonth() {
        return month;
    }
    public int getNumberOfPatients() {
        return numberOfPatients;
    }
    public double getRefund() {
        return refund;
    }
}
