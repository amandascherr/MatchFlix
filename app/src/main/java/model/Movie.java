package model;

public class Movie {

    private int id;
    private String title;
    private String description;
    private String posterPath;

    public Movie() {
    }

    public Movie(
            int id,
            String title,
            String description,
            String posterPath
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}