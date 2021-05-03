package com.sengpgroup.sengp.repository;

import com.sengpgroup.sengp.domain.Colis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Colis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {}
