package model;

public class Match extends Notification {

    private Movie movie;
    private Group group;

    public Match(Movie movie, Group group) {
        this.movie = movie;
        this.group = group;
    }

    public Movie getMovie() {
        return movie;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String getMessage() {
        return "\"" + movie.getTitle() +
                "\" foi um match para \"" +
                group.getName() + "\"!";
    }
}