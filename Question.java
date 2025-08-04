package com.kodnest.quizzApplication;

import java.util.Arrays;
import java.util.Random;

public class Question {
 private final String prompt;
 private final String[] options;
 private final int correctIndex; 

 public Question(String prompt, String[] options, int correctIndex) {
     if (correctIndex < 1 || correctIndex > options.length)
         throw new IllegalArgumentException("Correct index out of bounds");
     this.prompt = prompt;
     this.options = options;
     this.correctIndex = correctIndex;
 }

 public String getPrompt() {
     return prompt;
 }

 public String[] getOptions() {
     return Arrays.copyOf(options, options.length);
 }

 public int getCorrectIndex() {
     return correctIndex;
 }

 //--->50/50
 public String[] applyFiftyFifty() {
     Random rand = new Random();
     String[] reduced = new String[options.length];
     int keepWrong = -1;
     // collect wrong indices
     int[] wrongs = new int[options.length - 1];
     int wi = 0;
     for (int i = 1; i <= options.length; i++) {
         if (i != correctIndex) {
             wrongs[wi++] = i;
         }
     }
     keepWrong = wrongs[rand.nextInt(wrongs.length)];
     for (int i = 1; i <= options.length; i++) {
         if (i == correctIndex || i == keepWrong) {
             reduced[i - 1] = options[i - 1];
         } else {
             reduced[i - 1] = null;
         }
     }
     return reduced;
 }

//Random Split
 public int[] simulateAudiencePoll() {
     Random rand = new Random();
     int[] votes = new int[options.length];
     int remaining = 100;
     int correctVotes = 50 + rand.nextInt(21); // 50..70
     votes[correctIndex - 1] = correctVotes;
     remaining -= correctVotes;
     for (int i = 0; i < options.length; i++) {
         if (i == correctIndex - 1) continue;
         if (i == options.length - 1 || (i == options.length - 2 && remaining >= 0)) {
             votes[i] = remaining;
         } else {
             int share = rand.nextInt(remaining + 1);
             votes[i] = share;
             remaining -= share;
         }
     }
     return votes;
 }
}
