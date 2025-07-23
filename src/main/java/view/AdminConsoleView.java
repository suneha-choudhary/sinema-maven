package view;

import java.util.Scanner;

import repository.IMovieRepository;
import repository.IBookingRepository;
import view.handler.AddMovieHandler;
import view.handler.DeleteMovieHandler;
import view.handler.DeleteShowHandler;
import view.handler.ViewBookingsHandler;
import view.handler.ViewMoviesHandler;

public class AdminConsoleView {
    private final Scanner sc = new Scanner(System.in);
    private final IMovieRepository movieRepo;
    private final IBookingRepository bookingRepo;

    public AdminConsoleView(IMovieRepository movieRepo, IBookingRepository bookingRepo) {
        this.movieRepo = movieRepo;
        this.bookingRepo = bookingRepo;
    }

    public void show() {
        System.out.println("\nWelcome Admin!!. \nSelect option:");
        while (true) {
            System.out.println("\n1. View All Movies");
            System.out.println("2. Add Movie");
            System.out.println("3. Delete Movie");
            System.out.println("4. View All Bookings");
            System.out.println("5. Delete Show");
            System.out.println("6. Exit");
            System.out.print("\nChoice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> new ViewMoviesHandler(movieRepo).handle();
                case 2 -> new AddMovieHandler(movieRepo, sc).handle();
                case 3 -> new DeleteMovieHandler(movieRepo, sc).handle();
                case 4 -> new ViewBookingsHandler(bookingRepo).handle();
                case 5 -> new DeleteShowHandler(movieRepo, sc).handle();
                case 6 -> {
                    System.out.println("Exiting Admin Panel");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
