package br.com.casadocodigo.loja.beans;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.models.Compra;
import br.com.casadocodigo.loja.service.PagamentoGateway;

@Path("/pagamento")
public class PagamentoService {
	@Context
	private ServletContext context;

	@Inject
	private PagamentoGateway pagamentoGateway;

	@Inject
	private CompraDao compraDao;
	
	@Inject
	private MailSender mailSender;

	private static ExecutorService executor = Executors.newFixedThreadPool(50);

	@POST
	public void pagar(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid) {
		Compra compra = compraDao.findByUUID(uuid);

		String contextPath = context.getContextPath();
		
		executor.submit(() -> {
			try {
				String message = pagamentoGateway.pagar(compra.getTotal());

				URI uriLocation = UriBuilder.fromPath("http://localhost:9000" + 
							contextPath + "/index.xhtml")
						.queryParam("msg", message)
						.build();
	
				Response response = Response.seeOther(uriLocation)
						.build();
				
				String messageBody = "Sua compra foi realizada com sucesso, com o número de pedido " + compra.getUuid();
				
				mailSender.send("compras@casadocodigo.com.br", compra.getUsuario().getEmail(), 
						"Nova Compra na CDC", messageBody);
				
				ar.resume(response);
			} catch (Exception e) {
				ar.resume(new WebApplicationException(e));
			}
		});
	}
}