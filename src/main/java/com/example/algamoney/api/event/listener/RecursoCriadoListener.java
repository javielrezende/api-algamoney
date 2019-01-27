package com.example.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	/**
	 * este metodo é da classe que foi impmenetada
	 */
	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long codigo = recursoCriadoEvent.getCodigo();

		adicionarHeaderLocation(response, codigo);

	}

	/**
	 * Utilizase a classe (ServletUriComponentsBuilder) para utilizar o metodo
	 * (fromCurrentRequestUri) para pegar a uri atual e adicionar um codigo (id)
	 * apos a insercao no banco. Isso devolve uma URI, por isso atribuise a variavel
	 * uri importada da classe do javaNet
	 * 
	 * Esta uri é adicionada ao header no location
	 * @param codigo
	 */
	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
