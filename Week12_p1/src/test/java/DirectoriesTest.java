import com.nagarro.remotelearning.utils.DirectoryManager;
import org.junit.Test;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DirectoriesTest {
    private static final String PATH = "src/main";

    private List<String> expectedList = new ArrayList<>(Arrays.asList("src\\main\\java", "src\\main\\resources"));
    private DirectoryManager directoryManager = new DirectoryManager(PATH);

    @Test
    public void getSubdirectoriesTest() {

    }

}
