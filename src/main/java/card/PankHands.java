package card;

import java.util.*;

public class PankHands {
    public String compareCards(String[] player1, String[] player2) {
        Map<String, Integer> cards1 = divideCards(player1);
        Map<String, Integer> cards2 = divideCards(player2);
        List<Integer> carNumbers1 = findCarNumbers(cards1);
        List<Integer> carNumbers2 = findCarNumbers(cards2);
        Collections.sort(carNumbers1);
        Collections.sort(carNumbers2);
        List<Integer> values1 = getValues(cards1);
        List<Integer> values2 = getValues(cards2);
        int maxValue1 = values1.stream().max(Comparator.naturalOrder()).orElse(0);
        int maxValue2 = values2.stream().max(Comparator.naturalOrder()).orElse(0);
        if (maxValue1 > maxValue2) {
            return "player1";
        } else if (maxValue1 < maxValue2) {
            return "player2";
        } else {
            for (int i = carNumbers1.size() - 1; i >= 0; i--) {
                if (carNumbers1.get(i) > carNumbers2.get(i)) {
                    return "player1";
                }
                if (carNumbers1.get(i) < carNumbers2.get(i)) {
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
