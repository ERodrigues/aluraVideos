package com.alura.videos.controller;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Api("Categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @ApiOperation(value="Retorna todas categorias cadastradas")
    public ResponseEntity<Page<CategoriaDto>> getAll(@RequestParam int page, @RequestParam int size){
        Pageable pages = PageRequest.of(page, size);
        return new ResponseEntity<>(categoriaService.getAll(pages), HttpStatus.OK);
    }

    @ApiOperation(value="Retorna a categoria de acordo com o seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoriaService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value="Cadastra uma categoria na base")
    @PostMapping
    public ResponseEntity<CategoriaDto> saveCategory(@RequestBody @Valid CategoriaDto categoriaDto){
        return new ResponseEntity<>(categoriaService.save(categoriaDto), HttpStatus.OK);
    }

    @ApiOperation(value="Altera o registro de uma categoria de acordo com o seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategory(@RequestBody @Valid CategoriaDto categoriaDto, @PathVariable Long id){
        return new ResponseEntity<>(categoriaService.update(categoriaDto, id), HttpStatus.OK);
    }

    @ApiOperation(value="Exclui o registro de uma categoria de acordo com o seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id){
        var isRemoved = categoriaService.delete(id);

        if (!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Categoria Excluída com sucesso!", HttpStatus.OK);
    }
}
