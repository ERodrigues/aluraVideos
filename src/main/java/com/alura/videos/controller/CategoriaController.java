package com.alura.videos.controller;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CategoriaDto>> getCategorias(){
        return new ResponseEntity<>(categoriaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDto> getCategoria(@PathVariable long id){
        return new ResponseEntity<>(categoriaService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDto> postCategoria(@RequestBody @Valid CategoriaDto categoriaDto){
        return new ResponseEntity<>(categoriaService.save(categoriaDto), HttpStatus.OK);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDto> putCategoria(@RequestBody @Valid CategoriaDto categoriaDto, @PathVariable long id){
        return new ResponseEntity<>(categoriaService.refresh(categoriaDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable long id){
        var isRemoved = categoriaService.delete(id);

        if (!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Categoria Excluída com sucesso!", HttpStatus.OK);
    }
}
