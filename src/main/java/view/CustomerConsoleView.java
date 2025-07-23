package view;

import repository.IBookingRepository;
import repository.IMovieRepository;
import repository.ISeatRepository;
import repository.IShowRepository;
import model.Movie;
import model.Seat;
import model.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerConsoleView {
    private IMovieRepository movieRepo;
    private IBookingRepository bookingRepo;
    private ISeatRepository seatRepo;
    private IShowRepository showRepo;
    private Scanner sc;

    public CustomerConsoleView(IMovieRepository movieRepo, IBookingRepository bookingRepo, ISeatRepository seatRepo, IShowRepository showRepo) {
        this.movieRepo = movieRepo;
        this.bookingRepo = bookingRepo;
        this.seatRepo = seatRepo;
        this.showRepo = showRepo;
        this.sc = new Scanner(System.in);
    }

    public void show() {
        System.out.println("Welcome to Movie Booking Portal");

        while (true) {
            System.out.println("\n1. View Movies");
            System.out.println("2. Book Ticket");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewMovies();
                case 2 -> bookTicket();
                case 3 -> {
                    System.out.println("Thank you for using the portal.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewMovies() {
        List<Movie> movies = movieRepo.getAllMovies();
        System.out.println("\nAvailable Movies:");
        for (Movie movie : movies) {
            System.out.println("ID: " + movie.getId() + ", Title: " + movie.getTitle());
        }
    }

    private void bookTicket() {
        System.out.print("Enter Movie ID to book: ");
        int movieId = sc.nextInt();
        sc.nextLine();

        List<Show> shows = showRepo.getShowsByMovieId(movieId);
        if (shows.isEmpty()) {
            System.out.println("No shows available for this movie.");
            return;
        }

        System.out.println("\nAvailable Shows:");
        for (Show show : shows) {
            System.out.println("Show ID: " + show.getId() + ", Time: " + show.getTime());
        }

        System.out.print("Enter Show ID to book seats for: ");
        int showId = sc.nextInt();
        sc.nextLine();

        List<Seat> availableSeats = seatRepo.getAvailableSeatsByShowId(showId);
        if (availableSeats.isEmpty()) {
            System.out.println("No seats available for this show.");
            return;
        }

        System.out.println("\nAvailable Seats:");
        for (Seat seat : availableSeats) {
            System.out.println("Seat Number: " + seat.getSeatNumber() + " (" + seat.getSeatType() + ", Price: Rs." + seat.getPrice() + ")");
        }

        System.out.print("Enter seat numbers to book (comma separated): ");
        String input = sc.nextLine();
        String[] tokens = input.split(",");
        List<Integer> seatIdsToBook = new ArrayList<>();
        double totalPrice = 0;

        for (String token : tokens) {
            int seatNum = Integer.parseInt(token.trim());
            for (Seat seat : availableSeats) {
                if (seat.getSeatNumber() == seatNum) {
                    seatIdsToBook.add(seat.getSeatId());
                    totalPrice += seat.getPrice();
                    break;
                }
            }
        }

        if (seatIdsToBook.isEmpty()) {
            System.out.println("No valid seat numbers entered.");
            return;
        }

        System.out.println("Total amount to be paid: Rs." + totalPrice);
        System.out.print("Enter 'yes' to confirm payment: ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Payment cancelled.");
            return;
        }

        try {
            bookingRepo.addBooking(showId, seatIdsToBook);
            System.out.println("Booking successful for seat(s): " + input);
        } catch (Exception e) {
            System.out.println("Failed to book ticket: " + e.getMessage());
        }
    }
}
