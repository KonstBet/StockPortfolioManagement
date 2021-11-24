package gr.aueb.team1.dao;

import java.util.List;


public interface DAO<T> {
	   public List<T> findAll();
	   public T findById(Integer id);
	   public T save(T t);
	   public T delete(T t);
}
