package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

// Esta classe é o controller
@RestController // metadados que ja retorna json, nao eh necessario transformar na logica
@RequestMapping("/categorias") // Quando for utilizado o /categoria na url, trara as requisições para esta
								// classe
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//Utiliza-se tal anotação para quando for solicitadoi um get em categorias
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	
	@PostMapping
	//Quando criar o dado na tabela, retornará o status 201, correto para insercoes no bd
	//@ResponseStatus(HttpStatus.CREATED)
	
	// Este RequestBody serve para receber o json da nova insercao e ja transformar em objeto automaticamente
	// O segundo argumento (HttpServletResponse) é utilizado para adequar este metodo conforme a solicitação do padrao REST
	// Quando algo é criado, deve-se entregar um location para o header de resposta para retornar os dados inseridos
	// Para melhorar a entrega do dado de insercao da API, pode-se retornar tambem o dado salvo, sem ser
	// pelo location, mas sim como json pelo body, para isso coloca-se como retorno do metodo (ResponseEntity) e tira-se o VOID
	// Validacao de notNull com o @Valid
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
		// Agora colocamos o resultado da insercao no bd para um objeto Categoria
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		// Utilizase a classe (ServletUriComponentsBuilder) para utilizar o metodo (fromCurrentRequestUri)
		// para pegar a uri atual e adicionar um codigo (id) apos a insercao no banco.
		// Isso devolve uma URI, por isso atribuise a variavel uri importada da classe do javaNet
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		// Esta uri é adicionada ao header no location
		response.setHeader("Location", uri.toASCIIString());
		//Aqui ele retorna que foi criado e pode-se tirar o status da segunda anotação para o método,
		// que foi comentado em baixa do POSTMAPPING
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	/**
	 * 
	 * @param codigo recebera o id da categoria. Utiliza-se esta anotacao para isto
	 * @return
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	} 
	
	
}
