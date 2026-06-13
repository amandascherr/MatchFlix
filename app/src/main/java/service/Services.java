package service;

import service.dataManager.DataManager;
import service.dataManager.JsonDataManager;

/**
 * Ponto de acesso estatico aos servicos compartilhados da aplicacao.
 * Mantem uma unica instancia de {@link DataManager} usada para persistencia.
 */
public final class Services {

    private static final DataManager dataManager = new JsonDataManager();

    private Services() {
    }

    /**
     * Devolve a instancia compartilhada de {@link DataManager}.
     *
     * @return o gerenciador de dados da aplicacao.
     */
    public static DataManager getManager() {
        return dataManager;
    }
}
