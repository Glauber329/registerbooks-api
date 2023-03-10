package com.glauber.registerbooksapi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.glauber.registerbooksapi.DTOs.CategoriaDTO;
import com.glauber.registerbooksapi.domain.Categoria;
import com.glauber.registerbooksapi.repositories.CategoriaRepository;
import com.glauber.registerbooksapi.service.excptions.EntityNotFound;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria findById(Long id) {

		// PESQUISO NO BANCO DE DADOS SE EXISTE OU NAO, SE EXISTE ME RETORNA SE NAO ME
		// LANÇA EXCESSAO
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

		categoriaOptional.orElseThrow(() -> new EntityNotFound("Categoria Nao Existe"));
		// PESQUISO NO BANCO DE DADOS SE EXISTE OU NAO, SE EXISTE ME RETORNA SE NAO ME
		// LANÇA EXCESSAO

		return categoriaOptional.get();
	}

	public List<Categoria> findAll() {

		List<Categoria> list = categoriaRepository.findAll();

		return list;

		// List<CategoriaDTO> listDto = list.stream().map(obj -> new
		// CategoriaDTO(obj)).toList();
	}

	public Object save(Categoria categoria) {

		if (this.existsByNome(categoria.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ESSE NOME DE CATEGORIA JA EXISTE NA BASE DE DADOS");
		}

		return categoriaRepository.save(categoria);

	}

	public CategoriaDTO update(Long id, CategoriaDTO dto) {

		// obj RECEBE A PESQUISA DO BANCO E RETORNA O OBJETO OU A EXCESSAO
		Categoria obj = this.findById(id);

		obj.setNome(dto.getNome());
		obj.setDescricao(dto.getDescricao());

		categoriaRepository.save(obj);

		return new CategoriaDTO(obj);

	}

	// localhost:8086/categorias/id
	public void delete(Long id) {

		try {
			findById(id);
			categoriaRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new com.glauber.registerbooksapi.service.excptions.DataIntegrityViolationException(
					"Categoria Nao pode Ser Deletada Possui Livros Associados");
		}

	}

	private boolean existsByNome(String nome) {
		return categoriaRepository.existsByNome(nome);
	}

}
