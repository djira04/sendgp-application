package com.sengpgroup.sengp.service.impl;

import com.sengpgroup.sengp.domain.Colis;
import com.sengpgroup.sengp.repository.ColisRepository;
import com.sengpgroup.sengp.service.ColisService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Colis}.
 */
@Service
@Transactional
public class ColisServiceImpl implements ColisService {

    private final Logger log = LoggerFactory.getLogger(ColisServiceImpl.class);

    private final ColisRepository colisRepository;

    public ColisServiceImpl(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }

    @Override
    public Colis save(Colis colis) {
        log.debug("Request to save Colis : {}", colis);
        return colisRepository.save(colis);
    }

    @Override
    public Optional<Colis> partialUpdate(Colis colis) {
        log.debug("Request to partially update Colis : {}", colis);

        return colisRepository
            .findById(colis.getId())
            .map(
                existingColis -> {
                    if (colis.getWeight() != null) {
                        existingColis.setWeight(colis.getWeight());
                    }
                    if (colis.getDetails() != null) {
                        existingColis.setDetails(colis.getDetails());
                    }

                    return existingColis;
                }
            )
            .map(colisRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Colis> findAll(Pageable pageable) {
        log.debug("Request to get all Colis");
        return colisRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Colis> findOne(Long id) {
        log.debug("Request to get Colis : {}", id);
        return colisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Colis : {}", id);
        colisRepository.deleteById(id);
    }
}
