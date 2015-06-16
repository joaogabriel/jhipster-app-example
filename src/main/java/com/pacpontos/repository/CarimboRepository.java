package com.pacpontos.repository;

import com.pacpontos.domain.Carimbo;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Carimbo entity.
 */
public interface CarimboRepository extends JpaRepository<Carimbo,Long> {

}
