package com.alura.videos.service;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.dto.VideoDto;
import com.alura.videos.exception.CategoriaInexistenteException;
import com.alura.videos.model.Video;
import com.alura.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoriaService categoriaService;

    public Page<VideoDto> listaTodos(Pageable paginas) {
        Page<Video> videos = videoRepository.findAll(paginas);
        return videos
                .map(VideoDto::converter);
    }

    public VideoDto retornaPorId(Long idVideo) {
        Optional<Video> video = videoRepository.findById(idVideo);
        return video.map(VideoDto::converter).orElse(null);
    }

    public VideoDto salvar(VideoDto videoDto) {
        videoDto.setCategoria(retornaCategoriaValida(videoDto.getCategoria()));
        if (categoriaValida(videoDto.getCategoria())){
            Video video = videoRepository.save(Video.convert(videoDto));
            return VideoDto.converter(video);
        }
        throw new CategoriaInexistenteException("Categoria informada no registro não esta cadastrada!");
    }

    public VideoDto atualizar(Long idVideo, VideoDto videoDto){
        if (retornaPorId(idVideo) != null) {
            videoDto.setId(idVideo);
            return salvar(videoDto);
        }
        return null;
    }

    public Boolean excluir(Long idVideo){
        Optional<Video> video = videoRepository.findById(idVideo);
        video.ifPresent(value -> videoRepository.delete(value));
        return video.isPresent();
    }

    public Page<VideoDto> retornaListaPorTitulo(String tituloPesquisado, Pageable paginas) {
        Page<Video> videos = videoRepository.findByTituloContainingIgnoreCase(tituloPesquisado, paginas);
        return videos
                .map(VideoDto::converter);
    }

    public Page<VideoDto> retornaListaPorCategoria(Long idCategoria, Pageable paginas){
        Page<Video> videos = videoRepository.findByCategoriaId(idCategoria, paginas);
        return videos
                .map(VideoDto::converter);
    }

    private CategoriaDto retornaCategoriaValida(CategoriaDto categoriaDto){
        if (categoriaDto == null){
            return new CategoriaDto(1, "LIVRE", "Branco");
        }
        return categoriaDto;
    }

    private Boolean categoriaValida(CategoriaDto categoriaDto){
        return (categoriaService.retornaPorId(categoriaDto.getId()) != null);
    }
}
