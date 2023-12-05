import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Day04 extends BaseDay {

    public static void main(String... args) throws URISyntaxException, IOException {
        final var day = new Day04();

        final var input = getInput(day.getClass(), false);
        day.run(input);
    }

    public void run(String input) {

        final var cards = input.lines()
                .map(Card::new)
                .toList();

        int total = 0;
        for (var card : cards) {
            final var myWinners = card.cardNumbers.stream().filter(it -> card.myNumbers.contains(it)).toList();
            log.info("Winners: {}", myWinners.size());
            if (myWinners.size() > 0) {
                total += Math.pow(2,myWinners.size() - 1);
            }
            log.info("Total: {}", total);
        }

        log.info("Part 1 solution: {}", total);


        long total2 = 0;
        for (int i = 0; i < cards.size(); i++) {
            final var currentCard = cards.get(i);
            final var myWinners = currentCard.cardNumbers.stream().filter(it -> currentCard.myNumbers.contains(it)).toList();

            final var winningCount = myWinners.size();
            log.info("Winners: {}", myWinners.size());

            for(int j = 1; j <= winningCount; j++) {
                cards.get(i + j).copies += currentCard.copies;
            }

            total2 += currentCard.copies;
        }


        log.info("Part 2 solution: {}", total2);
    }

    public static class Card {

        int copies = 1;
        List<Integer> cardNumbers = new ArrayList<>();
        List<Integer> myNumbers = new ArrayList<>();


        public Card(String cardRow) {
            final var numbers = cardRow.split(": ")[1].split(" \\| ");

            cardNumbers.addAll(Arrays.stream(numbers[0].split("\\s+"))
                    .filter(it -> it.length() > 0)
                    .map(Integer::parseInt).toList());
            myNumbers.addAll(Arrays.stream(numbers[1].split("\\s+"))
                    .filter(it -> it.length() > 0)
                    .map(Integer::parseInt).toList());
        }
    }
}
