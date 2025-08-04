package com.kodnest.quizzApplication;

import java.util.Scanner;

public class QuizGame {
 private final Question[] questions;
 private final LifelineManager lifelines = new LifelineManager();
 private final PrizeManager prizeManager = new PrizeManager();
 private final Scanner sc = new Scanner(System.in);

 public QuizGame() {
     questions = new Question[] {
         new Question("Which planet is known as the Red Planet?",
                 new String[] {"1. Earth", "2. Mars", "3. Venus", "4. Jupiter"}, 2),
         new Question("Who wrote 'Romeo and Juliet'?",
                 new String[] {"1. William Shakespeare", "2. Charles Dickens", "3. Jane Austen", "4. Mark Twain"}, 1),
         new Question("What is the capital of Japan?",
                 new String[] {"1. Tokyo", "2. Beijing", "3. Seoul", "4. Bangkok"}, 1),
         new Question("Which element has the chemical symbol 'O'?",
                 new String[] {"1. Oxygen", "2. Gold", "3. Silver", "4. Iron"}, 1),
         new Question("Who painted the Mona Lisa?",
                 new String[] {"1. Pablo Picasso", "2. Leonardo da Vinci", "3. Vincent van Gogh", "4. Michelangelo"}, 2),
         new Question("What is the largest mammal in the world?",
                 new String[] {"1. Elephant", "2. Blue Whale", "3. Giraffe", "4. Hippopotamus"}, 2),
         new Question("How many continents are there?",
                 new String[] {"1. 5", "2. 6", "3. 7", "4. 8"}, 3),
         new Question("Who is known as the Father of Computers?",
                 new String[] {"1. Charles Babbage", "2. Alan Turing", "3. Thomas Edison", "4. Nikola Tesla"}, 1),
         new Question("What is the boiling point of water at sea level in Celsius?",
                 new String[] {"1. 90°C", "2. 100°C", "3. 80°C", "4. 120°C"}, 2),
         new Question("Which gas do humans exhale?",
                 new String[] {"1. Oxygen", "2. Carbon Dioxide", "3. Nitrogen", "4. Hydrogen"}, 2)
     };
 }

 public void start() {
     for (int i = 0; i < questions.length; i++) {
         int qNum = i + 1;
         Question q = questions[i];
         System.out.println("--------------------------------------------------");
         System.out.println("Question " + qNum + ": " + q.getPrompt());
         String[] displayOpts = q.getOptions();
         printOptions(displayOpts);

         // show lifelines
         System.out.print("Lifelines available: ");
         if (lifelines.isAudienceAvailable()) System.out.print("[A]udience Poll ");
         if (lifelines.isFiftyAvailable()) System.out.print("[5]0/50 ");
         System.out.println("[Q]uit");

         System.out.print("Enter answer (1-4), lifeline (A/5), or Q to quit: ");
         String input = sc.next().trim();
         boolean usedFiftyNow = false;
         boolean usedAudienceNow = false;

         // lifeline or quit
         if (input.equalsIgnoreCase("A") && lifelines.isAudienceAvailable()) {
             lifelines.useAudience();
             usedAudienceNow = true;
             showAudience(q);
             System.out.print("Answer now (1-4) or Q to quit: ");
             input = sc.next().trim();
         } else if (input.equals("5") && lifelines.isFiftyAvailable()) {
             lifelines.useFifty();
             usedFiftyNow = true;
             displayOpts = q.applyFiftyFifty();
             printOptions(displayOpts);
             System.out.print("Answer now (1-4) from remaining or Q to quit: ");
             input = sc.next().trim();
         } else if (input.equalsIgnoreCase("Q")) {
             long take = prizeManager.computeTakeHomeOnQuit(qNum);
             System.out.println(ConsoleColor.PURPLE + "You quit. You take home: Rs. " + take + ConsoleColor.RESET);
             return;
         }

         // Lifelines Used or not
         if (!usedAudienceNow && lifelines.isAudienceAvailable() && input.equalsIgnoreCase("A")) {
             lifelines.useAudience();
             usedAudienceNow = true;
             showAudience(q);
             System.out.print("Answer now (1-4) or Q to quit: ");
             input = sc.next().trim();
         }
         if (!usedFiftyNow && lifelines.isFiftyAvailable() && input.equals("5")) {
             lifelines.useFifty();
             usedFiftyNow = true;
             displayOpts = q.applyFiftyFifty();
             printOptions(displayOpts);
             System.out.print("Answer now (1-4) from remaining or Q to quit: ");
             input = sc.next().trim();
         }
         if (input.equalsIgnoreCase("Q")) {
             long take = prizeManager.computeTakeHomeOnQuit(qNum);
             System.out.println(ConsoleColor.PURPLE + "You quit. You take home: Rs. " + take + ConsoleColor.RESET);
             return;
         }

         int answer;
         try {
             answer = Integer.parseInt(input);
         } catch (Exception e) {
             answer = -1;
         }

         if (answer == q.getCorrectIndex()) {
             System.out.println(ConsoleColor.GREEN + "✅ Congratulations! Correct answer." + ConsoleColor.RESET);
             prizeManager.markCorrect(qNum);
             if (qNum == 10) {
                 System.out.println(ConsoleColor.PURPLE + "🎉 You've finished the game! You won Rs. " +
                         prizeManager.getCurrentPrize(qNum) + ConsoleColor.RESET);
                 return;
             }
             System.out.println("You have won so far: Rs. " + prizeManager.getCurrentPrize(qNum));
         } else {
             System.out.println(ConsoleColor.RED + "❌ Sorry! Wrong answer." + ConsoleColor.RESET);
             long takeHome = prizeManager.computeTakeHomeOnWrong(qNum);
             System.out.println(ConsoleColor.PURPLE + "You take home: Rs. " + takeHome + ConsoleColor.RESET);
             return;
         }
     }
 }

 private void printOptions(String[] opts) {
     for (String o : opts) {
         if (o != null) System.out.println(o);
     }
 }

 private void showAudience(Question q) {
     int[] poll = q.simulateAudiencePoll();
     System.out.println("Audience Poll results:");
     for (int i = 0; i < poll.length; i++) {
         System.out.printf("Option %d: %d%%%n", i + 1, poll[i]);
     }
 }
}
