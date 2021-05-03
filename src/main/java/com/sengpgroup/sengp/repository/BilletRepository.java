package com.sengpgroup.sengp.repository;

import com.sengpgroup.sengp.domain.Billet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Billet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {}
