package dto;

import java.util.ArrayList;
import java.util.Map;

/**
 * Representacao serializavel de um {@link model.Group}.
 * <p>
 * Guarda apenas o estado que faz sentido persistir (nome, numero de usuarios e
 * os likes acumulados), sem o {@link model.observer.Publisher} nem os
 * inscritos, que sao estado de runtime e causariam referencia circular na
 * serializacao.
 * </p>
 *
 * @param id           identificador unico do grupo.
 * @param name         nome do grupo.
 * @param numOfUsers   numero de membros do grupo.
 * @param likedMovies  contagem de curtidas por id de filme.
 * @param groupMatches ids dos filmes que ja deram match.
 */
public record GroupDTO(
    String id,
    String name,
    int numOfUsers,
    Map<Integer, Integer> likedMovies,
    ArrayList<Integer> groupMatches
) {}
