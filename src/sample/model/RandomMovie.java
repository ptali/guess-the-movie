package sample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RandomMovie {

    static RandomMovie instance = new RandomMovie();
    private Random random;
    private List<String> moviesList;


    private RandomMovie() {
        this.random = new Random();
        loadMovies();
    }

    String getMovie() {
        String movie;
        int rnd = random.nextInt(moviesList.size());
        movie = moviesList.get(rnd);
        return movie.toUpperCase();
    }

    private void loadMovies() {
        moviesList = new ArrayList<>();
        Path path = Paths.get("movies.txt");
        String movieTitle;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            while ((movieTitle = br.readLine()) != null) {
                moviesList.add(movieTitle.trim());
            }
        } catch (IOException e) {
            System.out.println("Couldn't load the movies...");
        }
    }
}
