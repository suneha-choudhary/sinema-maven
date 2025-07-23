package view.handler;

import java.util.Scanner;

import repository.IMovieRepository;

public class DeleteMovieHandler {
    private final IMovieRepository repo;
    private final Scanner sc;

    public DeleteMovieHandler(IMovieRepository repo, Scanner sc) {
        this.repo = repo;
        this.sc = sc;
    }

    public void handle() {
        System.out.print("Enter movie ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();
        boolean success = repo.removeMovie(id);
        if (success) {
            System.out.println("Movie deleted successfully.");
        } else {
            System.out.println("Movie not found or could not be deleted.");
        }
    }
}
