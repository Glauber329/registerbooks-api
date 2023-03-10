package com.glauber.registerbooksapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glauber.registerbooksapi.DTOs.CategoriaDTO;
import com.glauber.registerbooksapi.domain.Categoria;
import com.glauber.registerbooksapi.service.CategoriaService;

import jakarta.validation.Valid;

@CrossOrigin("*") // ANOTAÇAO PARA O FRONT END ANGULAR
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired()
	private CategoriaService categoriaService;

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Long id) {

		Categoria categoria = categoriaService.findById(id);

		return ResponseEntity.ok().body(categoria);
	}

	@GetMapping
	public ResponseEntity<List<Categoria>> findall() {

		List<Categoria> list = categoriaService.findAll();

		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	public ResponseEntity<Object> save(@Valid @RequestBody Categoria categoria) {

		return ResponseEntity.status(HttpStatus.CREATED).
				body(categoriaService.save(categoria));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> update(@Valid @PathVariable Long id, @RequestBody CategoriaDTO dto) {

		CategoriaDTO newObj = categoriaService.update(id, dto);

		return ResponseEntity.ok().body(newObj);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		categoriaService.delete(id);

		return ResponseEntity.ok().body("Categoria Deletado Com Sucesso ID: " + id + Categoria.class.getName());

	}
}
