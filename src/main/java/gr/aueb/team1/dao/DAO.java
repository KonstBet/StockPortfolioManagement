package gr.aueb.team1.dao;

import java.util.List;

public interface DAO<T> {
	   public List<T> getAll();
	   public T get(T id);
	   public void update(T object);
	   public void delete(T object);
}
