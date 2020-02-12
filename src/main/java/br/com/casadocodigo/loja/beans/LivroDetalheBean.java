package br.com.casadocodigo.loja.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Livro;

@Named
@SessionScoped
public class LivroDetalheBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LivroDao dao;

	private Integer id;
	private Livro livro;
	
	public void carregarDetalhe() {
		this.livro = dao.findById(id);
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
}