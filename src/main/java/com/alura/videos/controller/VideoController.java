package com.alura.videos.controller;

import com.alura.videos.dto.VideoDto;
import com.alura.videos.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<VideoDto>> getAll(@RequestParam int page, @RequestParam int size){
        Pageable pages = PageRequest.of(page, size);
        return new ResponseEntity<>(videoService.getAll(pages), HttpStatus.OK);
    }

    @ApiOperation(value="Retorna o vídeo de acordo com o seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long id){
        return new ResponseEntity<>(videoService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value="Cadastra um novo vídeo na base de dados")
    @PostMapping
    public ResponseEntity<VideoDto> saveVideo(@RequestBody @Valid VideoDto videoDto){
        return new ResponseEntity<>(videoService.save(videoDto), HttpStatus.OK);
    }

    @ApiOperation(value="Altera um video de acordo com o ID informado")
    @PutMapping("/alterar/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable long id, @RequestBody @Valid VideoDto videoDto){
        return new ResponseEntity<>(videoService.update(id, videoDto), HttpStatus.OK);
    }

    @ApiOperation(value="Deleta um video de acordo com o seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id){
        var isRemoved = videoService.delete(id);
        if (!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Video Excluído com sucesso", HttpStatus.OK);
    }

    @ApiOperation(value = "Busca videos de acordo com o seu titulo")
    @GetMapping("/")
    public ResponseEntity<Page<VideoDto>> getVideosByTitulo(@RequestParam String search, @RequestParam int page, @RequestParam int size){
        Pageable pages = PageRequest.of(page, size);
        return new ResponseEntity<>(videoService.getVideoByTitulo(search, pages), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna uma lista de videos de acordo com a categoria")
    @GetMapping("/categorias/{id}/videos")
    @Cacheable("VideoByCategoria")
    public ResponseEntity<Page<VideoDto>> getVideoByCategoria(@PathVariable Long id, @RequestParam int page, @RequestParam int size){
        Pageable pages = PageRequest.of(page, size);
        return new ResponseEntity<>(videoService.getVideoByCategoria(id, pages), HttpStatus.OK);
    }
}
