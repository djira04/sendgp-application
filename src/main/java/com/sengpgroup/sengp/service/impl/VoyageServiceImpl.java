package com.sengpgroup.sengp.service.impl;

import com.sengpgroup.sengp.domain.Voyage;
import com.sengpgroup.sengp.repository.VoyageRepository;
import com.sengpgroup.sengp.service.VoyageService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Voyage}.
 */
@Service
@Transactional
public class VoyageServiceImpl implements VoyageService {

    private final Logger log = LoggerFactory.getLogger(VoyageServiceImpl.class);

    private final VoyageRepository voyageRepository;

    public VoyageServiceImpl(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    @Override
    public Voyage save(Voyage voyage) {
        log.debug("Request to save Voyage : {}", voyage);
        return voyageRepository.save(voyage);
    }

    @Override
    public Optional<Voyage> partialUpdate(Voyage voyage) {
        log.debug("Request to partially update Voyage : {}", voyage);

        return voyageRepository
            .findById(voyage.getId())
            .map(
                existingVoyage -> {
                    if (voyage.getDepatureCountry() != null) {
                        existingVoyage.setDepatureCountry(voyage.getDepatureCountry());
                    }
                    if (voyage.getDepatureAddress() != null) {
                        existingVoyage.setDepatureAddress(voyage.getDepatureAddress());
                    }
                    if (voyage.getDepatureCity() != null) {
                        existingVoyage.setDepatureCity(voyage.getDepatureCity());
                    }
                    if (voyage.getDepatureDate() != null) {
                        existingVoyage.setDepatureDate(voyage.getDepatureDate());
                    }
                    if (voyage.getDepatureTime() != null) {
                        existingVoyage.setDepatureTime(voyage.getDepatureTime());
                    }
                    if (voyage.getArrivalCountry() != null) {
                        existingVoyage.setArrivalCountry(voyage.getArrivalCountry());
                    }
                    if (voyage.getArrivalAddress() != null) {
                        existingVoyage.setArrivalAddress(voyage.getArrivalAddress());
                    }
                    if (voyage.getCityArrival() != null) {
                        existingVoyage.setCityArrival(voyage.getCityArrival());
                    }
                    if (voyage.getDateArrival() != null) {
                        existingVoyage.setDateArrival(voyage.getDateArrival());
                    }
                    if (voyage.getArrivalTime() != null) {
                        existingVoyage.setArrivalTime(voyage.getArrivalTime());
                    }
                    if (voyage.getKilos() != null) {
                        existingVoyage.setKilos(voyage.getKilos());
                    }
                    if (voyage.getUnitPrice() != null) {
                        existingVoyage.setUnitPrice(voyage.getUnitPrice());
                    }
                    if (voyage.getValid() != null) {
                        existingVoyage.setValid(voyage.getValid());
                    }

                    return existingVoyage;
                }
            )
            .map(voyageRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Voyage> findAll(Pageable pageable) {
        log.debug("Request to get all Voyages");
        return voyageRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Voyage> findOne(Long id) {
        log.debug("Request to get Voyage : {}", id);
        return voyageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Voyage : {}", id);
        voyageRepository.deleteById(id);
    }
}
