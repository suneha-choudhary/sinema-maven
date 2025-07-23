import presenter.AdminPresenter;
import presenter.CustomerPresenter;
import presenter.IPresenter;
import repository.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IMovieRepository movieRepo = new MovieRepository();
        IBookingRepository bookingRepo = new BookingRepository();
        ISeatRepository seatRepo = new SeatRepository();
        IAdminRepository adminRepo = new AdminRepository();
        IShowRepository showRepo = new ShowRepository();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Login as:");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            System.out.println();
            IPresenter presenter;

            switch (choice) {
                case 1 -> {
                    scanner.nextLine();
                    System.out.print("Enter admin username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (adminRepo.validateAdminLogin(username, password)) {
                        presenter = new AdminPresenter(movieRepo, bookingRepo, seatRepo);
                    } else {
                        System.out.println("Invalid login.");
                        return;
                    }
                }
                case 2 -> presenter = new CustomerPresenter(movieRepo, bookingRepo, seatRepo, showRepo);
                default -> {
                    System.out.println("Invalid choice.");
                    return;
                }
            }

            presenter.start();
        }
    }
}
