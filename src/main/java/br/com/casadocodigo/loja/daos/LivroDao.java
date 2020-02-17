package br.com.casadocodigo.loja.daos;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.annotations.QueryHints;

import br.com.casadocodigo.loja.models.Livro;

@Stateful
public class LivroDao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
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

	public List<Livro> ultimosLancamentos() {
		String jpql = "SELECT l FROM Livro l ORDER BY l.dataPublicacao DESC";
		return manager.createQuery(jpql, Livro.class)
				.setMaxResults(5)
				.setHint(QueryHints.CACHEABLE, true)
				.getResultList();
	}
	
	public List<Livro> demaisLivros() {
		String jpql = "SELECT l FROM Livro l ORDER BY l.dataPublicacao DESC";
		return manager.createQuery(jpql, Livro.class)
				.setFirstResult(5)
				.setHint(QueryHints.CACHEABLE, true)
				.getResultList();
	}

	public Livro findById(Integer id) {
		return manager.find(Livro.class, id);
	}
}