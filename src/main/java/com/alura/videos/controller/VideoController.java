package com.alura.videos.controller;

import com.alura.videos.dto.VideoDto;
import com.alura.videos.exception.CategoriaInexistenteException;
import com.alura.videos.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/videos")
@Api("Video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value="Retorna todos os videos cadastrados")
    @GetMapping
    public ResponseEntity<Page<VideoDto>> listarTodos(@RequestParam int pagina, @RequestParam int itensPorPagina){
        Pageable paginas = PageRequest.of(pagina, itensPorPagina);
        return new ResponseEntity<>(videoService.listaTodos(paginas), HttpStatus.OK);
    }

    @ApiOperation(value="Retorna o vídeo de acordo com o seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> retornaVideoPorId(@PathVariable Long id){
        return new ResponseEntity<>(videoService.retornaPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value="Cadastra um novo vídeo na base de dados")
    @PostMapping
    public ResponseEntity<VideoDto> salvarVideo(@RequestBody @Valid VideoDto videoDto){
        try{
            return new ResponseEntity<>(videoService.salvar(videoDto), HttpStatus.OK);
        } catch (CategoriaInexistenteException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="Altera um video de acordo com o ID informado")
    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> atualizarVideo(@PathVariable Long id, @RequestBody @Valid VideoDto videoDto){
        try{
            return new ResponseEntity<>(videoService.atualizar(id, videoDto), HttpStatus.OK);
        } catch (CategoriaInexistenteException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="Deleta um video de acordo com o seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirVideo(@PathVariable Long id){
        var foiRemovido = videoService.excluir(id);
        if (!foiRemovido){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Video Excluído com sucesso", HttpStatus.OK);
    }

    @ApiOperation(value = "Busca videos de acordo com o seu titulo")
    @GetMapping("/")
    public ResponseEntity<Page<VideoDto>> retornaVideosPorTitulo(@RequestParam String titulo, @RequestParam int pagina, @RequestParam int itensPorPagina){
        Pageable paginas = PageRequest.of(pagina, itensPorPagina);
        return new ResponseEntity<>(videoService.retornaListaPorTitulo(titulo, paginas), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna uma lista de videos de acordo com a categoria")
    @GetMapping("/categorias/{idCategoria}/videos")
    public ResponseEntity<Page<VideoDto>> retornaVideosPorCategoria(@PathVariable Long idCategoria, @RequestParam int pagina, @RequestParam int itensPorPagina){
        Pageable paginas = PageRequest.of(pagina, itensPorPagina);
        return new ResponseEntity<>(videoService.retornaListaPorCategoria(idCategoria, paginas), HttpStatus.OK);
    }
}
