package model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private List<String> showTimes;

    public Movie(int id, String title) {
        this.id = id;
        this.title = title;
        this.showTimes = new ArrayList<>();
    }

    public Movie(String title, String time) {
        this.title = title;
        this.showTimes = new ArrayList<>();
        this.showTimes.add(time);
    }

    public void addShowTime(String time) {
        showTimes.add(time);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getShowTimes() {
        return showTimes;
    }

    public int getId() {
        return id;
    }
}
