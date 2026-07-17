package controller;

import model.User;

/**
 * Guarda o estado global da sessão: o usuário atualmente logado e a última ação
 * relevante ({@code logAction}), compartilhados entre as telas e a lógica de
 * match.
 */
public class Session {

    private static User loggedUser;
    public static String logAction = "";

    /**
     * @return o usuário logado, ou {@code null} se não houver sessão ativa.
     */
    public static User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Define o usuário logado na sessão.
     *
     * @param user usuário autenticado.
     */
    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    /**
     * Encerra a sessão, removendo o usuário logado.
     */
    public static void logout() {
        loggedUser = null;
    }
}