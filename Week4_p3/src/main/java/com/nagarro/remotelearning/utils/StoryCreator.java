package com.nagarro.remotelearning.utils;

import java.util.List;
import java.util.Random;

public class StoryCreator {
    private final Random random = new Random();
    private final SentenceCreator sentenceCreator = new SentenceCreator();

    public String createStory(int numberOfSentences) {
        List<String> sentences = sentenceCreator.createSentences(numberOfSentences);
        StringBuilder story = new StringBuilder();
        for (int i = 0; i < numberOfSentences; i++) {
            int nextRandom = random.nextInt(sentences.size());
            story.append(sentences.get(nextRandom));
            story.append("\n");
            sentences.remove(nextRandom);
        }
        return story.toString();
    }
}
