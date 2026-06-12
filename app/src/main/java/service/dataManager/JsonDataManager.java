package service.dataManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Salva cada {@link DataDTO} como um arquivo JSON na pasta de resources.
 * O campo {@code id} vira o nome do arquivo ({@code <id>.json}) e o campo
 * {@code body} e gravado como conteudo. O nome da classe do body tambem e
 * salvo para que a leitura consiga reconstruir o objeto.
 */
public class JsonDataManager implements DataManager {

    private static final Path BASE_DIR = Path.of("src", "main", "resources", "data");

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createData(DataDTO<?> data) {
        write(data.id(), data.body());
    }

    @Override
    public void deleteData(DataDTO<?> data) {
        fileFor(data.id()).delete();
    }

    @Override
    public DataDTO<?> readData(String id) {
        try {
            Map<?, ?> file = mapper.readValue(fileFor(id), Map.class);
            Class<?> bodyClass = Class.forName((String) file.get("type"));
            Record body = (Record) mapper.convertValue(file.get("body"), bodyClass);
            return new DataDTO<>(id, body);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] Falha ao ler o dado com id: " + id);
            return null;
        }
    }

    @Override
    public void appendData(String id, DataDTO<?> appendData) {
        File file = fileFor(id);
        String type = appendData.body().getClass().getName();
        List<Object> body = new ArrayList<>();
        try {
            if (file.exists()) {
                Map<?, ?> content = mapper.readValue(file, Map.class);
                
                String existingType = (String) content.get("type");
                if (!type.equals(existingType)) {
                    System.out.println("[ERROR] Tipo incompativel para o id: " + id);
                    return;
                }

                Object existing = content.get("body");
                if (existing instanceof List<?> list) {
                    body.addAll(list);
                } else {
                    body.add(existing);
                }
            }
            body.add(appendData.body());
            write(id, type, body);
        } catch (IOException e) {
            System.out.println("[ERROR] Falha ao adicionar o dado ao id: " + id);
        }
    }

    private void write(String id, Record body) {
        write(id, body.getClass().getName(), body);
    }

    private void write(String id, String type, Object body) {
        try {
            Files.createDirectories(BASE_DIR);
            Map<String, Object> content = Map.of(
                "type", type,
                "body", body
            );
            mapper.writeValue(fileFor(id), content);
        } catch (IOException e) {
            System.out.println("[ERROR] Falha ao gravar o dado com id: " + id);
        }
    }

    private File fileFor(String id) {
        return BASE_DIR.resolve(id + ".json").toFile();
    }
}
