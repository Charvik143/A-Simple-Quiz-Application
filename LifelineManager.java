package com.kodnest.quizzApplication;

public class LifelineManager {
 private boolean audienceUsed = false;
 private boolean fiftyUsed = false;

 public boolean isAudienceAvailable() {
     return !audienceUsed;
 }

 public boolean isFiftyAvailable() {
     return !fiftyUsed;
 }

 public void useAudience() {
     audienceUsed = true;
 }

 public void useFifty() {
     fiftyUsed = true;
 }
}
