package keyin;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
public class SuggestionEngineTest {
    private SuggestionEngine suggestionEngine = new SuggestionEngine();
    Path path = Paths.get("src/main/resources/words.txt");
    @Mock
    private SuggestionsDatabase mockSuggestionDB;
    private boolean testInstanceSame = false;



    @Test
    public void loadDictionaryTest() throws Exception {
        suggestionEngine.loadDictionaryData(path);
        Map<String, Integer> wordSuggestionDB = suggestionEngine.getWordSuggestionDB();
        Assertions.assertFalse(wordSuggestionDB.isEmpty(), "Word suggestion file not loaded correctly.");
    }

    @Test

    public void generateSuggestionsTest() throws Exception {
        suggestionEngine.loadDictionaryData(path);
        String testSuggestions = suggestionEngine.generateSuggestions("tesy");
        System.out.println(testSuggestions);
        Assertions.assertNotNull(testSuggestions, "Suggestions have not been generated correctly.");
    }

    @Test
    public void databaseTest() {
        SuggestionsDatabase suggestionsDatabase = new SuggestionsDatabase();
        Map<String, Integer> wordMap = suggestionsDatabase.getWordMap();
        Assertions.assertNotNull(wordMap, "Word database is empty.");

    }

    @Test
    public void testGenerateSuggestionsForTypos() throws Exception {

        /* Calls the DictionaryData into the Suggestion Engine. */
        suggestionEngine.loadDictionaryData(path);

        /* Generate suggestions for argument*/
        String suggestions = suggestionEngine.generateSuggestions("helo");
        System.out.println(suggestions);

        /*Assert that the suggestions contain the correct argument.*/
        Assertions.assertTrue(suggestions.contains("hello"), "The suggestions should include the correct word 'hello'");

    }

    @Test
    public void suggestionsNotGeneratedForWordInDictionary() throws Exception {
        suggestionEngine.loadDictionaryData(path);


        String suggestions = suggestionEngine.generateSuggestions("hello");

        /*Asserts that no suggestions are generated for a word already in dictionary.*/
        Assertions.assertEquals("", suggestions, "No suggestions should be generated for a word already in the dictionary.");
    }



}
