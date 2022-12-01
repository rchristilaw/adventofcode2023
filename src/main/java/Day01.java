import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day01 extends BaseDay {
    private final static Logger log = Logger.getLogger(Day01.class.getName());
    private final static String inputFile = "day01.txt";

    public static void main(String... args) throws URISyntaxException, IOException {
        final var day1 = new Day01();
        day1.runPart1();
        day1.runPart2();
    }

    public void runPart1() throws URISyntaxException, IOException {
        final var input = readClassPathResource(inputFile);

        log.info("Results");
    }

    public void runPart2() throws URISyntaxException, IOException {
        final var input = readClassPathResource(inputFile);

        log.info("Restults");
    }
}
