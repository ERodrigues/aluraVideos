package com.alura.videos.controller;

import com.alura.videos.dto.VideoDto;
import com.alura.videos.model.Video;
import com.alura.videos.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("Video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value="Retorna todos os videos cadastrados")
    @GetMapping("/videos")
    public ResponseEntity<List<VideoDto>> getVideos(){
        return new ResponseEntity<>(videoService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value="Retorna o vídeo de acordo com o seu ID")
    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoDto> getVideo(@PathVariable long id){
        return new ResponseEntity<>(videoService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value="Cadastra um novo vídeo na base de dados")
    @PostMapping("/video")
    public ResponseEntity<VideoDto> postVideo(@RequestBody @Valid VideoDto videoDto){
        return new ResponseEntity<>(videoService.save(videoDto), HttpStatus.OK);
    }

    @ApiOperation(value="Altera um video de acordo com o ID informado")
    @PutMapping("/videos/alterar/{id}")
    public ResponseEntity<VideoDto> putVideo(@PathVariable long id, @RequestBody @Valid VideoDto videoDto){
        return new ResponseEntity<>(videoService.refresh(id, videoDto), HttpStatus.OK);
    }

    @ApiOperation(value="Deleta um video de acordo com o seu ID")
    @DeleteMapping("/videos/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable long id){
        var isRemoved = videoService.delete(id);
        if (!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Video Excluído com sucesso", HttpStatus.OK);
    }
}
