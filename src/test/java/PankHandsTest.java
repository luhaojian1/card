import card.PankHands;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PankHandsTest {
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
}
