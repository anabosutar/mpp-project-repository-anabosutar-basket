package repositories;
import model.User;
public interface UserRepository extends IRepository<Integer,User> {
    User findBy(String username, String pass);
}
