package com.tekup.gestionimmobil.dao.repositories;

import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByEtatAnnonce(EtatAnnonce etatAnnonce);
    
    Page<Annonce> findByEtatAnnonce(EtatAnnonce etatAnnonce, Pageable pageable);
    
    @Query("SELECT a FROM Annonce a WHERE " +
           "(:type is null OR a.immobilier.type = :type) AND " +
           "(:ville is null OR LOWER(a.immobilier.ville) LIKE LOWER(CONCAT('%', :ville, '%'))) AND " +
           "(:minPrix is null OR a.immobilier.prix >= :minPrix) AND " +
           "(:maxPrix is null OR a.immobilier.prix <= :maxPrix) AND " +
           "a.etatAnnonce = :etatAnnonce")
    Page<Annonce> findByFilters(
        @Param("type") String type,
        @Param("ville") String ville,
        @Param("minPrix") Double minPrix,
        @Param("maxPrix") Double maxPrix,
        @Param("etatAnnonce") EtatAnnonce etatAnnonce,
        Pageable pageable);
}