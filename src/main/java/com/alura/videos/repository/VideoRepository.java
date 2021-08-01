package com.alura.videos.repository;

import com.alura.videos.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query(value = "select b.id, b.titulo, b.descricao, b.url, b.id_categoria " +
                   "from videos.biblioteca b, videos.categoria c " +
                   "where b.id_categoria = c.id " +
                   "and c.id = :categoriaId", nativeQuery = true)
    public List<Video> getVideosByCategoria(@Param("categoriaId") Long categoriaId);
    public List<Video> findByTituloContainingIgnoreCase (String search);
}
