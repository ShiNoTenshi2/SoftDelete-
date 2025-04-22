package data;

import java.util.ArrayList;

public interface CRUD_Operation<S,T> {
    void save(S entity);
    void update(S entity);
    void delete(T id);
    S fetchById(T id);
    ArrayList<S> fetchAll();
    boolean authenticate(T id);

}