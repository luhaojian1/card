package card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PankHands {
    public String compareCards(String[] player1, String[] player2) {
        int card1 = Integer.parseInt(player1[0].substring(0, 1));
        int card2 = Integer.parseInt(player2[0].substring(0, 1));
        if (card1 > card2) {
            return "player1";
        } else if (card1 < card2) {
            return "player2";
        } else return "deuce";
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
}
