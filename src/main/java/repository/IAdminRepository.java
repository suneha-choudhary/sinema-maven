package repository;

public interface IAdminRepository {
    boolean validateAdminLogin(String username, String password);
}
