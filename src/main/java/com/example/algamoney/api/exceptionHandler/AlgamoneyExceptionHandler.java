package com.example.algamoney.api.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author Roger
 *
 *         Classe que esta herndando captura exceções para respostas das
 *         entidades Anotacao para indicar que eh um controller mas advice
 *         porque scaneio toda a aplicação
 */
@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * MessageSource utilizado para pegar os erros do arquivo messages.properties
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Tratamento de mensagens que não foi possivel a leitura dos dados para a
	 * persistencia Como no momento que se quer adicionar campos que nao existem na
	 * tabela
	 * 
	 * O retorno tem um metodo que pode-se alterar o corpo da mensagem (body), o
	 * status, headers, etc...
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		/**
		 * esse metodo primeiramente recebe a mensagem que se quer recuperar do arquivo
		 * messages.properties o segundo eh se tiver algum outro parametro necessario e
		 * o terceiro é o local atual Estes erros foram transofrmados em uma lista para
		 * ser enviado ao usuario
		 */
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Recupera argumentos de metodos que nao sao validos o que nao passar pela
	 * validacao passara para esse metodo
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		// getFieldErrors recupera os erros que acontecerao em todos os campos
		// referentes a tabela do bd
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}

		return erros;
	}

	/**
	 * 
	 * @author Roger Classe que ira receber as mensagens e jogar as duas mensagens
	 *         para dentro do handleException do return de cima
	 */
	public static class Erro {
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

	}

}
