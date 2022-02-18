import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

abstract class LadderGame {
    protected ArrayList<ArrayList<String>> canonDict = new ArrayList<>();
    protected ArrayList<ArrayList<String>> dictionary = new ArrayList<>();

    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    abstract void play(String start, String end);

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        if (withRemoval) {
            dictionary.get(word.length() - 1).remove(word);
        }
        ArrayList<String> words = new ArrayList<>();
        int wordSize = word.length();
        for (int n = 0; n < dictionary.get(wordSize - 1).size(); n++) {
            int counter = 0;
            for (int m = 0; m < wordSize; m++) {
                if (dictionary.get(wordSize - 1).get(n).charAt(m) != word.charAt(m)) {
                    counter++;
                }
            }
            if (counter == 1) {
                words.add(dictionary.get(wordSize - 1).get(n));
            }
        }
        if (withRemoval) {
            dictionary.get(word.length() - 1).removeAll(words);
        }
        return words;
    }

    public void listWords(int length, int howMany) {
        for (int n = 0; n < howMany; n++) {
            System.out.println(dictionary.get(length - 1).get(n));
        }
    }

    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();
        canonDict = new ArrayList<>();
        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            for (int n = 1; n <= longestWord; n++) {
                ArrayList<String> groups = new ArrayList<>();
                for (String allWord : allWords) {
                    if (allWord.length() == n) {
                        groups.add(allWord.toLowerCase());
                    }
                }
                canonDict.add(groups);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: "
                    + ex);
        }
        deepCloneDict();
    }

    protected void deepCloneDict() {
        dictionary = new ArrayList<>();
        for(ArrayList<String> entry : canonDict ) dictionary.add((ArrayList<String>) entry.clone());
    }
}