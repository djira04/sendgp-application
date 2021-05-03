package com.sengpgroup.sengp.repository;

import com.sengpgroup.sengp.domain.Voyage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Voyage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {}
