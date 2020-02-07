package br.com.casadocodigo.loja.converters;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.casadocodigo.loja.models.Autor;

@FacesConverter("autorConverter")
public class AutorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (Objects.isNull(value) || value.isBlank()) {
			return null;
		}
		
		Autor autor = new Autor();
		autor.setId(Integer.valueOf(value));
		
		return autor;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (Objects.isNull(value)) {
			return null;
		}
		
		return ((Autor) value).getId().toString();
	}
}