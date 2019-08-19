package card;

import java.util.*;
import java.util.stream.Collectors;

public class PankHands {
    public String compareCards(String[] player1, String[] player2) {
        Map<String, List<Integer>> cards1 = designPankCards(player1);
        Map<String, List<Integer>> cards2 = designPankCards(player2);
        return findWinners(cards1, cards2);

    }

    private String findWinners(Map<String, List<Integer>> cards1, Map<String, List<Integer>> cards2) {
        List<Integer> values1 = cards1.get("values");
        List<Integer> values2 = cards2.get("values");
        int cardsLevel1 = cards1.get("level").get(0);
        int cardsLevel2 = cards2.get("level").get(0);
        if (cardsLevel1 > cardsLevel2) {
            return "player1";
        } else if (cardsLevel1 < cardsLevel2) {
            return "player2";
        } else {
            List<Integer> cardNumbers1 = cards1.get("carNumbers");
            List<Integer> cardNumbers2 = cards2.get("carNumbers");
            if (cardsLevel1 == Level.Two_Pairs.code()) {
                return compareTwoPairs(values1, values2, cardNumbers1, cardNumbers2);
            } else if (cardsLevel1 == Level.Pair.code()) {
                return comparePairs(values1, values2, cardNumbers1, cardNumbers2);
            } else if (cardsLevel1 == Level.Three_of_a_Kind.code() || cardsLevel1 == Level.Full_House.code() || cardsLevel1 == Level.Four_of_a_Kind.code()) {
                return compareThreeOrFourKinds(values1, values2);
            } else {
                return findMaxValuePlayer(cardNumbers1, cardNumbers2);
            }
        }
    }

    private String compareThreeOrFourKinds(List<Integer> values1, List<Integer> values2) {
        int maxValues1 = values1.stream().max(Comparator.naturalOrder()).orElse(0);
        int maxValues2 = values2.stream().max(Comparator.naturalOrder()).orElse(0);
        return compareValueAndFindWinners(maxValues1, maxValues2);
    }

    private String comparePairs(List<Integer> values1, List<Integer> values2, List<Integer> cardNumbers1, List<Integer> cardNumbers2) {
        List<Integer> cardNum1 = findFilterCarNumbers(cardNumbers1, values1);
        List<Integer> cardNum2 = findFilterCarNumbers(cardNumbers2, values2);
        return findMaxValuePlayer(cardNum1, cardNum2);
    }

    private String compareTwoPairs(List<Integer> values1, List<Integer> values2, List<Integer> cardNumbers1, List<Integer> cardNumbers2) {
        List<Integer> twoPairCards2 = findTwoPairCarNumbers(cardNumbers2, values2);
        List<Integer> twoPairCards1 = findTwoPairCarNumbers(cardNumbers1, values1);
        String winner = findMaxValuePlayer(twoPairCards1, twoPairCards2);
        if (winner.equals("deuce")) {
            int singleValue1 = cardNumbers1.stream().filter(carNumber -> !twoPairCards1.contains(carNumber)).findFirst().orElse(0);
            int singleValue2 = cardNumbers2.stream().filter(carNumber -> !twoPairCards1.contains(carNumber)).findFirst().orElse(0);
            return compareValueAndFindWinners(singleValue1, singleValue2);
        }
        return winner;
    }

    private String compareValueAndFindWinners(int value1, int value2) {
        if (value1 > value2) {
            return "player1";
        } else if (value1 < value2) {
            return "player2";
        } else return "deuce";
    }

    private Map<String, List<Integer>> designPankCards(String[] playingCards) {
        Map<String, Integer> cards = divideCards(playingCards);
        Map<String, List<Integer>> pankCards = new HashMap<>();
        List<Integer> cardNumbers = findCarNumbers(cards);
        pankCards.put("carNumbers", cardNumbers);
        List<Integer> values = getValues(cards);
        pankCards.put("values", values);
        boolean isStraight = isStraightCards(values);
        boolean isFlush = isFlushCards(playingCards);
        int level = judgeCardsLevel(isFlush, isStraight, values);
        pankCards.put("level", Collections.singletonList(level));
        return pankCards;
    }

    private List<Integer> findFilterCarNumbers(List<Integer> cardNumbers, List<Integer> values) {
        int maxValue = values.stream().max(Comparator.naturalOrder()).orElse(0);
        int targetCarNumber = cardNumbers.get(values.indexOf(maxValue));
        return cardNumbers.stream().filter(carNumber -> carNumber != targetCarNumber).sorted().collect(Collectors.toList());
    }

    private List<Integer> findTwoPairCarNumbers(List<Integer> cardNumbers, List<Integer> values) {
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

    private String findMaxValuePlayer(List<Integer> cardNumbers1, List<Integer> cardNumbers2) {
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
            return Level.Three_of_a_Kind.code();
        } else if (values.stream().filter(value -> value == 2).count() == 2) {
            return Level.Three_of_a_Kind.code();
        } else if (values.stream().filter(value -> value == 2).count() == 1) {
            return Level.Two_Pairs.code();
        }
        return Level.Pair.code();

    }

}
