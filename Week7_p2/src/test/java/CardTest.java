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
    List<Card> cards;
    DeckCardGenerator deckCardGenerator;

    @Before
    public void setUp() {
        deckCardGenerator = new DeckCardGenerator();
        cards = deckCardGenerator.getDeck();
    }

    @Test()
    public void shuffleTest() {
        List<Card> copyArrayCards = new ArrayList<>();
        copyArrayCards.addAll(cards);
        Collections.shuffle(cards);
        Random random = new Random();
        int randomIndex = random.nextInt(cards.size());
        assertNotEquals(cards.get(randomIndex), copyArrayCards.get(randomIndex));

    }

}
