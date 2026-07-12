package model;

public class Match extends Notification {

    private String movie;
    private String group;

    public Match(String movie, String group) {
        this.movie = movie;
        this.group = group;
    }

    public Match(MatchDTO matchDTO){
        this.movie = matchDTO.movie();
        this.group = matchDTO.group();
    }

    public String getMovie() {
        return movie;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String getMessage() {
        return "\""  +
                "\" foi um match para \"" +
                group + "\"!";
    }

        /**
     * Converte este grupo na sua representacao serializavel.
     *
     * @return um {@link GroupDTO} com o estado persistivel deste grupo.
     */
    @Override
    public MatchDTO toDTO(){
        return new MatchDTO(movie, group);
    }

}