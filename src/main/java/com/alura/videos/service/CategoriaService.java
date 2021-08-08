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

    public Page<CategoriaDto> getAll(Pageable pages) {
        return categoriaRepository.findAll(pages)
                .map(CategoriaDto::convert);
    }

    public CategoriaDto getById(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(CategoriaDto::convert).orElse(null);
    }

    public CategoriaDto save(CategoriaDto categoriaDto) {
        Categoria categoria = categoriaRepository.save(Categoria.convert(categoriaDto));
        return CategoriaDto.convert(categoria);
    }

    public CategoriaDto update(CategoriaDto categoriaDto, Long id) {
        if (getById(id) != null) {
            categoriaDto.setId(id);
            Categoria categoria = categoriaRepository.save(Categoria.convert(categoriaDto));
            return CategoriaDto.convert(categoria);
        }
        return null;
    }

    public boolean delete(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        categoria.ifPresent(value -> categoriaRepository.delete(value));
        return categoria.isPresent();
    }
}
