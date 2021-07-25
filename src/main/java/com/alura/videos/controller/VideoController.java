package com.alura.videos.controller;

import com.alura.videos.dto.VideoDto;
import com.alura.videos.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/videos")
    public List<VideoDto> getVideos(){
        return videoService.getAll();
    }

    @GetMapping("/videos/{id}")
    public VideoDto getVideo(@PathVariable long id){
        return videoService.getById(id);
    }

    @PostMapping("/video")
    public VideoDto postVideo(@RequestBody @Valid VideoDto videoDto){
        return videoService.save(videoDto);
    }

    @PutMapping("/videos/alterar/{id}")
    public VideoDto putVideo(@PathVariable long id, @RequestBody @Valid VideoDto videoDto){
        return videoService.refresh(id, videoDto);
    }

    @DeleteMapping("/videos/{id}")
    public VideoDto deleteVideo(@PathVariable long id){
        return videoService.delete(id);
    }
}
