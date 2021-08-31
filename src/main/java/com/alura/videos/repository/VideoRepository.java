package com.alura.videos.repository;

import com.alura.videos.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findByCategoriaId(Long categoriaId, Pageable pages);
    Page<Video> findByTituloContainingIgnoreCase (String search, Pageable pages);
}
