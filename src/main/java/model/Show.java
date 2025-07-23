package model;

public class Show {
    private int id;
    private int movieId;
    private String time;

    public Show() {}

    public Show(int id, int movieId, String time) {
        this.id = id;
        this.movieId = movieId;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
