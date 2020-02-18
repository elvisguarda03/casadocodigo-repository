package br.com.casadocodigo.loja.beans;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Livro;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("livros")
public class LivroResource {
	@Inject
	private LivroDao dao;
	
	@GET
	@Path("lancamentos")
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	@Wrapped(element = "livros")
	public List<Livro> ultimosLancamentosJson() {
		return dao.ultimosLancamentos();
	}
}