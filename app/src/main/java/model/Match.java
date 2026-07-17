package model;

import dto.MatchDTO;

/**
 * Notificação que representa um match: um filme curtido por todos os membros de
 * um grupo. É gerada por {@link Group#checkMatch(Integer)} e entregue aos
 * usuários do grupo.
 */
public class Match extends Notification {

    private final Integer movieId;
    private final String group;

    /**
     * Cria um match para o filme e grupo informados.
     *
     * @param movieId id do filme que deu match.
     * @param group   nome do grupo em que o match ocorreu.
     */
    public Match(Integer movieId, String group) {
        this.movieId = movieId;
        this.group = group;
    }

    /**
     * Reconstrói um match a partir do seu {@link MatchDTO} persistido.
     *
     * @param matchDTO representação serializada do match.
     */
    public Match(MatchDTO matchDTO){
        this.movieId = matchDTO.movieId();
        this.group = matchDTO.group();
    }

    /** {@return o id do filme que deu match} */
    public Integer getmovieId() {
        return movieId;
    }

    /** {@return o nome do grupo em que o match ocorreu} */
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
     * Converte este match na sua representação serializável.
     *
     * @return um {@link MatchDTO} com o filme e o grupo do match.
     */
    @Override
    public MatchDTO toDTO(){
        return new MatchDTO(movieId, group);
    }

}