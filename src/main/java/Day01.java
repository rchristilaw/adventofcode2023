import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day01 extends BaseDay {

    private static final Map<String, String> numbers = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    public static void main(String... args) throws URISyntaxException, IOException {
        final var day = new Day01();

        final var input = getInput(day.getClass(), false);
        day.run(input);
    }

    public void run(String input) {

        final var rows = input.lines()
                .map(it -> it.split(""))
                .toList();

        int sum = 0;

        for (final var row : rows) {
            final var first = findNumber(row, false);
            final var last = findNumber(row, true);

            final var num = first + last;
            log.info(num);
            sum += Integer.parseInt(num);
        }
        
        log.info("Part 1 solution: {}", sum);


        final var rows2 = input.lines().toList();

        int sum2 = 0;

        for (final var row : rows2) {
            final var first = findFirstNumber(row);
            final var last = findLastNumber(row);

            final var num = first + last;
            log.info(first);
            log.info(last);

            log.info(num);
            sum2 += Integer.parseInt(num);
        }
        log.info("Part 2 solution: {}", sum2);
    }

    private String findFirstNumber(String val) {
        for (int i = 0; i < val.length(); i++) {
            final var c = val.substring(i, i + 1);
            if (isNumber(c)) {
                return c;
            }
            final var num = getStringNum(val.substring(i, val.length() - 1));
            if (num != null) {
                return num;
            }
        }
        return null;
    }

    private String getStringNum(String val) {
        for (var numString : numbers.keySet()) {
            if (val.indexOf(numString) == 0) {
                return numbers.get(numString);
            }
        }

        return null;
    }

    private String findLastNumber(String val) {
        for (int i = val.length() - 1; i >= 0; i--) {
            final var c = val.substring(i, i + 1);
            if (isNumber(c)) {
                return c;
            }
            final var num = getStringNum(val.substring(i, val.length()));
            if (num != null) {
                return num;
            }
        }
        return null;
    }


    private String findNumber(String[] list, boolean reverse) {
        if (reverse) {
            for (int i = list.length - 1; i >= 0; i--) {
                if (isNumber(list[i])) {
                    return list[i];
                }
            }
        } else {
            for (final var val : list) {
                if (isNumber(val)) {
                    return val;
                }
            }
        }
        return null;
    }


    private boolean isNumber(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
