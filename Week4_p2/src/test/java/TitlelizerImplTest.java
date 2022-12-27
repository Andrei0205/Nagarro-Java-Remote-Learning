
import com.nagarro.remotelearning.model.StringTitlelizer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TitlelizerImplTest {

    private StringTitlelizer titlelizer = new StringTitlelizer();

    @Test(expected = IllegalArgumentException.class)
    public void testTitlelizeForNullShouldThrowException() throws Exception {
        titlelizer.titlelize(null);
    }

    @Test
    public void testTitlelizeForEmptyStringShouldReturnEmptyString() {

        String actualResult = titlelizer.titlelize("");
        assertEquals(actualResult, "");
    }

    @Test
    public void testTitlelizeForTitlelizedString() throws Exception {
        String actualResult = titlelizer.titlelize("Already Titlelized String");
        assertEquals(actualResult, "Already Titlelized String");
    }

    @Test
    public void testTitlelizeShouldIgnorePrepositionsString() throws Exception {
        String actualResult = titlelizer.titlelize("This String is Titlelized");
        assertEquals(actualResult, "This String is Titlelized");
    }

    @Test
    public void testTitlelizeWhenFirsWordIsPreposition() throws Exception {
        String actualResult = titlelizer.titlelize("this String is Titlelized");
        assertEquals(actualResult, "This String is Titlelized");
    }

    @Test
    public void testTitlelize() throws Exception {
        String actualResult = titlelizer.titlelize("please titlelize me");
        assertEquals(actualResult, "Please Titlelize Me");
    }

}
