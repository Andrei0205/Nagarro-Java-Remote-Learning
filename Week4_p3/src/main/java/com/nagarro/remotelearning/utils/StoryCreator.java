package com.nagarro.remotelearning.utils;

import java.util.List;
import java.util.Random;

public class StoryCreator {
    private Random random = new Random();
    public String createStory(List<String> sentences) {
        StringBuilder story = new StringBuilder();
        for (int i = 0; i < random.nextInt(sentences.size()); i++) {
            story.append(sentences.get(random.nextInt(sentences.size())));
            story.append("\n");
        }
        return story.toString();
    }
}
