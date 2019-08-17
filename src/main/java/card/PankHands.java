package card;

import java.util.*;
import java.util.stream.Collectors;

public class PankHands {
    public String compareCards(String[] player1, String[] player2) {
        Map<String, Integer> cards1 = divideCards(player1);
        Map<String, Integer> cards2 = divideCards(player2);
        List<Integer> cardNumbers1 = findCarNumbers(cards1);
        List<Integer> cardNumbers2 = findCarNumbers(cards2);
        List<Integer> values1 = getValues(cards1);
        List<Integer> values2 = getValues(cards2);
        int maxValue1 = values1.stream().max(Comparator.naturalOrder()).orElse(0);
        int maxValue2 = values2.stream().max(Comparator.naturalOrder()).orElse(0);

        if (maxValue1 > maxValue2) {
            return "player1";
        } else if (maxValue1 < maxValue2) {
            return "player2";
        } else {
            if (values1.stream().filter(value -> value == 2).count() == 2) {
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
            } else if (maxValue1 == 2) {
                int targetCarNumber1 = cardNumbers1.get(values1.indexOf(maxValue1));
                int targetCarNumber2 = cardNumbers2.get(values2.indexOf(maxValue2));
                List<Integer> cardNum1 = findFilterCarNumbers(cardNumbers1, targetCarNumber1);
                List<Integer> cardNum2 = findFilterCarNumbers(cardNumbers2, targetCarNumber2);
                return findMaxValuePlayer(cardNum1, cardNum2);
            } else {
                return findMaxValuePlayer(cardNumbers1, cardNumbers2);
            }

        }

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

}
