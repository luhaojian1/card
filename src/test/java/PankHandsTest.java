import card.PankHands;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
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

    @Test
    void should_find_all_card_numbers_when_given_cards_of_map() {
        //given
        PankHands pankHands = new PankHands();
        String[] players = {"2H", "3D", "4S", "8H", "TH"};
        Map<String, Integer> cards = pankHands.divideCards(players);

        //when
        List<Integer> carNumbers = pankHands.findCarNumbers(cards);
        Collections.sort(carNumbers);

        //given
        assertEquals(2, carNumbers.get(0));
        assertEquals(3, carNumbers.get(1));
        assertEquals(4, carNumbers.get(2));
        assertEquals(8, carNumbers.get(3));
        assertEquals(10, carNumbers.get(4));
    }



}
