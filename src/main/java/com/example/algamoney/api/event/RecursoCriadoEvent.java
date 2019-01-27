package com.example.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author Roger
 *
 *Quando criamos este classe exetendemos ApplicationEvent do Spring
 *Apos isso o nome da Classe criada ficara com erro. No STS clica-se ctrl + 1 e adiciona-se o constructor
 *para tirar o erro.
 *Depois adiciona-se o Serial version id
 *
 */
public class RecursoCriadoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	private Long codigo;

	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}
	
	

}
