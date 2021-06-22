/**
 * Employee
 */
public class Employee {

  private String firstname;
  private String lastname;
  private String secondaryname;
  private String occupation;
  private String email;
  private long telephone;
  private float salary;
  private int age;

  public Employee() {

  }

  public Employee(final String firstname, 
                  final String lastname, 
                  final String secondaryname, 
                  final String occupation, 
                  final String email,
                  final long telephone, 
                  float salary, 
                  final int age) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.secondaryname = secondaryname;
    this.occupation = occupation;
    this.email = email;
    this.telephone = telephone;
    this.salary = salary;
    this.age = age;
  }
  
  @Override
  public String toString() {
    return
      " firstname=" + getFirstname() +
      " lastname=" + getLastname() +
      " secondaryname=" + getSecondaryname() +
      " occupation=" + getOccupation() +
      " email=" + getEmail() +
      " telephone=" + getTelephone() +
      " salary=" + getSalary()  +
      " age=" + getAge();
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getSecondaryname() {
    return this.secondaryname;
  }

  public void setSecondaryname(String secondaryname) {
    this.secondaryname = secondaryname;
  }

  public String getOccupation() {
    return this.occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getTelephone() {
    return this.telephone;
  }

  public void setTelephone(long telephone) {
    this.telephone = telephone;
  }

  public float getSalary() {
    return this.salary;
  }

  public void setSalary(float salary) {
    this.salary = salary;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}