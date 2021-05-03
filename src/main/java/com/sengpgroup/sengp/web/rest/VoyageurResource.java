package com.sengpgroup.sengp.web.rest;

import com.sengpgroup.sengp.domain.Voyageur;
import com.sengpgroup.sengp.repository.VoyageurRepository;
import com.sengpgroup.sengp.service.VoyageurService;
import com.sengpgroup.sengp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sengpgroup.sengp.domain.Voyageur}.
 */
@RestController
@RequestMapping("/api")
public class VoyageurResource {

    private final Logger log = LoggerFactory.getLogger(VoyageurResource.class);

    private static final String ENTITY_NAME = "voyageur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoyageurService voyageurService;

    private final VoyageurRepository voyageurRepository;

    public VoyageurResource(VoyageurService voyageurService, VoyageurRepository voyageurRepository) {
        this.voyageurService = voyageurService;
        this.voyageurRepository = voyageurRepository;
    }

    /**
     * {@code POST  /voyageurs} : Create a new voyageur.
     *
     * @param voyageur the voyageur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voyageur, or with status {@code 400 (Bad Request)} if the voyageur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voyageurs")
    public ResponseEntity<Voyageur> createVoyageur(@RequestBody Voyageur voyageur) throws URISyntaxException {
        log.debug("REST request to save Voyageur : {}", voyageur);
        if (voyageur.getId() != null) {
            throw new BadRequestAlertException("A new voyageur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Voyageur result = voyageurService.save(voyageur);
        return ResponseEntity
            .created(new URI("/api/voyageurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voyageurs/:id} : Updates an existing voyageur.
     *
     * @param id the id of the voyageur to save.
     * @param voyageur the voyageur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyageur,
     * or with status {@code 400 (Bad Request)} if the voyageur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voyageur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voyageurs/{id}")
    public ResponseEntity<Voyageur> updateVoyageur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Voyageur voyageur
    ) throws URISyntaxException {
        log.debug("REST request to update Voyageur : {}, {}", id, voyageur);
        if (voyageur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyageur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Voyageur result = voyageurService.save(voyageur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voyageur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voyageurs/:id} : Partial updates given fields of an existing voyageur, field will ignore if it is null
     *
     * @param id the id of the voyageur to save.
     * @param voyageur the voyageur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyageur,
     * or with status {@code 400 (Bad Request)} if the voyageur is not valid,
     * or with status {@code 404 (Not Found)} if the voyageur is not found,
     * or with status {@code 500 (Internal Server Error)} if the voyageur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voyageurs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Voyageur> partialUpdateVoyageur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Voyageur voyageur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Voyageur partially : {}, {}", id, voyageur);
        if (voyageur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyageur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Voyageur> result = voyageurService.partialUpdate(voyageur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voyageur.getId().toString())
        );
    }

    /**
     * {@code GET  /voyageurs} : get all the voyageurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voyageurs in body.
     */
    @GetMapping("/voyageurs")
    public ResponseEntity<List<Voyageur>> getAllVoyageurs(Pageable pageable) {
        log.debug("REST request to get a page of Voyageurs");
        Page<Voyageur> page = voyageurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /voyageurs/:id} : get the "id" voyageur.
     *
     * @param id the id of the voyageur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voyageur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voyageurs/{id}")
    public ResponseEntity<Voyageur> getVoyageur(@PathVariable Long id) {
        log.debug("REST request to get Voyageur : {}", id);
        Optional<Voyageur> voyageur = voyageurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voyageur);
    }

    /**
     * {@code DELETE  /voyageurs/:id} : delete the "id" voyageur.
     *
     * @param id the id of the voyageur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voyageurs/{id}")
    public ResponseEntity<Void> deleteVoyageur(@PathVariable Long id) {
        log.debug("REST request to delete Voyageur : {}", id);
        voyageurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
