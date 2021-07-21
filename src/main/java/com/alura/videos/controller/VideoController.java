package com.alura.videos.controller;

import com.alura.videos.dto.VideoDto;
import com.alura.videos.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public VideoDto postVideo(@RequestBody VideoDto videoDto){
        return videoService.save(videoDto);
    }
}
