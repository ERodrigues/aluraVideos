package com.alura.videos.service;

import com.alura.videos.controller.CategoriaController;
import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.model.Categoria;
import com.alura.videos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDto> getAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaDto::convert)
                .collect(Collectors.toList());
    }

    public CategoriaDto getById(long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(CategoriaDto::convert).orElse(null);
    }

    public CategoriaDto save(CategoriaDto categoriaDto) {
        Categoria categoria = categoriaRepository.save(Categoria.convert(categoriaDto));
        return CategoriaDto.convert(categoria);
    }
}
