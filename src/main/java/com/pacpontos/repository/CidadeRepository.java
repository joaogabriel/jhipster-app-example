package com.pacpontos.repository;

import com.pacpontos.domain.Cidade;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cidade entity.
 */
public interface CidadeRepository extends JpaRepository<Cidade,Long> {

}
