/**
 * App
 */
public class App {

  public static void main(String[] args) {

    Plate plate = new Plate(100);
    plate.setEat(90);

    System.out.println("Еды в миске: " + plate.getCapacity());

    Cat[] cats = { 
      new Cat(22), 
      new Cat(17), 
      new Cat(11), 
      new Cat(21), 
      new Cat(25)
    };

    
    for (int i = 0; i < cats.length; i++) {
      
      cats[i].eat(plate);

      if (!cats[i].getStatus()) {
        System.out.println("кот сьел " + cats[i].getEat());
        plate.addEat(20);

        cats[i].eat(plate);
      }
    }
      
    System.out.println(plate.getValue());


    for (Cat cat : cats) {
      System.out.println("Кот сожрал еды: " + cat.getEat()  + " : " 
                                            + ((cat.getStatus()) ? "кот сыт" : "кот еще голодный"));
    }
  }
}