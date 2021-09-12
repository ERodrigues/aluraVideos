package com.alura.videos.service;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.model.Categoria;
import com.alura.videos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<CategoriaDto> listarTodos(Pageable paginas) {
        return categoriaRepository.findAll(paginas)
                .map(CategoriaDto::converter);
    }

    public CategoriaDto retornaPorId(Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        return categoria.map(CategoriaDto::converter).orElse(null);
    }

    public CategoriaDto salvar(CategoriaDto categoriaDto) {
        Categoria categoria = categoriaRepository.save(Categoria.convert(categoriaDto));
        return CategoriaDto.converter(categoria);
    }

    public CategoriaDto atualizar(CategoriaDto categoriaDto, Long idCategoria) {
        if (retornaPorId(idCategoria) != null) {
            categoriaDto.setId(idCategoria);
            return salvar(categoriaDto);
        }
        return null;
    }

    public boolean excluir(Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        categoria.ifPresent(value -> categoriaRepository.delete(value));
        return categoria.isPresent();
    }
}
