package com.kodnest.quizzApplication;

public class PrizeManager {

 private final long[] ladder = {0, 1000, 2000, 5000, 30000, 100000, 200000, 500000, 1000000, 2500000, 5000000};
 private long securedAt5 = 0;
 private long securedAt7 = 0;

 public void markCorrect(int questionNumber) {
     if (questionNumber == 5) securedAt5 = ladder[questionNumber];
     if (questionNumber == 7) securedAt7 = ladder[questionNumber];
 }

 public long getCurrentPrize(int questionNumber) {
     if (questionNumber >= 1 && questionNumber <= 10) return ladder[questionNumber];
     return 0;
 }

 public long computeTakeHomeOnWrong(int questionNumber) {
     if (questionNumber <= 5) {
         return 0;
     } else if (questionNumber <= 7) {
         return securedAt5;
     } else {
         return securedAt7;
     }
 }

 public long computeTakeHomeOnQuit(int questionNumber) {
    
     if (questionNumber <= 5) {
         if (questionNumber == 5) return securedAt5;
         return 0;
     } else if (questionNumber <= 7) {
         return securedAt5;
     } else {
         return securedAt7;
     }
 }
}
