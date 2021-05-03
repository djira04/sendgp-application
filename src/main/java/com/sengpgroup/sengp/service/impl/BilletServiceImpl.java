package com.sengpgroup.sengp.service.impl;

import com.sengpgroup.sengp.domain.Billet;
import com.sengpgroup.sengp.repository.BilletRepository;
import com.sengpgroup.sengp.service.BilletService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Billet}.
 */
@Service
@Transactional
public class BilletServiceImpl implements BilletService {

    private final Logger log = LoggerFactory.getLogger(BilletServiceImpl.class);

    private final BilletRepository billetRepository;

    public BilletServiceImpl(BilletRepository billetRepository) {
        this.billetRepository = billetRepository;
    }

    @Override
    public Billet save(Billet billet) {
        log.debug("Request to save Billet : {}", billet);
        return billetRepository.save(billet);
    }

    @Override
    public Optional<Billet> partialUpdate(Billet billet) {
        log.debug("Request to partially update Billet : {}", billet);

        return billetRepository
            .findById(billet.getId())
            .map(
                existingBillet -> {
                    if (billet.getLabel() != null) {
                        existingBillet.setLabel(billet.getLabel());
                    }
                    if (billet.getUrl() != null) {
                        existingBillet.setUrl(billet.getUrl());
                    }
                    if (billet.getWebsite() != null) {
                        existingBillet.setWebsite(billet.getWebsite());
                    }

                    return existingBillet;
                }
            )
            .map(billetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Billet> findAll(Pageable pageable) {
        log.debug("Request to get all Billets");
        return billetRepository.findAll(pageable);
    }

    /**
     *  Get all the billets where Voyage is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Billet> findAllWhereVoyageIsNull() {
        log.debug("Request to get all billets where Voyage is null");
        return StreamSupport
            .stream(billetRepository.findAll().spliterator(), false)
            .filter(billet -> billet.getVoyage() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Billet> findOne(Long id) {
        log.debug("Request to get Billet : {}", id);
        return billetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Billet : {}", id);
        billetRepository.deleteById(id);
    }
}
