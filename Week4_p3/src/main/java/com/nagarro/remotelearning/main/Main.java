package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.SentenceCreator;
import com.nagarro.remotelearning.utils.StoryCreator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SentenceCreator sentenceCreator = new SentenceCreator();
        List<String> sentences = sentenceCreator.createSentences(20);
        StoryCreator storyCreator = new StoryCreator();
        String story = storyCreator.createStory(5);
        System.out.println(story);
    }
}