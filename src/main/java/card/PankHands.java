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
            int targetCarNumber1 = cardNumbers1.get(values1.indexOf(maxValue1));
            int targetCarNumber2 = cardNumbers2.get(values2.indexOf(maxValue2));
            List<Integer> cardNum1 = cardNumbers1.stream().filter(carNumber -> carNumber != targetCarNumber1).sorted().collect(Collectors.toList());
            List<Integer> cardNum2 = cardNumbers2.stream().filter(carNumber -> carNumber != targetCarNumber2).sorted().collect(Collectors.toList());
            for (int i = cardNum1.size() - 1; i >= 0; i--) {

                if (cardNum1.get(i) > cardNum2.get(i)) {
                    return "player1";
                }
                if (cardNum1.get(i) < cardNum2.get(i)) {
                    return "player2";
                }
            }
            return "deuce";
        }

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
}
