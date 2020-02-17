package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal total;

	public Pagamento(BigDecimal total) {
		this.total = total;
	}
	
	public Pagamento() {}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
