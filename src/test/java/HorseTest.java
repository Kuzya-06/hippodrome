import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    @DisplayName("при передаче в конструктор первым параметром null, будет выброшено IllegalArgumentException")
    @Order(1)
    public void horseNameShouldNull() {

        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0.2, 0.9));
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0.9));
    }

    @Test
    @DisplayName("при передаче в конструктор первым параметром null, выброшенное исключение будет содержать сообщение \"Name cannot be null.\"")
    @Order(2)
    public void horseNameNullShouldMessage() {
        Horse horse2;
        Horse horse3;
        String message = "1";
        try {
            horse3 = new Horse(null, 0.2, 0.9);
            horse2 = new Horse(null, 0.2);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        System.out.println(message);
        assertEquals("Name cannot be null.", message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\r", "\t", "\f"})
    @DisplayName("при передаче в конструктор первым параметром пустой строки, будет выброшено IllegalArgumentException")
    @Order(3)
    public void horseNameShouldBlank(String n) {

        assertThrows(IllegalArgumentException.class, () -> new Horse(n, 0.2, 0.9));
        assertThrows(IllegalArgumentException.class, () -> new Horse(n, 0.9));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\r", "\t", "\f"})
    @DisplayName("при передаче в конструктор первым параметром пустой строки, выброшенное исключение будет содержать сообщение \"Name cannot be blank.\"")
    @Order(4)
    public void horseNameBlankShouldMessage(String n) {
        Horse horse2;
        Horse horse3;
        String name = n;
        String message = "1";
        try {
            horse3 = new Horse(name, 0.2, 0.9);
            horse2 = new Horse(name, 0.2);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        System.out.println(message);
        assertEquals("Name cannot be blank.", message);
    }

    @Test
    @DisplayName("при передаче в конструктор вторым параметром отрицательное число, будет выброшено IllegalArgumentException")
    @Order(5)
    public void horseSpeedShouldNegative() {

        assertThrows(IllegalArgumentException.class, () -> new Horse("Tor", -0.2, 0.9));
        assertThrows(IllegalArgumentException.class, () -> new Horse("Tor", -0.9));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -2.1, -99.99, -0.0001})
    @DisplayName("при передаче в конструктор вторым параметром отрицательное число, выброшенное исключение будет содержать сообщение \"Name cannot be негатив.\"")
    @Order(6)
    public void horseSpeedShouldMessage(double i) {
        Horse horse2;
        Horse horse3;

        String message = "1";
        try {
            horse3 = new Horse("Tor", i, 0.9);
            horse2 = new Horse("Tor", i);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        System.out.println(message);
        assertEquals("Speed cannot be negative.", message);
    }

    @Test
    @DisplayName("при передаче в конструктор третьим параметром отрицательное число, будет выброшено IllegalArgumentException")
    @Order(7)
    public void horseDistanceShouldNegative() {

        assertThrows(IllegalArgumentException.class, () -> new Horse("Tor", 0.2, -0.9));

    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -2.1, -99.99, -0.0001})
    @DisplayName("при передаче в конструктор третьим параметром отрицательное число, выброшенное исключение будет содержать сообщение \"Distance cannot be negative.\"")
    @Order(8)
    public void horseDistanceShouldMessage(double i) {
        Horse horse3;
        String message = "1";
        try {
            horse3 = new Horse("Tor", 0.5, -0.9);

        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        System.out.println(message);
        assertEquals("Distance cannot be negative.", message);
    }


    @Test
    @Order(9)
    public void getNameTest() {
        Horse h = new Horse("MoonBlan", 1.1, 0.8);
        Assertions.assertEquals("MoonBlan", h.getName());
    }

    @Test
    @Order(10)
    public void getSpeedTest() {
        Horse h = new Horse("MoonBlan", 1.1, 0.8);
        Assertions.assertEquals(1.1, h.getSpeed());
    }

    @Test
    @Order(11)
    public void getDistanceTest() {
        Horse h = new Horse("MoonBlan", 1.1, 0.8);
        Assertions.assertEquals(0.8, h.getDistance());
    }

    @Test
    @Order(12)
    public void getDistanceZeroTest() {
        Horse h = new Horse("MoonBlan", 1.1);
        Assertions.assertEquals(0, h.getDistance());
    }

    // Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9. Для этого нужно использовать MockedStatic и его метод verify;
    @ParameterizedTest
    @CsvSource({"0.20, 0.9"})
    @Order(13)
    public void moveTest(double s, double d) {

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("MoonBlan", 1.1, 123).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(s, d));
        }
    }

    // Проверить, что метод присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9).
    // Для этого нужно замокать getRandomDouble, чтобы он возвращал определенные значения, которые нужно задать параметризовав тест.
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.1, 0.2, 0.6, 0.8, 1.0, 99.99})
    public void moveDistanceTest(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("MoonBlan", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(283 + 31 * random, horse.getDistance());
        }
    }
}