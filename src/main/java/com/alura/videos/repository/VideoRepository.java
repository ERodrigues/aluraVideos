package com.alura.videos.repository;

import com.alura.videos.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    public List<Video> findByCategoriaId(@Param("categoriaId") Long categoriaId);
    public List<Video> findByTituloContainingIgnoreCase (String search);
}
