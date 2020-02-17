package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;

import br.com.casadocodigo.loja.daos.CompraDao;

@Named
@SessionScoped
public class CarrinhoCompras implements Serializable {
	private static final long serialVersionUID = 1L;

	private Set<CarrinhoItem> itens = 
			new HashSet<>();
	
	@Inject
	private CompraDao compraDao;

	public void add(CarrinhoItem item) {
		itens.add(item);
	}
	
	public List<CarrinhoItem> getItens() {
		return new ArrayList<>(itens);
	}
	
	public void setItens(Set<CarrinhoItem> itens) {
		this.itens = itens;
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		for (CarrinhoItem carrinhoItem : itens) {
			total = total.add(carrinhoItem.getLivro().getPreco()
					.multiply(BigDecimal.valueOf(carrinhoItem.getQuantidade())));
		}
		
		return total;
	}
	
	public Integer getQuantidadeTotal() {
		return itens.stream().mapToInt(i -> i.getQuantidade())
				.sum();
	}
	
	public void remove(CarrinhoItem carrinhoItem) {
		this.itens.remove(carrinhoItem);
	}
	
	public void finalizar(Compra compra) {
		compra.setItens(toJson());
		compra.setTotal(getTotal());
		
		compraDao.save(compra);
	}

	private String toJson() {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		
		for (CarrinhoItem carrinhoItem : itens) {
			builder.add(Json.createObjectBuilder()
					.add("titulo", carrinhoItem.getLivro().getTitulo())
					.add("preco", carrinhoItem.getLivro().getPreco())
					.add("quantidade", carrinhoItem.getQuantidade())
					.add("total", getTotal(carrinhoItem))
			);
		}
		
		return builder.build()
				.toString();
	}

	public BigDecimal getTotal(CarrinhoItem carrinhoItem) {
		return carrinhoItem.getLivro().getPreco()
					.multiply(BigDecimal.valueOf(carrinhoItem.getQuantidade()));
	}
}