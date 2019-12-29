package net.burakkaratas.learning.filter;

import java.io.Serializable;

public class Claim implements Serializable {

  private int hospitalId;

  private String doctorName;

  private String doctorType;

  private double claimAmount;

  public int getHospitalId() {
    return hospitalId;
  }

  public void setHospitalId(int hospitalId) {
    this.hospitalId = hospitalId;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public String getDoctorType() {
    return doctorType;
  }

  public void setDoctorType(String doctorType) {
    this.doctorType = doctorType;
  }

  public double getClaimAmount() {
    return claimAmount;
  }

  public void setClaimAmount(double claimAmount) {
    this.claimAmount = claimAmount;
  }

  @Override
  public String toString() {
    return "Claim{" +
        "hospitalId=" + hospitalId +
        ", doctorName='" + doctorName + '\'' +
        ", doctorType='" + doctorType + '\'' +
        ", claimAmount=" + claimAmount +
        '}';
  }
}
