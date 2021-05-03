package com.sengpgroup.sengp.web.rest;

import com.sengpgroup.sengp.domain.Colis;
import com.sengpgroup.sengp.repository.ColisRepository;
import com.sengpgroup.sengp.service.ColisService;
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
 * REST controller for managing {@link com.sengpgroup.sengp.domain.Colis}.
 */
@RestController
@RequestMapping("/api")
public class ColisResource {

    private final Logger log = LoggerFactory.getLogger(ColisResource.class);

    private static final String ENTITY_NAME = "colis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColisService colisService;

    private final ColisRepository colisRepository;

    public ColisResource(ColisService colisService, ColisRepository colisRepository) {
        this.colisService = colisService;
        this.colisRepository = colisRepository;
    }

    /**
     * {@code POST  /colis} : Create a new colis.
     *
     * @param colis the colis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colis, or with status {@code 400 (Bad Request)} if the colis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colis")
    public ResponseEntity<Colis> createColis(@RequestBody Colis colis) throws URISyntaxException {
        log.debug("REST request to save Colis : {}", colis);
        if (colis.getId() != null) {
            throw new BadRequestAlertException("A new colis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Colis result = colisService.save(colis);
        return ResponseEntity
            .created(new URI("/api/colis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colis/:id} : Updates an existing colis.
     *
     * @param id the id of the colis to save.
     * @param colis the colis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colis,
     * or with status {@code 400 (Bad Request)} if the colis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colis/{id}")
    public ResponseEntity<Colis> updateColis(@PathVariable(value = "id", required = false) final Long id, @RequestBody Colis colis)
        throws URISyntaxException {
        log.debug("REST request to update Colis : {}, {}", id, colis);
        if (colis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Colis result = colisService.save(colis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colis/:id} : Partial updates given fields of an existing colis, field will ignore if it is null
     *
     * @param id the id of the colis to save.
     * @param colis the colis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colis,
     * or with status {@code 400 (Bad Request)} if the colis is not valid,
     * or with status {@code 404 (Not Found)} if the colis is not found,
     * or with status {@code 500 (Internal Server Error)} if the colis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/colis/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Colis> partialUpdateColis(@PathVariable(value = "id", required = false) final Long id, @RequestBody Colis colis)
        throws URISyntaxException {
        log.debug("REST request to partial update Colis partially : {}, {}", id, colis);
        if (colis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Colis> result = colisService.partialUpdate(colis);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colis.getId().toString())
        );
    }

    /**
     * {@code GET  /colis} : get all the colis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colis in body.
     */
    @GetMapping("/colis")
    public ResponseEntity<List<Colis>> getAllColis(Pageable pageable) {
        log.debug("REST request to get a page of Colis");
        Page<Colis> page = colisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colis/:id} : get the "id" colis.
     *
     * @param id the id of the colis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colis/{id}")
    public ResponseEntity<Colis> getColis(@PathVariable Long id) {
        log.debug("REST request to get Colis : {}", id);
        Optional<Colis> colis = colisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colis);
    }

    /**
     * {@code DELETE  /colis/:id} : delete the "id" colis.
     *
     * @param id the id of the colis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colis/{id}")
    public ResponseEntity<Void> deleteColis(@PathVariable Long id) {
        log.debug("REST request to delete Colis : {}", id);
        colisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
