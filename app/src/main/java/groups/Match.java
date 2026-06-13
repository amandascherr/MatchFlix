package groups;

import model.Movie;

public class Match {
  Movie movie;
  Group group;

  public Match(Movie movie, Group group){
    this.movie = movie;
    this.group = group;
  }
  
  public Movie getMovie(){
    return movie;
  }

  public Group getGroup(){
    return group;
  }

}
