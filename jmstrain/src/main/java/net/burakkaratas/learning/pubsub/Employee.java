package net.burakkaratas.learning.pubsub;

import java.io.Serializable;

public class Employee implements Serializable {

  private int id;

  private String firstName;
  private String designation;

  public Employee() {
  }

  public Employee(int id, String firstName, String designation) {
    this.id = id;
    this.firstName = firstName;
    this.designation = designation;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", designation='" + designation + '\'' +
        '}';
  }
}
