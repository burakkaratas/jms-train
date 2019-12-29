package net.burakkaratas.learning.p2p;

import java.io.Serializable;

public class Patient implements Serializable {

  private int id;

  private String insuranceProvide;

  private Double copay;

  private Double amountToBeCopy;

  public Patient() {
  }

  public Patient(int id, String insuranceProvide, Double copay, Double amountToBeCopy) {
    this.id = id;
    this.insuranceProvide = insuranceProvide;
    this.copay = copay;
    this.amountToBeCopy = amountToBeCopy;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getInsuranceProvide() {
    return insuranceProvide;
  }

  public void setInsuranceProvide(String insuranceProvide) {
    this.insuranceProvide = insuranceProvide;
  }

  public Double getCopay() {
    return copay;
  }

  public void setCopay(Double copay) {
    this.copay = copay;
  }

  public Double getAmountToBeCopy() {
    return amountToBeCopy;
  }

  public void setAmountToBeCopy(Double amountToBeCopy) {
    this.amountToBeCopy = amountToBeCopy;
  }

  @Override
  public String toString() {
    return "Patient{" +
        "id=" + id +
        ", insuranceProvide='" + insuranceProvide + '\'' +
        ", copay=" + copay +
        ", amountToBeCopy=" + amountToBeCopy +
        '}';
  }
}
