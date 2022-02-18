import java.util.ArrayList;

public class LadderGameExhaustive extends LadderGame {

    public LadderGameExhaustive(String dictionaryFile) {
        super(dictionaryFile);
    }

    public void play(String start, String end) {
        deepCloneDict();
        int enqueueCounter = 0;
        if (start.length() == end.length() && dictionary.get(start.length() - 1).contains(start) && dictionary.get(end.length() - 1).contains(end)) {

            WordInfo ladder = new WordInfo(start, enqueueCounter);
            boolean solutionFound = false;
            Queue<WordInfo> partialSolution = new Queue<>();
            partialSolution.enqueue(ladder);
            enqueueCounter++;

            while (!partialSolution.isEmpty() && !solutionFound) {
                WordInfo curLadder = partialSolution.dequeue();
                ArrayList<String> oneAways = this.oneAway(curLadder.getWord(), true);
                for(String word : oneAways) {
                    ladder = new WordInfo(word, curLadder.getMoves() + 1, curLadder.getHistory() + " " + word);
                    if(word.equals(end)) {
                        solutionFound = true;
                        System.out.println("Seeking exhaustive solution from " + start + " -> " + end + "\n [" + ladder.getHistory() + "] " + enqueueCounter + " total enqueues");
                        break;
                    } else {
                        partialSolution.enqueue(ladder);
                        enqueueCounter++;
                    }
                }
            }
            if(partialSolution.isEmpty()) {
                System.out.println(start + " -> " + end + " : No ladder was found");
            }
        }
    }
}
