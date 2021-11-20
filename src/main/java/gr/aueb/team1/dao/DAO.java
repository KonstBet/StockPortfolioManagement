package gr.aueb.team1.dao;

import java.util.List;


public interface DAO<T> {
	   public List<T> findAll();
	   public T save(T t);
	   public void update(T t, String[] params);
	   public void delete(T t);
}
