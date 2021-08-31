package com.alura.videos.repository;

import com.alura.videos.model.Categoria;
import com.alura.videos.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("hml")
public class VideoRepositoryTest {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Pageable pages;

    @BeforeEach
    void initializer(){
        pages = PageRequest.of(0, 5);
    }

    @Test
    public void deveRetornarUmaListaDeVideosDeAcordoComACategoria(){
        Categoria categoria = testEntityManager.persist(new Categoria("Novo", "teste"));
        Video video = testEntityManager.persist(new Video("teste", "teste", "teste", categoria));

        Page<Video> videosByCategory = videoRepository.findByCategoriaId(categoria.getId(), pages);
        assertNotNull(videosByCategory);
    }

    @Test
    public void naoDeveRetornarNenhumVideoSeACategoriaNaoForInformada() {
        Page<Video> videosByCategory = videoRepository.findByCategoriaId(null, pages);
        assertEquals(0L, videosByCategory.getTotalElements());
    }
}