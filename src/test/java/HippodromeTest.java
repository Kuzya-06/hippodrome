import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    // Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException;
    @Test
    @Order(1)
    public void horsesNullShouldNull() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    // Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать сообщение "Horses cannot be null.";
    @Test
    @Order(2)
    public void horsesNullShouldMessage() {

        String message = "1";
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        System.out.println(message);
        assertEquals("Horses cannot be null.", message);
    }

    //Проверить, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException;
    @Test
    @Order(3)
    public void horsesEmptyShouldEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    //Проверить, что при передаче в конструктор пустого списка, выброшенное исключение будет содержать сообщение "Horses cannot be empty.";
    @Test
    @Order(4)
    public void horsesEmptyShouldMessage() {

        String message = "1";
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        System.out.println(message);
        assertEquals("Horses cannot be empty.", message);
    }

    // Проверить, что метод возвращает список, который содержит те же объекты и в той же последовательности, что и список, который был передан в конструктор.
    // При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей;
    @Test
    @Order(5)
    public void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(i, new Horse("Hors-" + (i + 1), i + 0.1, i + 0.5));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        Assertions.assertEquals(horses, hippodrome.getHorses());
    }

    // Проверить, что метод вызывает метод move у всех лошадей.
    // При создании объекта Hippodrome передай в конструктор список из 50 моков лошадей и воспользуйся методом verify.
    @Test
    @Order(6)
    void moveTest() {
        List <Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse:horses) {
            verify(horse).move();
        }
    }

    // Проверить, что метод возвращает лошадь с самым большим значением distance.
    @Test
    @Order(7)
    void getWinnerTest() {
        Horse horse2 = new Horse("MoonBlan", 1, 4.5);
        Horse horse4 = new Horse("MoonBlan", 1, 2.5);
        Horse horse1 = new Horse("MoonBlan", 1, 5.5);
        Horse horse3 = new Horse("MoonBlan", 1, 3.5);

        Hippodrome hippodrome = new Hippodrome(List.of(horse2,horse4, horse1, horse3));

        assertSame(horse1, hippodrome.getWinner());
    }
}