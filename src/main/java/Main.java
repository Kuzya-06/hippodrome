import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        List<Horse> horses = List.of(
                new Horse("Буцефал", 2.4),
                new Horse("Туз Пик", 2.5),
                new Horse("Зефир", 2.6),
                new Horse("Пожар", 2.7),
                new Horse("Лобстер", 2.8),
                new Horse("Пегас", 2.9),
                new Horse("Вишня", 3)
        );

        Hippodrome hippodrome = new Hippodrome(horses);

        // После создания объекта ипподрома, добавить в лог запись вида: 2022-05-31 17:05:26,152 INFO Main: Начало скачек. Количество участников: 7
        logger.info("Начало скачек. Количество участников: {}", horses.size());

        for (int i = 0; i < 4; i++) {
            hippodrome.move();
            watch(hippodrome);
            TimeUnit.MILLISECONDS.sleep(200);
        }

        String winnerName = hippodrome.getWinner().getName();
        System.out.println("Победил " + winnerName + "!");

        // После вывода информации о победители, добавить в лог запись вида: 2022-05-31 17:05:46,963 INFO Main: Окончание скачек. Победитель: Вишня
        logger.error("Окончание скачек. Победитель: {}", winnerName); // проверил уровни записи для консоли и для log
    }

    private static void watch(Hippodrome hippodrome) throws Exception {
        hippodrome.getHorses().stream()
                .map(horse -> ".".repeat((int) horse.getDistance()) + horse.getName())
                .forEach(System.out::println);
        System.out.println("\n".repeat(10));
    }
}
