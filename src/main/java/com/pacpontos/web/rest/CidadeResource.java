package com.pacpontos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pacpontos.domain.Cidade;
import com.pacpontos.repository.CidadeRepository;
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
 * REST controller for managing Cidade.
 */
@RestController
@RequestMapping("/api")
public class CidadeResource {

    private final Logger log = LoggerFactory.getLogger(CidadeResource.class);

    @Inject
    private CidadeRepository cidadeRepository;

    /**
     * POST  /cidades -> Create a new cidade.
     */
    @RequestMapping(value = "/cidades",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Cidade cidade) throws URISyntaxException {
        log.debug("REST request to save Cidade : {}", cidade);
        if (cidade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cidade cannot already have an ID").build();
        }
        cidadeRepository.save(cidade);
        return ResponseEntity.created(new URI("/api/cidades/" + cidade.getId())).build();
    }

    /**
     * PUT  /cidades -> Updates an existing cidade.
     */
    @RequestMapping(value = "/cidades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Cidade cidade) throws URISyntaxException {
        log.debug("REST request to update Cidade : {}", cidade);
        if (cidade.getId() == null) {
            return create(cidade);
        }
        cidadeRepository.save(cidade);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /cidades -> get all the cidades.
     */
    @RequestMapping(value = "/cidades",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cidade> getAll() {
        log.debug("REST request to get all Cidades");
        return cidadeRepository.findAll();
    }

    /**
     * GET  /cidades/:id -> get the "id" cidade.
     */
    @RequestMapping(value = "/cidades/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cidade> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Cidade : {}", id);
        Cidade cidade = cidadeRepository.findOne(id);
        if (cidade == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cidade, HttpStatus.OK);
    }

    /**
     * DELETE  /cidades/:id -> delete the "id" cidade.
     */
    @RequestMapping(value = "/cidades/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Cidade : {}", id);
        cidadeRepository.delete(id);
    }
}
