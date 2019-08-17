import card.Level;
import card.PankHands;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void should_player2_win_given_player1_2358T_and_player2_358JA() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"2H", "3D", "5S", "8C", "TH"};
        String[] player2 = {"3H", "5D", "8S", "JC", "AH"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("player2", result);
    }

    @Test
    void should_deuce_given_player1_2358T_and_player2_2358T() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"2H", "3D", "5S", "8C", "TH"};
        String[] player2 = {"2S", "3C", "5H", "8D", "TS"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("deuce", result);
    }

    @Test
    void should_get_card_number_counts_given_card_map() {
        PankHands pankHands = new PankHands();
        //given
        String[] players = {"2H", "2D", "5S", "8C", "TH"};
        Map<String, Integer> cards = pankHands.divideCards(players);

        //when
        List<Integer> result = pankHands.getValues(cards);
        Collections.sort(result);

        //then
        assertEquals(Arrays.asList(1, 1, 1, 2).toString(), result.toString());
    }

    @Test
    void should_player1_win_given_player1_2258T_and_player2_2359T() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"2H", "2D", "5S", "8C", "TH"};
        String[] player2 = {"2S", "3C", "5H", "9D", "TS"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("player1", result);
    }

    @Test
    void should_player1_win_given_player1_7738T_and_player2_7726T() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"7H", "7D", "3S", "8C", "TH"};
        String[] player2 = {"7S", "7C", "2H", "6D", "TS"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("player1", result);
    }

    @Test
    void should_player1_win_given_player1_7733T_and_player2_7726T() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"7H", "7D", "3S", "8C", "TH"};
        String[] player2 = {"7S", "7C", "2H", "6D", "TS"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("player1", result);
    }

    @Test
    void should_two_players_deuce_given_player1_7733T_and_player2_7733T() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"7H", "7D", "3S", "3C", "TH"};
        String[] player2 = {"7S", "7C", "3H", "3D", "TS"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("deuce", result);
    }

    @Test
    void should_player1_win_given_player1_is_three_of_a_kind_and_player2_is_pair() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"7H", "7D", "7S", "3C", "TH"};
        String[] player2 = {"AS", "9C", "3H", "3D", "TS"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("player1", result);
    }

    @Test
    void should_player1_win_given_player1_is_34567_and_player_is_23456() {
        PankHands pankHands = new PankHands();
        //given
        String[] player1 = {"3H", "7D", "4S", "5C", "6H"};
        String[] player2 = {"5S", "4C", "2H", "3D", "6S"};

        //when
        String result = pankHands.compareCards(player1, player2);

        //then
        assertEquals("player1", result);
    }

    @Test
    void should_cards_is_flush_given_player_3D_4D_6D_8D_9D() {
        PankHands pankHands = new PankHands();
        //given
        String[] player = {"3D", "4D", "6D", "8D", "9D"};

        //when
        boolean isFlush = pankHands.isFlushCards(player);

        //then
        assertTrue(isFlush);
    }

    @Test
    void should_cards_is_straight_given_player_23456() {
        PankHands pankHands = new PankHands();
        //given
        List<Integer> player = Arrays.asList(2, 3, 4, 5, 6);

        //when
        boolean isFlush = pankHands.isStraightCards(player);

        //then
        assertTrue(isFlush);
    }

    @Test
    void should_judge_player_cards_level_is_straight_flush_given_flush_and_straight() {
        PankHands pankHands = new PankHands();
        //given
        List<Integer> values = Arrays.asList(1, 1, 1, 1, 1);
        boolean isFlush = true;
        boolean isStraight = true;
        //when
        int level = pankHands.judgeCardsLevel(isFlush, isStraight, values);

        //then
        assertEquals(Level.Straight_Flush.code(), level);
    }

}
