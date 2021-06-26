/**
 * Main
 */
public class App {

  public static void main(String[] args) {
    
    Employee[] staff = { 
        new Employee("User1", "lastname1", "secname1", "manger1", "1@1.com", 123456789, 123.45f, 70),
        new Employee("User2", "lastname2", "secname2", "manger2", "2@2.com", 678678677, 234.54f, 19),
        new Employee("User3", "lastname3", "secname3", "manger3", "3@3.com", 456456546, 553.89f, 26),
        new Employee("User4", "lastname4", "secname4", "manger4", "4@4.com", 234324324, 567.43f, 34),
        new Employee("User5", "lastname5", "secname5", "manger5", "5@5.com", 234234234, 323.12f, 50), 
    };

    for (Employee employee : staff) {
      if (employee.getAge() > 40) {
        System.out.println(employee.toString());
      }
    }
  }
}