package com.alura.videos.controller;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("Categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    @ApiOperation(value="Apresenta todos as categorias cadastradas")
    public List<CategoriaDto> getCategorias(){
        return categoriaService.getAll();
    }

    @GetMapping("/categorias/{id}")
    public CategoriaDto getCategoria(@PathVariable long id){
        return categoriaService.getById(id);
    }

    @PostMapping("/categorias")
    public CategoriaDto postCategoria(@RequestBody @Valid CategoriaDto categoriaDto){
        return categoriaService.save(categoriaDto);
    }

    @PutMapping("/categorias/{id}")
    public CategoriaDto putCategoria(@RequestBody @Valid CategoriaDto categoriaDto, @PathVariable long id){
        return categoriaService.refresh(categoriaDto, id);
    }
}
