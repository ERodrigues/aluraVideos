package com.alura.videos.controller;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("listCategory")
    public ResponseEntity<Page<CategoriaDto>> listarTodos(@RequestParam int pagina, @RequestParam int itensPorPagina){
        Pageable paginas = PageRequest.of(pagina, itensPorPagina);
        return new ResponseEntity<>(categoriaService.listarTodos(paginas), HttpStatus.OK);
    }

    @ApiOperation(value="Retorna a categoria de acordo com o seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> retornaCategoriaPorId(@PathVariable Long id){
        return new ResponseEntity<>(categoriaService.retornaPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value="Cadastra uma categoria na base")
    @PostMapping
    @CacheEvict("listCategory")
    public ResponseEntity<CategoriaDto> salvarCategoria(@RequestBody @Valid CategoriaDto categoriaDto){
        return new ResponseEntity<>(categoriaService.salvar(categoriaDto), HttpStatus.OK);
    }

    @ApiOperation(value="Altera o registro de uma categoria de acordo com o seu ID")
    @PutMapping("/{id}")
    @CacheEvict("listCategory")
    public ResponseEntity<CategoriaDto> atualizarCategoria(@RequestBody @Valid CategoriaDto categoriaDto, @PathVariable Long id){
        return new ResponseEntity<>(categoriaService.atualizar(categoriaDto, id), HttpStatus.OK);
    }

    @ApiOperation(value="Exclui o registro de uma categoria de acordo com o seu ID")
    @DeleteMapping("/{id}")
    @CacheEvict("listCategory")
    public ResponseEntity<String> excluirCategoria(@PathVariable Long id){
        var isRemoved = categoriaService.excluir(id);

        if (!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Categoria Excluída com sucesso!", HttpStatus.OK);
    }
}
