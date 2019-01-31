package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

/**
 * A classe BeansUtils é utilizada para receber todos os dados do formulario do
 * client-side. O primeiro argumento é relacionado de onde queremos pegar as
 * infromacoes. No nosso caso estamos recebendo o objeto pessoa do client O
 * segundo é para onde queremos copiar a informacao. No nosso caso para
 * pessoaSalva O ultimo parametro eh relacionado ao id (codigo), que eh null,
 * pois estamos passando o id pela url e nao pelo parametro
 * 
 * @author Roger
 *
 */
@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {

		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		return pessoaRepository.save(pessoaSalva);

	}


	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
	
	/**
	 * Passado este metodo para public porque eh utilizado para buscar pessoas pelo codigo
	 * Esta sendo utilizado no LancamentoService
	 * @param codigo
	 * @return
	 */
	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);

		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1); // Tamanho esperado
		}
		return pessoaSalva;
	}
}
