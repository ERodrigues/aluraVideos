package com.alura.videos.service;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.dto.VideoDto;
import com.alura.videos.model.Categoria;
import com.alura.videos.model.Video;
import com.alura.videos.repository.CategoriaRepository;
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
import org.springframework.test.context.ActiveProfiles;

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

    private Video video;

    @BeforeEach
    public void setUp(){
        video = new Video("Novo Video", "Teste de video", "", null);
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