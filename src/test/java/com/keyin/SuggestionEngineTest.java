package com.keyin;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SuggestionEngineTest {
    private SuggestionEngine suggestionEngine = new SuggestionEngine();
    Path path = Paths.get("src/main/resources/words.txt");
    @Mock
    private SuggestionsDatabase mockSuggestionDB;
    private boolean testInstanceSame = false;

    @Test
    public void testGenerateSuggestions() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

//        Assertions.assertTrue(testInstanceSame);
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hellw").contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsFail() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        testInstanceSame = true;
        Assertions.assertTrue(testInstanceSame);
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hello").contains("hello"));
    }

    @Test
    public void testSuggestionsAsMock() {
        Map<String,Integer> wordMapForTest = new HashMap<>();

        wordMapForTest.put("test", 1);

        Mockito.when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);

        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);

        Assertions.assertFalse(suggestionEngine.generateSuggestions("test").contains("test"));

        Assertions.assertTrue(suggestionEngine.generateSuggestions("tes").contains("test"));
    }



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
    public void dbTest() {
        SuggestionsDatabase suggestionsDatabase = new SuggestionsDatabase();
        Map<String, Integer> wordMap = suggestionsDatabase.getWordMap();
        Assertions.assertNotNull(wordMap, "Word database is empty.");
    }

    @Test
    public void testGenerateSuggestionsForTypos() throws Exception {

        suggestionEngine.loadDictionaryData(path);

        String suggestions = suggestionEngine.generateSuggestions("helo");

        // Check if the correct word "hello" is included in the suggestions
        Assertions.assertTrue(suggestions.contains("hello"), "The suggestions should include the correct word 'hello'");

    }
}
