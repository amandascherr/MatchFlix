package model;

import dto.GroupDTO;
import dto.MatchDTO;

public class Match extends Notification {

    private final Integer movieId;
    private final String group;

    public Match(Integer movieId, String group) {
        this.movieId = movieId;
        this.group = group;
    }

    public Match(MatchDTO matchDTO){
        this.movieId = matchDTO.movieId();
        this.group = matchDTO.group();
    }

    public Integer getmovieId() {
        return movieId;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String getMessage() {
        return "\""  + movieId +
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
        return new MatchDTO(movieId, group);
    }

}