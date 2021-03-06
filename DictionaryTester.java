import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class DictionaryTester
{
private Dictionary dictionary;
private DictionaryService dictService;
List<String> dictionaryList;

 @Before
    public void setUp() {
        dictionary = new Dictionary();
        dictService = mock(DictionaryService.class);
        dictionary.setDictionaryService(dictService);
        // Adding mocked dictionary
        when(dictService.getDictionary()).thenReturn(createDictionaryArray());
        dictionaryList = dictService.getDictionary();
    }

    
    static List<String> createDictionaryArray() {
        List<String> listDictionary = new ArrayList<String>();
        BufferedReader reader;

        try {
            ClassLoader loader = DictionaryTester.class.getClassLoader();
            File file = new File(loader.getResource("EnglishWords").getFile());
            reader = new BufferedReader(new FileReader(file));
            //reader = new BufferedReader(new FileReader("EnglishWords"));
            String line = reader.readLine();
            while (line != null) {
                listDictionary.add(line);
                line = reader.readLine(); // read next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listDictionary;
    }

    public boolean isThisEnglish(String word) {
        for (String w : dictionaryList) {
            if (w.equals(word.toLowerCase())) {
                System.out.println(word + " is a valid english word");
                return true;
            }
        }
        return false;
    }

    @Test
    public void validateKiteWord() {
        when(dictService.isEnglishWord("KITE")).thenReturn(isThisEnglish("KITE"));
        Assert.assertTrue(dictionary.isEnglishWord("KITE"));
    }

    @Test
    public void validateNoWord() {
        when(dictService.isEnglishWord("NO")).thenReturn(isThisEnglish("NO"));
        Assert.assertTrue(dictionary.isEnglishWord("NO"));
        Assert.assertEquals(Arrays.asList("n", "no", "o", "on"),(dictionary.findPossibleWords("NO")));
    }

    @Test
    public void validateWorkingWord() {
        when(dictService.isEnglishWord("WORKING")).thenReturn(isThisEnglish("WORKING"));
        Assert.assertTrue(dictionary.isEnglishWord("WORKING"));
        dictionary.findPossibleWords("WORKING");
    }
}