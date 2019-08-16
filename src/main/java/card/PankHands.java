package card;

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
}
