package core;

public class WordGuesser {
    
    private boolean isSubstring = false;
    private String word;
    private String substring;
    private int score = 0;
    private int life = 3;
    private int timer = 30;





    public void isSubstring(String word, String substring) {
        boolean result = word.contains(substring);
        
        // Set the global variable isSubstring based on the result
        this.isSubstring = result;
    }

    public void guess(String word, String substring){
        if (isSubstring){
            nextWord();
            this.score++;
        }

        else{
            this.timer-=5;

        }

    }

    public void nextWord() {
        
    }

    public void loseLife() {
        if (this.timer<1){
            this.life--;
        }
    }

}
