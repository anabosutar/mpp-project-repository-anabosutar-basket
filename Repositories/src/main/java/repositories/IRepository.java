package repositories;

import java.util.Collection;
import java.util.List;

public interface IRepository<ID,T> {
    void add(T elem);
    void delete(ID id);
    void update(T elem);
    T findById(ID id);
    List<T> getAll();
}
