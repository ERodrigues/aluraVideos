package com.alura.videos.service;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.dto.VideoDto;
import com.alura.videos.model.Video;
import com.alura.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Page<VideoDto> getAll(Pageable pages) {
        Page<Video> videos = videoRepository.findAll(pages);
        return videos
                .map(VideoDto::convert);
    }

    public VideoDto getById(Long id) {
        Optional<Video> video = videoRepository.findById(id);
        return video.map(VideoDto::convert).orElse(null);
    }

    public VideoDto save(VideoDto videoDto) {
        if (videoDto.getCategoria() == null){
            videoDto.setCategoria(new CategoriaDto(1, "LIVRE", "Branco"));
        }
        Video video = videoRepository.save(Video.convert(videoDto));
        return VideoDto.convert(video);
    }

    public VideoDto update(Long id, VideoDto videoDto){
        if (getById(id) != null) {
            videoDto.setId(id);
            Video video = videoRepository.save(Video.convert(videoDto));
            return VideoDto.convert(video);
        }
        return null;
    }

    public Boolean delete(Long id){
        Optional<Video> video = videoRepository.findById(id);
        video.ifPresent(value -> videoRepository.delete(value));
        return video.isPresent();
    }

    public Page<VideoDto> getVideoByTitulo(String search, Pageable pages) {
        Page<Video> videos = videoRepository.findByTituloContainingIgnoreCase(search, pages);
        return videos
                .map(VideoDto::convert);
    }

    public Page<VideoDto> getVideoByCategoria(Long id, Pageable pages){
        Page<Video> videos = videoRepository.findByCategoriaId(id, pages);
        return videos
                .map(VideoDto::convert);
    }
}
