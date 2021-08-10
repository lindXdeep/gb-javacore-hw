import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {

  private Map<String, List<String>> data = new HashMap<>();
  private List<String> phones;

  public void add(String lastname, String phone) {

    if (!data.containsKey(lastname)) {
      data.put(lastname, new ArrayList<String>());
    }
    
    data.get(lastname).add(phone);
  }

  public List<String> get(final String lastName) {

    if (data.containsKey(lastName)) {
      phones = data.get(lastName);
    }

    return phones;
  }
}
