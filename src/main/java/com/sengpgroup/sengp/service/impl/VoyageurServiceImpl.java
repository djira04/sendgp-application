package com.sengpgroup.sengp.service.impl;

import com.sengpgroup.sengp.domain.Voyageur;
import com.sengpgroup.sengp.repository.VoyageurRepository;
import com.sengpgroup.sengp.service.VoyageurService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Voyageur}.
 */
@Service
@Transactional
public class VoyageurServiceImpl implements VoyageurService {

    private final Logger log = LoggerFactory.getLogger(VoyageurServiceImpl.class);

    private final VoyageurRepository voyageurRepository;

    public VoyageurServiceImpl(VoyageurRepository voyageurRepository) {
        this.voyageurRepository = voyageurRepository;
    }

    @Override
    public Voyageur save(Voyageur voyageur) {
        log.debug("Request to save Voyageur : {}", voyageur);
        return voyageurRepository.save(voyageur);
    }

    @Override
    public Optional<Voyageur> partialUpdate(Voyageur voyageur) {
        log.debug("Request to partially update Voyageur : {}", voyageur);

        return voyageurRepository
            .findById(voyageur.getId())
            .map(
                existingVoyageur -> {
                    if (voyageur.getFirstname() != null) {
                        existingVoyageur.setFirstname(voyageur.getFirstname());
                    }
                    if (voyageur.getLastname() != null) {
                        existingVoyageur.setLastname(voyageur.getLastname());
                    }
                    if (voyageur.getEmail() != null) {
                        existingVoyageur.setEmail(voyageur.getEmail());
                    }
                    if (voyageur.getPassword() != null) {
                        existingVoyageur.setPassword(voyageur.getPassword());
                    }
                    if (voyageur.getTelephone() != null) {
                        existingVoyageur.setTelephone(voyageur.getTelephone());
                    }
                    if (voyageur.getBornDate() != null) {
                        existingVoyageur.setBornDate(voyageur.getBornDate());
                    }
                    if (voyageur.getCin() != null) {
                        existingVoyageur.setCin(voyageur.getCin());
                    }
                    if (voyageur.getPhoto() != null) {
                        existingVoyageur.setPhoto(voyageur.getPhoto());
                    }

                    return existingVoyageur;
                }
            )
            .map(voyageurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Voyageur> findAll(Pageable pageable) {
        log.debug("Request to get all Voyageurs");
        return voyageurRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Voyageur> findOne(Long id) {
        log.debug("Request to get Voyageur : {}", id);
        return voyageurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Voyageur : {}", id);
        voyageurRepository.deleteById(id);
    }
}
