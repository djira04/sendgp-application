package com.sengpgroup.sengp.service;

import com.sengpgroup.sengp.domain.Colis;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Colis}.
 */
public interface ColisService {
    /**
     * Save a colis.
     *
     * @param colis the entity to save.
     * @return the persisted entity.
     */
    Colis save(Colis colis);

    /**
     * Partially updates a colis.
     *
     * @param colis the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Colis> partialUpdate(Colis colis);

    /**
     * Get all the colis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Colis> findAll(Pageable pageable);

    /**
     * Get the "id" colis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Colis> findOne(Long id);

    /**
     * Delete the "id" colis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
