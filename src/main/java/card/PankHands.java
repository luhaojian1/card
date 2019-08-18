package card;

import java.util.*;
import java.util.stream.Collectors;

public class PankHands {
    public String compareCards(String[] player1, String[] player2) {
        Map<String, Integer> cards1 = divideCards(player1);
        Map<String, Integer> cards2 = divideCards(player2);
        List<Integer> values1 = getValues(cards1);
        List<Integer> values2 = getValues(cards2);
        int cardsLevel1 = findCardsLevel(player1, values1);
        int cardsLevel2 = findCardsLevel(player2, values2);

        if (cardsLevel1 > cardsLevel2) {
            return "player1";
        } else if (cardsLevel1 < cardsLevel2) {
            return "player2";
        } else {
            List<Integer> cardNumbers1 = findCarNumbers(cards1);
            List<Integer> cardNumbers2 = findCarNumbers(cards2);

            if (cardsLevel1 == Level.Two_Pairs.code()) {
                List<Integer> twoPairCards2 = findTwoPairCarNumbers(cardNumbers2, values2);
                List<Integer> twoPairCards1 = findTwoPairCarNumbers(cardNumbers1, values1);
                String result = findMaxValuePlayer(twoPairCards1, twoPairCards2);
                if (result.equals("deuce")) {
                    int singleValue1 = cardNumbers1.stream().filter(carNumber -> !twoPairCards1.contains(carNumber)).findFirst().orElse(0);
                    int singleValue2 = cardNumbers2.stream().filter(carNumber -> !twoPairCards1.contains(carNumber)).findFirst().orElse(0);
                    if (singleValue1 > singleValue2) {
                        return "player1";
                    } else if (singleValue1 < singleValue2) {
                        return "player2";
                    } else return "deuce";
                }
                return result;
            } else if (cardsLevel1 == Level.Pair.code() || cardsLevel1 == Level.Three_of_a_Kind.code() || cardsLevel1 == Level.Four_of_a_Kind.code()) {
                int maxValue = values1.stream().max(Comparator.naturalOrder()).orElse(0);
                int targetCarNumber1 = cardNumbers1.get(values1.indexOf(maxValue));
                int targetCarNumber2 = cardNumbers2.get(values2.indexOf(maxValue));
                List<Integer> cardNum1 = findFilterCarNumbers(cardNumbers1, targetCarNumber1);
                List<Integer> cardNum2 = findFilterCarNumbers(cardNumbers2, targetCarNumber2);
                return findMaxValuePlayer(cardNum1, cardNum2);
            } else {
                System.out.println(1111);
                return findMaxValuePlayer(cardNumbers1, cardNumbers2);
            }

        }

    }

    int findCardsLevel(String[] playingCards, List<Integer> values) {
        boolean isStraight = isStraightCards(values);
        boolean isFlush = isFlushCards(playingCards);
        return judgeCardsLevel(isFlush, isStraight, values);

    }

    public List<Integer> findFilterCarNumbers(List<Integer> cardNumbers, int targetCarNumber) {
        return cardNumbers.stream().filter(carNumber -> carNumber != targetCarNumber).sorted().collect(Collectors.toList());
    }

    public List<Integer> findTwoPairCarNumbers(List<Integer> cardNumbers, List<Integer> values) {
        System.out.println(cardNumbers.toString());
        System.out.println(values.toString());
        List<Integer> twoPairCards = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == 2) {
                twoPairCards.add(cardNumbers.get(i));
            }
        }
        Collections.sort(twoPairCards);
        return twoPairCards;
    }

    public String findMaxValuePlayer(List<Integer> cardNumbers1, List<Integer> cardNumbers2) {
        for (int i = cardNumbers1.size() - 1; i >= 0; i--) {
            if (cardNumbers1.get(i) > cardNumbers2.get(i)) {
                return "player1";
            }
            if (cardNumbers1.get(i) < cardNumbers2.get(i)) {
                return "player2";
            }
        }
        return "deuce";
    }

    public Map<String, Integer> divideCards(String[] cards) {
        Map<String, Integer> map = new HashMap<>();
        for (String card : cards) {
            String key = card.substring(0, 1);
            map.merge(key, 1, Integer::sum);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println();
        return map;
    }

    public List<Integer> findCarNumbers(Map<String, Integer> map) {
        List<Integer> carNumbers = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "T":
                    carNumbers.add(10);
                    break;
                case "J":
                    carNumbers.add(11);
                    break;
                case "Q":
                    carNumbers.add(12);
                    break;
                case "K":
                    carNumbers.add(13);
                    break;
                case "A":
                    carNumbers.add(14);
                    break;
                default:
                    carNumbers.add(Integer.parseInt(entry.getKey()));
                    break;
            }
        }
        return carNumbers;
    }

    public List<Integer> getValues(Map<String, Integer> map) {
        return new ArrayList<>(map.values());
    }

    public boolean isFlushCards(String[] cards) {
        String designLevel = cards[0].substring(1);
        return Arrays.stream(cards).allMatch(card -> card.substring(1).equals(designLevel));
    }

    public boolean isStraightCards(List<Integer> carNumbers) {
        if (carNumbers.size() != 5)
            return false;
        for (int i = 0; i < carNumbers.size() - 1; i++) {
            if (carNumbers.get(i) + 1 != carNumbers.get(i + 1)) {

                return false;
            }
        }
        return true;
    }

    public int judgeCardsLevel(boolean isFlush, boolean isStraight, List<Integer> values) {
        if (isFlush && isStraight) {
            return Level.Straight_Flush.code();
        } else if (isFlush) {
            return Level.Flush.code();
        } else if (isStraight) {
            return Level.Straight.code();
        } else if (values.stream().anyMatch(value -> value == 4)) {
            return Level.Four_of_a_Kind.code();
        } else if (values.stream().anyMatch(value -> value == 3)) {
            if (values.stream().anyMatch(value -> value == 2)) {
                return Level.Full_House.code();
            }
            return 4;
        } else if (values.stream().filter(value -> value == 2).count() == 2) {
            return Level.Three_of_a_Kind.code();
        } else if (values.stream().filter(value -> value == 2).count() == 1) {
            return Level.Two_Pairs.code();
        }
        return Level.Pair.code();

    }

}
