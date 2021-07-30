package com.alura.videos.controller;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.dto.VideoDto;
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
    @ApiOperation(value="Retorna todas categorias cadastradas")
    public ResponseEntity<List<CategoriaDto>> getCategorias(){
        return new ResponseEntity<>(categoriaService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value="Retorna a categoria de acordo com o seu ID")
    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDto> getCategoria(@PathVariable long id){
        return new ResponseEntity<>(categoriaService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value="Cadastra uma categoria na base")
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDto> postCategoria(@RequestBody @Valid CategoriaDto categoriaDto){
        return new ResponseEntity<>(categoriaService.save(categoriaDto), HttpStatus.OK);
    }

    @ApiOperation(value="Altera o registro de uma categoria de acordo com o seu ID")
    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDto> putCategoria(@RequestBody @Valid CategoriaDto categoriaDto, @PathVariable long id){
        return new ResponseEntity<>(categoriaService.refresh(categoriaDto, id), HttpStatus.OK);
    }

    @ApiOperation(value="Exclui o registro de uma categoria de acordo com o seu ID")
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable long id){
        var isRemoved = categoriaService.delete(id);

        if (!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Categoria Excluída com sucesso!", HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna uma lista de videos de acordo com a categoria")
    @GetMapping("/categorias/{id}/videos")
    public ResponseEntity<List<VideoDto>> getVideosPorCategoria(@PathVariable long id){
        return new ResponseEntity<>(categoriaService.getVideoByCategoria(id), HttpStatus.OK);
    }
}
