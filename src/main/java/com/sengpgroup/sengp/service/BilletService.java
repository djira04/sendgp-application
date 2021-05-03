package com.sengpgroup.sengp.service;

import com.sengpgroup.sengp.domain.Billet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Billet}.
 */
public interface BilletService {
    /**
     * Save a billet.
     *
     * @param billet the entity to save.
     * @return the persisted entity.
     */
    Billet save(Billet billet);

    /**
     * Partially updates a billet.
     *
     * @param billet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Billet> partialUpdate(Billet billet);

    /**
     * Get all the billets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Billet> findAll(Pageable pageable);
    /**
     * Get all the Billet where Voyage is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Billet> findAllWhereVoyageIsNull();

    /**
     * Get the "id" billet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Billet> findOne(Long id);

    /**
     * Delete the "id" billet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
