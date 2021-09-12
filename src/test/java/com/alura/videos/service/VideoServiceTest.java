package com.alura.videos.service;

import com.alura.videos.dto.CategoriaDto;
import com.alura.videos.dto.VideoDto;
import com.alura.videos.exception.CategoriaInexistenteException;
import com.alura.videos.model.Categoria;
import com.alura.videos.model.Video;
import com.alura.videos.repository.VideoRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("hml")
public class VideoServiceTest {
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private CategoriaService categoriaService;

    @Captor
    private ArgumentCaptor<Video> videoArgumentCaptor;

    @InjectMocks
    private VideoService videoService;


    private Pageable pages;
    private Video video;

    @BeforeEach
    public void setUp(){
        video = new Video(1L, "Novo Video", "Teste de video", "", null);
        pages = PageRequest.of(0, 5);
    }

    @Test
    public void dadoDoisRegistrosInseridosAoListaTodosOsRegistrosAMesmaQuantidadeDeveSerRetornada(){
        List<Video> videos = Arrays.asList(
            new Video(2L, "A vida como ela e", "Drama", "", null),
            new Video(3L, "Deby e Loyd", "Comedia", "", null)
        );

        Page<Video> PageVideos = new PageImpl<>(videos, pages, videos.size());

        when(videoRepository.findAll(pages)).thenReturn(PageVideos);
        Page<VideoDto> allVideos = videoService.listaTodos(pages);

        Assertions.assertEquals(allVideos.get().count(), videos.size());
    }

    @Test
    public void aoBuscarPorUmIdExistenteOServicoDeveRetornarVideoComMesmoIdTitulo(){
        Optional<Video> videoOptional = Optional.ofNullable(video);

        when(videoRepository.findById(1L)).thenReturn(videoOptional);
        VideoDto videoById = videoService.retornaPorId(1L);

        Assertions.assertEquals(videoById.getId(), videoOptional.get().getId());
        Assertions.assertEquals(videoById.getTitulo(), videoOptional.get().getTitulo());
    }

    @Test
    public void aoBuscaPorUmIdInexistenteOServicoDeveRetornarNulo(){
        VideoDto videoById = videoService.retornaPorId(1L);
        Assertions.assertNull(videoById);
    }

    @Test
    public void aoBuscarPorUmConjuntoDeCaracteresDoTituloExistenteOServicoDeveRetornarOVideoQueContanhaOsMesmosCaracteresNoTitulo(){
        List<Video> videos = Arrays.asList(
                new Video(1L, "A vida como ela e", "Drama", "", null)
        );

        Page<Video> pageVideos = new PageImpl<>(videos, pages, videos.size());
        when(videoRepository.findByTituloContainingIgnoreCase("Vida", pages)).thenReturn(pageVideos);
        Page<VideoDto> videoByTitulo = videoService.retornaListaPorTitulo("Vida", pages);

        Assertions.assertEquals(1, videoByTitulo.get().count());
    }

    // ao buscar um video por um id existente entao o servico deve retornar a entidade de video com mesmo Id e Titulo
    @Test
    public void aoBuscarPorUmConjuntoDeCaracteresDoTituloInexistenteOServicoDeveRetornarUmaListaVazia(){
        Page<Video> pageVideos = new PageImpl<>(new ArrayList<>(), pages, 0);
        when(videoRepository.findByTituloContainingIgnoreCase("Vida", pages)).thenReturn(pageVideos);
        Page<VideoDto> videoByTitulo = videoService.retornaListaPorTitulo("Vida", pages);

        Assertions.assertEquals(videoByTitulo.get().count(), pageVideos.get().count());
    }

    @Test
    public void aoBuscarPorUmaCategoriaExistenteRetornaQuantidadeDeVideosDestaCategoria(){
        Categoria categoria = new Categoria(1L, "LIVRE", "PRETO");

        List<Video> videos = Arrays.asList(
                new Video(2L, "A vida como ela e", "Drama", "", categoria),
                new Video(3L, "Deby e Loyd", "Comedia", "", categoria)
        );
        Page<Video> pageVideos = new PageImpl<>(videos, pages, videos.size());
        when(videoRepository.findByCategoriaId(1L, pages)).thenReturn(pageVideos);

        Page<VideoDto> videoByCategoria = videoService.retornaListaPorCategoria(1L, pages);

        Assertions.assertEquals(videoByCategoria.get().count(), pageVideos.get().count());
    }

    @Test
    public void aoBuscarPorUmaCategoriaInexistenteRetornadoUmaListaVazia(){
        Page<Video> pageVideos = new PageImpl<>(new ArrayList<>(), pages, 0);
        when(videoRepository.findByCategoriaId(1L, pages)).thenReturn(pageVideos);
        Page<VideoDto> videoByTitulo = videoService.retornaListaPorCategoria(1L, pages);

        Assertions.assertEquals(videoByTitulo.get().count(), pageVideos.get().count());
    }

    @Test
    public void aoInserirRegistroSemCategoriaACategoriaLivreDeveSerInformada(){
        VideoDto videoDto = new VideoDto("Teste", "Teste", "teste", null);
        CategoriaDto categoria = new CategoriaDto(1L, "LIVRE", "teste");

        when(categoriaService.retornaPorId(1L)).thenReturn(categoria);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        videoService.salvar(videoDto);

        verify(videoRepository).save(videoArgumentCaptor.capture());

        Video captureVideo = videoArgumentCaptor.getValue();
        Assertions.assertEquals("LIVRE", captureVideo.getCategoria().getTitulo());
    }

    @Test
    public void aoInserirUmRegistroComCategoriaValidaACategoriaInformadaDeveSerPersistida(){
        CategoriaDto categoria = new CategoriaDto(1L, "ACAO", "VERMELHA");
        VideoDto videoDto = new VideoDto("Teste", "Teste", "teste", categoria);

        when(categoriaService.retornaPorId(1L)).thenReturn(categoria);
        when(videoRepository.save(any(Video.class))).thenReturn(video);
        videoService.salvar(videoDto);

        verify(videoRepository).save(videoArgumentCaptor.capture());
        Video captureVideo = videoArgumentCaptor.getValue();
        Assertions.assertEquals("ACAO", captureVideo.getCategoria().getTitulo());
        Assertions.assertEquals("VERMELHA", captureVideo.getCategoria().getCor());
    }

    @Test
    public void aoInserirUmRegistroComCategoriaInvalidaAExecacaoDeCategoriaInvalidaDeveSerAcionada(){
        CategoriaDto categoria = new CategoriaDto(2L, "ACAO", "VERMELHA");
        VideoDto videoDto = new VideoDto("Teste", "Teste", "teste", categoria);

        when(categoriaService.retornaPorId(2L)).thenReturn(null);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        Assertions.assertThrows(CategoriaInexistenteException.class, ()-> videoService.salvar(videoDto));
    }

    @Test
    public void aoAtualizarUmRegistroComCategoriaInvalidaAExecacaoDeCategoriaInvalidaDeveSerAcionada(){
        CategoriaDto categoria = new CategoriaDto(2L, "ACAO", "VERMELHA");
        Optional<Video> videoOptional = Optional.ofNullable(video);

        VideoDto videoDto = new VideoDto("Alteracao de video", "", "", categoria);

        when(categoriaService.retornaPorId(2L)).thenReturn(null);
        when(videoRepository.findById(1L)).thenReturn(videoOptional);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        Assertions.assertThrows(CategoriaInexistenteException.class, ()-> videoService.salvar(videoDto));
    }

    @Test
    public void aoPassarUmIdValidoParaExclusaoRegistroEExcluido(){
        Optional<Video> videoOptional = Optional.ofNullable(video);

        when(videoRepository.findById(1L)).thenReturn(videoOptional);

        videoService.excluir(1L);
        verify(videoRepository).delete(video);
    }

    @Test
    public void videoNaoEExcluidoAoPassarUmIdDeVideoInexistente(){
        Assertions.assertFalse(videoService.excluir(1L));
    }

    @Test
    public void dadosDoVideoEAtualizadoQuandoUmIdExistenteForInformado(){
        CategoriaDto categoria = new CategoriaDto(1L, "ACAO", "VERMELHA");
        Optional<Video> videoOptional = Optional.ofNullable(video);

        VideoDto videoDto = new VideoDto("Alteracao de video", "", "", categoria);

        when(categoriaService.retornaPorId(1L)).thenReturn(categoria);
        when(videoRepository.findById(1L)).thenReturn(videoOptional);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        Assertions.assertNotNull(videoService.atualizar(1L, videoDto));
    }

    @Test
    public void dadosDoVideoNaoEAtualizadoQuandoUmIdInexistenteForInformado(){
        Optional<Video> videoOptional = Optional.empty();
        VideoDto videoDto = new VideoDto("Alteracao de video", "", "", null);

        when(videoRepository.findById(1L)).thenReturn(videoOptional);
        Assertions.assertNull(videoService.atualizar(1L, videoDto));
    }
}