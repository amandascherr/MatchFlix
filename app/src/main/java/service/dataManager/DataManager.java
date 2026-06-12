package service.dataManager;

public interface DataManager {
    public void createData(DataDTO<?> data);
    public void deleteData(DataDTO<?> data);
    public DataDTO<?> readData(String id);
    public void appendData(String id, DataDTO<?> appendData);
}
