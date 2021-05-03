package com.sengpgroup.sengp.web.rest;

import com.sengpgroup.sengp.domain.Billet;
import com.sengpgroup.sengp.repository.BilletRepository;
import com.sengpgroup.sengp.service.BilletService;
import com.sengpgroup.sengp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.sengpgroup.sengp.domain.Billet}.
 */
@RestController
@RequestMapping("/api")
public class BilletResource {

    private final Logger log = LoggerFactory.getLogger(BilletResource.class);

    private static final String ENTITY_NAME = "billet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BilletService billetService;

    private final BilletRepository billetRepository;

    public BilletResource(BilletService billetService, BilletRepository billetRepository) {
        this.billetService = billetService;
        this.billetRepository = billetRepository;
    }

    /**
     * {@code POST  /billets} : Create a new billet.
     *
     * @param billet the billet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billet, or with status {@code 400 (Bad Request)} if the billet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billets")
    public ResponseEntity<Billet> createBillet(@RequestBody Billet billet) throws URISyntaxException {
        log.debug("REST request to save Billet : {}", billet);
        if (billet.getId() != null) {
            throw new BadRequestAlertException("A new billet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Billet result = billetService.save(billet);
        return ResponseEntity
            .created(new URI("/api/billets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billets/:id} : Updates an existing billet.
     *
     * @param id the id of the billet to save.
     * @param billet the billet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billet,
     * or with status {@code 400 (Bad Request)} if the billet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billets/{id}")
    public ResponseEntity<Billet> updateBillet(@PathVariable(value = "id", required = false) final Long id, @RequestBody Billet billet)
        throws URISyntaxException {
        log.debug("REST request to update Billet : {}, {}", id, billet);
        if (billet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Billet result = billetService.save(billet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billet.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /billets/:id} : Partial updates given fields of an existing billet, field will ignore if it is null
     *
     * @param id the id of the billet to save.
     * @param billet the billet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billet,
     * or with status {@code 400 (Bad Request)} if the billet is not valid,
     * or with status {@code 404 (Not Found)} if the billet is not found,
     * or with status {@code 500 (Internal Server Error)} if the billet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/billets/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Billet> partialUpdateBillet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Billet billet
    ) throws URISyntaxException {
        log.debug("REST request to partial update Billet partially : {}, {}", id, billet);
        if (billet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Billet> result = billetService.partialUpdate(billet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billet.getId().toString())
        );
    }

    /**
     * {@code GET  /billets} : get all the billets.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billets in body.
     */
    @GetMapping("/billets")
    public ResponseEntity<List<Billet>> getAllBillets(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("voyage-is-null".equals(filter)) {
            log.debug("REST request to get all Billets where voyage is null");
            return new ResponseEntity<>(billetService.findAllWhereVoyageIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Billets");
        Page<Billet> page = billetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /billets/:id} : get the "id" billet.
     *
     * @param id the id of the billet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billets/{id}")
    public ResponseEntity<Billet> getBillet(@PathVariable Long id) {
        log.debug("REST request to get Billet : {}", id);
        Optional<Billet> billet = billetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billet);
    }

    /**
     * {@code DELETE  /billets/:id} : delete the "id" billet.
     *
     * @param id the id of the billet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billets/{id}")
    public ResponseEntity<Void> deleteBillet(@PathVariable Long id) {
        log.debug("REST request to delete Billet : {}", id);
        billetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
