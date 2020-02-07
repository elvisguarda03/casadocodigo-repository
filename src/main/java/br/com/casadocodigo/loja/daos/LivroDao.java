package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Livro;

public class LivroDao {
	@PersistenceContext
	private EntityManager manager;
	
	public void save(Livro livro) {
		manager.persist(livro);
	}
	
	public List<Livro> findAll() {
		String jpql = "SELECT DISTINCT(l) FROM Livro l "
				+ "JOIN FETCH l.autores";
		
		return manager.createQuery(jpql, Livro.class)
				.getResultList();
	}
}