package com.sengpgroup.sengp.service;

import com.sengpgroup.sengp.domain.Voyage;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Voyage}.
 */
public interface VoyageService {
    /**
     * Save a voyage.
     *
     * @param voyage the entity to save.
     * @return the persisted entity.
     */
    Voyage save(Voyage voyage);

    /**
     * Partially updates a voyage.
     *
     * @param voyage the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Voyage> partialUpdate(Voyage voyage);

    /**
     * Get all the voyages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Voyage> findAll(Pageable pageable);

    /**
     * Get the "id" voyage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Voyage> findOne(Long id);

    /**
     * Delete the "id" voyage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
