package card;

import java.util.HashMap;
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
}
