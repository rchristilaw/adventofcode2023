import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day02 extends BaseDay {

    private static final Map<String, Integer> MAXIMUMS = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14
    );

    public static void main(String... args) throws URISyntaxException, IOException {
        final var day = new Day02();

        final var input = getInput(day.getClass(), false);
        day.run(input);
    }

    public void run(String input) {

        final var games = input.lines()
                .toList();

        int sum1 = 0;
        for (final var game : games) {
            sum1 += parseGamePart1(game);
        }

        log.info("Part 1 solution: {}", sum1);

        int sum2 = 0;
        for (final var game : games) {
            sum2 += parseGamePart2(game);
        }

        log.info("Part 2 solution: {}", sum2);
    }

    private int parseGamePart1(String gameInput) {
        final var game = Game.parseGame(gameInput);

        for (var set : game.sets) {
            final var draws = set.split(", ");
            for (var drawValue : draws) {
                final var draw = drawValue.split(" ");
                final var colour = draw[1];
                final var amount = Integer.parseInt(draw[0]);

                if (amount > MAXIMUMS.get(colour)) {
                    return 0;
                }
            }
        }

        log.info(String.valueOf(game.gameNum));

        return game.gameNum;
    }

    private int parseGamePart2(String gameInput) {
        final var game = Game.parseGame(gameInput);

        final var gameMap = new HashMap<>(Map.of(
                "red", 0,
                "green", 0,
                "blue", 0
        ));


        for (var set : game.sets) {
            final var draws = set.split(", ");
            for (var drawValue : draws) {
                final var draw = drawValue.split(" ");
                final var colour = draw[1];
                final var amount = Integer.parseInt(draw[0]);

                final var currentVal = gameMap.get(colour);
                if (amount > currentVal) {
                    gameMap.put(colour, amount);
                }

            }
        }

        log.info(String.valueOf(game.gameNum));

        var total = 1;
        for (var maxCount : gameMap.values()) {
            total = maxCount*total;
        }

        return total;
    }

    public record Game(int gameNum, List<String> sets){
        public static Game parseGame(String input) {
            final var arr = input.split(": ");
            final var gameId = Integer.parseInt(arr[0].split(" ")[1]);

            final var sets = Arrays.stream(arr[1].split("; ")).toList();

            return new Game(gameId, sets);
        }
    }


}
