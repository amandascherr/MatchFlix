package service;

import model.Movie;
import model.User;

/**
 * Serviço responsável pelas avaliações de filmes feitas pelos usuários.
 */
public class RatingService {

    public RatingService() {
    }

    /**
     * Registra a curtida de um filme por um usuário, delegando a
     * {@link User#userLike(Movie)}.
     *
     * @param user  usuário que curtiu.
     * @param movie filme curtido.
     */
    public void likeMovie(User user, Movie movie){
      user.userLike(movie);
    }
}
