package presenter;

import repository.IMovieRepository;
import repository.IBookingRepository;
import repository.ISeatRepository;
import view.AdminConsoleView;

public class AdminPresenter implements IPresenter {
    private final AdminConsoleView view;

    public AdminPresenter(IMovieRepository movieRepo, IBookingRepository bookingRepo, ISeatRepository seatRepo) {
        this.view = new AdminConsoleView(movieRepo, bookingRepo);
    }

    public void start() {
        view.show();
    }
}
