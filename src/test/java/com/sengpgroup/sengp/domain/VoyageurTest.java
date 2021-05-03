package com.sengpgroup.sengp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sengpgroup.sengp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoyageurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voyageur.class);
        Voyageur voyageur1 = new Voyageur();
        voyageur1.setId(1L);
        Voyageur voyageur2 = new Voyageur();
        voyageur2.setId(voyageur1.getId());
        assertThat(voyageur1).isEqualTo(voyageur2);
        voyageur2.setId(2L);
        assertThat(voyageur1).isNotEqualTo(voyageur2);
        voyageur1.setId(null);
        assertThat(voyageur1).isNotEqualTo(voyageur2);
    }
}
