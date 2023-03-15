import com.nagarro.remotelearning.utils.Card;
import com.nagarro.remotelearning.utils.DeckCardGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardTest {


    private static final int FIRST_CARD = 0;
    private static final int SECOND_CARD = 1;

    @Test()
    public void shuffleTest() {
        DeckCardGenerator deckCardGenerator = new DeckCardGenerator();
        List<Card> cards = deckCardGenerator.getDeck();
        int sumBeforeShuffle = cards.get(FIRST_CARD).getNumber() + cards.get(SECOND_CARD).getNumber();
        Collections.shuffle(cards);
        int sumAfterShuffle = cards.get(FIRST_CARD).getNumber() + cards.get(SECOND_CARD).getNumber();
        assertNotEquals(sumBeforeShuffle,sumAfterShuffle);

    }

}
