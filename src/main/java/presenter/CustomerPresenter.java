package presenter;

import repository.IMovieRepository;
import repository.IBookingRepository;
import repository.ISeatRepository;
import repository.IShowRepository;
import view.CustomerConsoleView;

public class CustomerPresenter implements IPresenter {
    private final CustomerConsoleView view;

    public CustomerPresenter(IMovieRepository movieRepo, IBookingRepository bookingRepo, ISeatRepository seatRepo, IShowRepository showRepo) {
        this.view = new CustomerConsoleView(movieRepo, bookingRepo, seatRepo, showRepo);
    }

    public void start() {
        view.show();
    }
}
