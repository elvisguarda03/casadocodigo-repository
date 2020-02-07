package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Autor;

public class AutorDao {
	@PersistenceContext
	private EntityManager manager;
	
	public List<Autor> findAll() {
		return manager.createQuery("FROM Autor", Autor.class)
				.getResultList();
	}
}