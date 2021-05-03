package com.sengpgroup.sengp.repository;

import com.sengpgroup.sengp.domain.Voyageur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Voyageur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoyageurRepository extends JpaRepository<Voyageur, Long> {}
