package view.handler;

import java.util.Scanner;

import model.Movie;
import repository.IMovieRepository;

public class AddMovieHandler {
    private final IMovieRepository repo;
    private final Scanner sc;

    public AddMovieHandler(IMovieRepository repo, Scanner sc) {
        this.repo = repo;
        this.sc = sc;
    }

    public void handle() {
        System.out.println("Add New Movie");
        // if (sc.hasNextLine()) sc.nextLine();
        System.out.print("Enter movie title: ");
        String title = sc.nextLine().trim();


        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        if (repo.isMovieExists(title)) {
            System.out.println("Movie with this title already exists.");
            return;
        }

        System.out.print("Enter show time (e.g., 18:00): ");
        String time = sc.nextLine().trim();

        Movie movie = new Movie(title, time);
        repo.addMovie(movie, time);
        System.out.println("Movie added successfully.");
    }
}
