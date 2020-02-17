package br.com.casadocodigo.loja.beans;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Livro;

@Model
public class CarrinhoComprasBean {
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private CarrinhoCompras carrinho;
	
	public String add(Integer id) {
		Livro livro = livroDao.findById(id);
		carrinho.add(new CarrinhoItem(livro));
		
		return "carrinho?faces-redirect=true";
	}
	
	public List<CarrinhoItem> getItens() {
		return carrinho.getItens();
	}
	
	public BigDecimal getTotal() {
		return carrinho.getTotal();
	}
	
	public BigDecimal getTotal(CarrinhoItem carrinhoItem) {
		return carrinho.getTotal(carrinhoItem);
	}
	
	public void remover(CarrinhoItem carrinhoItem) {
		this.carrinho.remove(carrinhoItem);
	}
}