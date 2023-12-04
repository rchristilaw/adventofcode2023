import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Slf4j
public class Day03 extends BaseDay {

    public static void main(String... args) throws URISyntaxException, IOException {
        final var day = new Day03();

        final var input = getInput(day.getClass(), false);
        day.run(input);
    }

    public void run(String input) {

        final var grid = new Grid();

        input.lines().forEach(grid::addRow);

        int total = 0;

        for (int y = 0; y < grid.getHeight(); y++) {
            var currentNum = "";
            boolean isPartNum = false;

            for (int x = 0; x < grid.getWidth(); x++) {

                if (!grid.isNumber(x, y)) {
                    if (currentNum.length() > 0 && isPartNum) {
                        total += Integer.parseInt(currentNum);
                    }
                    currentNum = "";
                    isPartNum = false;
                    continue;
                }

                currentNum += grid.getCoord(x, y);

                if (isPartNum) {
                    continue;
                }

                if (grid.isSymbol(x - 1, y + 1)
                        || grid.isSymbol(x - 1, y)
                        || grid.isSymbol(x - 1, y - 1)
                        || grid.isSymbol(x, y + 1)
                        || grid.isSymbol(x, y - 1)
                        || grid.isSymbol(x + 1, y + 1)
                        || grid.isSymbol(x + 1, y)
                        || grid.isSymbol(x + 1, y - 1)) {
                    isPartNum = true;
                }
            }
            if (currentNum.length() > 0 && isPartNum) {
                total += Integer.parseInt(currentNum);
            }
        }


        log.info("Part 1 solution: {}", total);

        long total2 = 0;

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                if (!grid.isGear(x, y)) {
                    continue;
                }

                final var gearNumbers = new HashSet<String>();

                boolean skipNext = false;

                skipNext = grid.addGearNumber(x - 1, y - 1, gearNumbers);
                if (!skipNext) {
                    skipNext = grid.addGearNumber(x, y - 1, gearNumbers);
                } else {
                    skipNext = false;
                }
                if (!skipNext) {
                    grid.addGearNumber(x + 1, y - 1, gearNumbers);

                }


                skipNext = grid.addGearNumber(x - 1, y + 1, gearNumbers);
                if (!skipNext) {
                    skipNext = grid.addGearNumber(x, y + 1, gearNumbers);
                } else {
                    skipNext = false;
                }

                if (!skipNext) {
                    grid.addGearNumber(x + 1, y + 1, gearNumbers);
                }


                grid.addGearNumber(x - 1, y, gearNumbers);
                grid.addGearNumber(x + 1, y, gearNumbers);


                if (gearNumbers.size() == 2) {
                    final var gears = gearNumbers.stream().toList();
                    total2 += (Long.parseLong(gears.get(0)) * Long.parseLong(gears.get(1)));
                }
            }
        }

        log.info("Part 2 solution: {}", total2);
    }

    public static class Grid {
        private final List<List<String>> grid = new ArrayList<>();

        public String getCoord(int x, int y) {
            return grid.get(y).get(x);
        }

        public void addRow(String row) {
            grid.add(Arrays.stream(row.split("")).toList());
        }

        public int getWidth() {
            return grid.get(0).size();
        }

        public int getHeight() {
            return grid.size();
        }

        public boolean isNumber(int x, int y) {
            try {
                Integer.parseInt(getCoord(x, y));
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        public boolean isSymbol(int x, int y) {
            if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
                return false;
            }
            return this.getCoord(x, y).charAt(0) != '.' && !this.isNumber(x, y);
        }

        public boolean isGear(int x, int y) {
            if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
                return false;
            }
            return this.getCoord(x, y).charAt(0) == '*';
        }

        public boolean addGearNumber(int x, int y, Set<String> nums) {
            if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
                return false;
            }

            if (!isNumber(x, y)) {
                return false;
            }

            int startX = x;
            String number = "";
            while (isNumber(startX - 1, y)) {
                startX--;
            }

            while (isNumber(startX, y)) {
                number += getCoord(startX, y);
                startX++;
            }

            nums.add(number);
            return true;
        }
    }

}
