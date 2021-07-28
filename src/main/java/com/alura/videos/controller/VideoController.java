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

    @ApiOperation(value="Apresenta todos os videos cadastrados")
    @GetMapping("/videos")
    public ResponseEntity<List<VideoDto>> getVideos(){
        return new ResponseEntity<>(videoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoDto> getVideo(@PathVariable long id){
        return new ResponseEntity<>(videoService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/video")
    public ResponseEntity<VideoDto> postVideo(@RequestBody @Valid VideoDto videoDto){
        return new ResponseEntity<>(videoService.save(videoDto), HttpStatus.OK);
    }

    @PutMapping("/videos/alterar/{id}")
    public ResponseEntity<VideoDto> putVideo(@PathVariable long id, @RequestBody @Valid VideoDto videoDto){
        return new ResponseEntity<>(videoService.refresh(id, videoDto), HttpStatus.OK);
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable long id){
        var isRemoved = videoService.delete(id);
        if (!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Video Excluído com sucesso", HttpStatus.OK);
    }
}
