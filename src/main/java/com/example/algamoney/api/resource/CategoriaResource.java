package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
}
