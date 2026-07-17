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

    /** {@return o id do filme na TMDB} */
    public int getId() {
        return id;
    }

    /** {@return o titulo do filme} */
    public String getTitle() {
        return title;
    }

    /** {@return a sinopse do filme} */
    public String getDescription() {
        return description;
    }

    /** {@return o caminho do poster do filme na TMDB} */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Define o id do filme.
     *
     * @param id id do filme na TMDB.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define o titulo do filme.
     *
     * @param title titulo do filme.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Define a sinopse do filme.
     *
     * @param description sinopse do filme.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Define o caminho do poster do filme.
     *
     * @param posterPath caminho do poster na TMDB.
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}