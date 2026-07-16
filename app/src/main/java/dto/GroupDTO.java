package dto;

import java.util.ArrayList;
import java.util.Map;

/**
 * Representacao serializavel de um {@link Group}.
 * <p>
 * Guarda apenas o estado que faz sentido persistir (nome, numero de usuarios e
 * os likes acumulados), sem o {@link model.observer.Publisher} nem os
 * inscritos, que sao estado de runtime e causariam referencia circular na
 * serializacao.
 * </p>
 */
public record GroupDTO(
    String id,
    String name,
    int numOfUsers,
    Map<Integer, Integer> likedMovies,
    ArrayList<Integer> groupMatches
) {}
