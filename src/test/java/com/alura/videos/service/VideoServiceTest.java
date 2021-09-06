package com.alura.videos.service;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.dto.VideoDto;
import com.alura.videos.model.Video;
import com.alura.videos.repository.VideoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("hml")
class VideoServiceTest {
    @Mock
    private VideoRepository videoRepository;
    @Captor
    private ArgumentCaptor<Video> videoArgumentCaptor;
    @InjectMocks
    private VideoService videoService;

    private Pageable pages;
    private Video video;

    @BeforeEach
    public void setUp(){
        video = new Video("Novo Video", "Teste de video", "", null);
        pages = PageRequest.of(0, 5);
    }

    @Test
    public void aoBuscarPorTodosRegistrosRetornaQuantidadeDeRegistrosExistentesNoBanco(){
        List<Video> videos = Arrays.asList(
            new Video("A vida como ela e", "Drama", "", null),
            new Video("Deby e Loyd", "Comedia", "", null)
        );

        Page<Video> PageVideos = new PageImpl<>(videos, pages, videos.size());

        when(videoRepository.findAll(pages)).thenReturn(PageVideos);
        Page<VideoDto> allVideos = videoService.getAll(pages);

        Assertions.assertEquals(allVideos.get().count(), videos.size());
    }

    @Test
    public void aoEfetuarBuscaPorVideoComDeterminadoIdExistenteNoBancoOMesmoDeveSerRetornado(){
        Optional<Video> videoOptional = Optional.ofNullable(video);
        videoOptional.get().setId(1L);

        when(videoRepository.findById(1L)).thenReturn(videoOptional);
        VideoDto videoById = videoService.getById(1L);

        Assertions.assertEquals(videoById.getId(), videoOptional.get().getId());
        Assertions.assertEquals(videoById.getTitulo(), videoOptional.get().getTitulo());
    }

    @Test
    public void aoInserirUmVideoSemCategoriaACategoriaLivreDeveSerInformada(){
        VideoDto videoDto = new VideoDto("Teste", "Teste", "teste", null);

        when(videoRepository.save(any(Video.class))).thenReturn(video);
        videoService.save(videoDto);

        verify(videoRepository).save(videoArgumentCaptor.capture());

        Video captureVideo = videoArgumentCaptor.getValue();
        Assertions.assertEquals("LIVRE", captureVideo.getCategoria().getTitulo());
    }

    @Test
    public void aoInserirUmVideoComCategoriaValidaACategoriaInformadaDeveSerPersistida(){
        VideoDto videoDto = new VideoDto("Teste", "Teste", "teste", new CategoriaDto(1,"ACAO", "VERMELHA"));

        when(videoRepository.save(any(Video.class))).thenReturn(video);
        videoService.save(videoDto);

        verify(videoRepository).save(videoArgumentCaptor.capture());
        Video captureVideo = videoArgumentCaptor.getValue();
        Assertions.assertEquals("ACAO", captureVideo.getCategoria().getTitulo());
        Assertions.assertEquals("VERMELHA", captureVideo.getCategoria().getCor());
    }
}