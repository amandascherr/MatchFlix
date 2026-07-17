package model;

/**
 * Entidade que representa um filme obtido da API TMDB. É a unidade curtida
 * pelos usuários e contabilizada pelos grupos na regra de match.
 */
public class Movie {

    private int id;
    private String title;
    private String description;
    private String posterPath;

    /**
     * Cria um filme vazio, para preenchimento posterior via setters.
     */
    public Movie() {
    }

    /**
     * Cria um filme com todos os seus atributos.
     *
     * @param id          identificador do filme na TMDB.
     * @param title       título do filme.
     * @param description sinopse do filme.
     * @param posterPath  caminho do pôster na TMDB.
     */
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