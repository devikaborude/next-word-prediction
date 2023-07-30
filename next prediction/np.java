import java.util.HashMap;
import java.util.Map;

public class NextWordPrediction {
    private Map<String, Map<String, Integer>> trigramModel;

    public NextWordPrediction() {
        trigramModel = new HashMap<>();
    }

    public void train(String[] sentences) {
        for (String sentence : sentences) {
            String[] words = sentence.split("\\s+");
            if (words.length >= 3) {
                for (int i = 0; i < words.length - 2; i++) {
                    String trigram = words[i] + " " + words[i + 1];
                    String nextWord = words[i + 2];

                    trigramModel.putIfAbsent(trigram, new HashMap<>());
                    Map<String, Integer> nextWordMap = trigramModel.get(trigram);
                    nextWordMap.put(nextWord, nextWordMap.getOrDefault(nextWord, 0) + 1);
                }
            }
        }
    }

    public String predictNextWord(String prevTwoWords) {
        Map<String, Integer> nextWordMap = trigramModel.getOrDefault(prevTwoWords, new HashMap<>());

        String predictedWord = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : nextWordMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                predictedWord = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return predictedWord;
    }

    public static void main(String[] args) {
        String[] sentences = {
            "the quick brown",
            "brown fox jumps",
            "jumps over the",
            "the lazy dog"
        };

        NextWordPrediction predictor = new NextWordPrediction();
        predictor.train(sentences);

        String prevTwoWords = "the quick";
        String predictedWord = predictor.predictNextWord(prevTwoWords);
        System.out.println("Next word prediction for \"" + prevTwoWords + "\": " + predictedWord);
    }
}
