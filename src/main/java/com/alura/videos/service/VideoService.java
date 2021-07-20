package com.alura.videos.service;

import com.alura.videos.dto.VideoDto;
import com.alura.videos.model.Video;
import com.alura.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<VideoDto> getAll() {
        List<Video> videos = videoRepository.findAll();
        return videos
                .stream()
                .map(VideoDto::convert)
                .collect(Collectors.toList());
    }
}
