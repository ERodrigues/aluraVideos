package com.alura.videos.service;

import com.alura.videos.dto.VideoDto;
import com.alura.videos.model.Video;
import com.alura.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public VideoDto getById(long id) {
        Optional<Video> video = videoRepository.findById(id);
        return video.map(VideoDto::convert).orElse(null);
    }

    public VideoDto save(VideoDto videoDto) {
        Video video = videoRepository.save(Video.convert(videoDto));
        return VideoDto.convert(video);
    }

    public VideoDto refresh(long id, VideoDto videoDto){
        if (getById(id) != null) {
            videoDto.setId(id);
            Video video = videoRepository.save(Video.convert(videoDto));
            return VideoDto.convert(video);
        }
        return null;
    }
}
