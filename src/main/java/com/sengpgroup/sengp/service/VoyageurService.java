package com.sengpgroup.sengp.service;

import com.sengpgroup.sengp.domain.Voyageur;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Voyageur}.
 */
public interface VoyageurService {
    /**
     * Save a voyageur.
     *
     * @param voyageur the entity to save.
     * @return the persisted entity.
     */
    Voyageur save(Voyageur voyageur);

    /**
     * Partially updates a voyageur.
     *
     * @param voyageur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Voyageur> partialUpdate(Voyageur voyageur);

    /**
     * Get all the voyageurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Voyageur> findAll(Pageable pageable);

    /**
     * Get the "id" voyageur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Voyageur> findOne(Long id);

    /**
     * Delete the "id" voyageur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
