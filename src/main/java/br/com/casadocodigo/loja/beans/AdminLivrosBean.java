package br.com.casadocodigo.loja.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

@Named
@RequestScoped
public class AdminLivrosBean {
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private AutorDao autorDao;
	
	@Inject
	private FacesContext context;
	private Livro livro = new Livro();
	private Part capaLivro;

	@Transactional
	public void save() {
		livro.setCapaPath(new FileSaver().write(capaLivro, "livros"));
		livroDao.save(livro);
		
		ExternalContext externalContext = context.getExternalContext();
		
		externalContext.getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

		clear();

		try {
			externalContext.redirect("lista.xhtml");
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void clear() {
		this.livro = new Livro();
	}
	
	public Part getCapaLivro() {
		return capaLivro;
	}
	
	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public List<Autor> getAutores() {
		return autorDao.findAll();
	}
}