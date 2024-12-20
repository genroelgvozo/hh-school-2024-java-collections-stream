package tasks;

import common.Person;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Task3Test {

  private Person person1;
  private Person person2;
  private Person person3;
  private Person person4;

  @BeforeEach
  void before() {
    Instant time = Instant.now();
    person1 = new Person(1, "Oleg", "Ivanov", null, time);
    person2 = new Person(2, "Vasya", "Petrov", null, time);
    person3 = new Person(3, "Oleg", "Petrov", null, time.plusSeconds(1));
    person4 = new Person(4, "Oleg", "Ivanov", null, time.plusSeconds(1));
  }

  @Test
  public void test() {
    assertEquals(List.of(person1, person4, person3, person2), Task3.sort(List.of(person1, person2, person3, person4)));
    assertEquals(List.of(person1, person4, person3, person2), Task3.sort(List.of(person4, person3, person2, person1)));
  }

  @Test
  public void testEmpty() {
    assertEquals(Collections.emptyList(), Task3.sort(Collections.emptyList()));
  }
}
