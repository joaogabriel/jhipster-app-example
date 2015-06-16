package com.pacpontos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pacpontos.domain.Carimbo;
import com.pacpontos.repository.CarimboRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Carimbo.
 */
@RestController
@RequestMapping("/api")
public class CarimboResource {

    private final Logger log = LoggerFactory.getLogger(CarimboResource.class);

    @Inject
    private CarimboRepository carimboRepository;

    /**
     * POST  /carimbos -> Create a new carimbo.
     */
    @RequestMapping(value = "/carimbos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Carimbo carimbo) throws URISyntaxException {
        log.debug("REST request to save Carimbo : {}", carimbo);
        if (carimbo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new carimbo cannot already have an ID").build();
        }
        carimboRepository.save(carimbo);
        return ResponseEntity.created(new URI("/api/carimbos/" + carimbo.getId())).build();
    }

    /**
     * PUT  /carimbos -> Updates an existing carimbo.
     */
    @RequestMapping(value = "/carimbos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Carimbo carimbo) throws URISyntaxException {
        log.debug("REST request to update Carimbo : {}", carimbo);
        if (carimbo.getId() == null) {
            return create(carimbo);
        }
        carimboRepository.save(carimbo);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /carimbos -> get all the carimbos.
     */
    @RequestMapping(value = "/carimbos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Carimbo> getAll() {
        log.debug("REST request to get all Carimbos");
        return carimboRepository.findAll();
    }

    /**
     * GET  /carimbos/:id -> get the "id" carimbo.
     */
    @RequestMapping(value = "/carimbos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carimbo> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Carimbo : {}", id);
        Carimbo carimbo = carimboRepository.findOne(id);
        if (carimbo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carimbo, HttpStatus.OK);
    }

    /**
     * DELETE  /carimbos/:id -> delete the "id" carimbo.
     */
    @RequestMapping(value = "/carimbos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Carimbo : {}", id);
        carimboRepository.delete(id);
    }
}
