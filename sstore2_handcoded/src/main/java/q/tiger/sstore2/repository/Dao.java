package q.tiger.sstore2.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    
    List<T> getall();

    Optional<T> search(T t);

    Optional<T> searchById(int id);

    void add(T t);

    void remove(int id);

    void update(T t);
    
}
