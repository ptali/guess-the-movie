package sample.model;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsAny;

public class Game {

    private static final int MAX_WRONG_ATTEMPTS = 5;
    private String movie;
    private StringBuilder maskedMovie;
    private int attempts;
    private List<String> revealedLetters;

    public Game() {
        this.attempts = 0;
        this.revealedLetters = new ArrayList<>();
        movie = RandomMovie.instance.getMovie();
        maskedMovie = new StringBuilder(movie.replaceAll("[a-zA-Z0-9]", "_"));
    }

    public boolean isCorrectLetter(String letter) {
        if (containsAny(movie, letter) && !revealedLetters.contains(letter)) {
            revealedLetters.add(letter);
            revealLetter(letter);
            return true;
        } else {
            attempts++;
        }
        return false;
    }

    private void revealLetter(String letter) {
        for (int i = 0; i < movie.length(); i++) {
            if (movie.charAt(i) == letter.charAt(0)) {
                maskedMovie.replace(i, i + 1, letter);
            }
        }
    }

    public boolean isWin() {
        return maskedMovie.toString().equalsIgnoreCase(movie);
    }

    public boolean isGameOver() {
        return attempts > MAX_WRONG_ATTEMPTS;
    }

    public String getMovie() {
        return movie;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getGuessingMovie() {
        return maskedMovie.toString();
    }
}
