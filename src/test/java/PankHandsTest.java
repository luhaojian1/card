import card.PankHands;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PankHandsTest {
    @Test
    void should_player2_win_given_player1_2C_and_player2_3C() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"2C"};
        String[] player2 = {"3C"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("player2", result);
    }

    @Test
    void should_divide_card_numbers_when_given_2H_3D_4S_8H_TH() {
        //given
        PankHands pankHands = new PankHands();
        String[] players = {"2H", "3D", "4S", "8H", "TH"};

        //when
        Map<String, Integer> cards = pankHands.divideCards(players);

        //given
        assertEquals(1, cards.get("T"));
        assertEquals(1, cards.get("2"));
        assertEquals(1, cards.get("3"));
        assertEquals(1, cards.get("4"));
        assertEquals(1, cards.get("8"));
    }

}
