import java.util.ArrayList;

public class LadderGamePriority extends LadderGame {

    public LadderGamePriority(String dictionaryFile) {
        super(dictionaryFile);
    }

    public void play(String start, String end) {
        int enqueueCounter = 0;
        int priority = findPriority(start, end, 0);
        AVLTree<WordInfoPriority> priorityQueue = new AVLTree<>();
        priorityQueue.insert(new WordInfoPriority(start, 0, priority));
        enqueueCounter++;

        while (!priorityQueue.isEmpty()) {
            var highestPriority = priorityQueue.deleteMin();
            var moves = highestPriority.getMoves() + 1;
            var history = "";
            if (highestPriority.getHistory() == null) {
                history = highestPriority.getWord();
            } else {
                history = highestPriority.getHistory() + " " + highestPriority.getWord();
            }
            if (highestPriority.getWord().equals(end)) {
                System.out.println("Seeking A* solution from " + start + " -> " + end + "\n [" + history + "] " + enqueueCounter + " total enqueues");
                return;
            }
            ArrayList<String> oneAways = this.oneAway(highestPriority.getWord(), false);
            for (String word : oneAways) {
                WordInfoPriority ladder = new WordInfoPriority(word, moves, findPriority(word, end, moves), history);
                enqueueCounter++;
                priorityQueue.insert(ladder);
            }
        }
    }

    public int findPriority(String word, String goal, int moves) {
        int wordSize = word.length();
        int counter = 0;
        for (int m = 0; m < wordSize; m++) {
            if (word.charAt(m) != goal.charAt(m)) {
                counter++;
            }
        }
        return counter + moves;
    }
}
