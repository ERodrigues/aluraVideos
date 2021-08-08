package com.alura.videos.repository;

import com.alura.videos.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    public Page<Video> findByCategoriaId(@Param("categoriaId") Long categoriaId, Pageable pages);
    public Page<Video> findByTituloContainingIgnoreCase (String search, Pageable pages);
}
