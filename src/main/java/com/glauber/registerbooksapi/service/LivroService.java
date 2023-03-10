package com.glauber.registerbooksapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.glauber.registerbooksapi.domain.Categoria;
import com.glauber.registerbooksapi.domain.Livro;
import com.glauber.registerbooksapi.repositories.LivroRepository;
import com.glauber.registerbooksapi.resources.exceptions.FieldUnique;
import com.glauber.registerbooksapi.service.excptions.EntityNotFound;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private CategoriaService categoriaService;

	public Livro findById(Long id) {

		Optional<Livro> obj = livroRepository.findById(id);

		obj.orElseThrow(() -> new EntityNotFound("Livro Nao Encontrado Id: " + id + " Tipo " + Livro.class.getName()));

		return obj.get();
	}

	// PESQUISA TODOS OS LIVROS DE DETERMINADA CATEGORIA PESQUISADA PELO ID COM
	// QUERY CUSTOMER
	public List<Livro> findAllLivroPerCategoria(Long id_cat) {

		categoriaService.findById(id_cat); // PESQUISA CATEGORIA NO BANCO PASSANDO O ID

		return livroRepository.findAllLivroPerCategoria(id_cat);

	}

	public Livro update(Long id, Livro livro) {

		Livro newObj = this.findById(id);
		updateDataLivro(newObj, livro);

		return livroRepository.save(newObj);

	}

	// APENAS PARA A CLASSE LIVROSERVICE
	private void updateDataLivro(Livro newObj, Livro livro) {

		newObj.setTitulo(livro.getTitulo());
		newObj.setNome_autor(livro.getNome_autor());
		newObj.setTexto(livro.getTexto());
		newObj.setCategoria(livro.getCategoria());
	}

	public Livro create(Long id_cate, Livro livro) {

		try {
			livro.setId(null);
			Categoria categoria = categoriaService.findById(id_cate);
			livro.setCategoria(categoria);

			return livroRepository.save(livro);

		} catch (DataIntegrityViolationException e) {

			throw new FieldUnique("CAMPO DUPLICADO");
		}

	}

	public void delete(Long id) {

		Livro obj = this.findById(id);

		livroRepository.delete(obj);

	}
}
