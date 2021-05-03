package com.sengpgroup.sengp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sengpgroup.sengp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BilletTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Billet.class);
        Billet billet1 = new Billet();
        billet1.setId(1L);
        Billet billet2 = new Billet();
        billet2.setId(billet1.getId());
        assertThat(billet1).isEqualTo(billet2);
        billet2.setId(2L);
        assertThat(billet1).isNotEqualTo(billet2);
        billet1.setId(null);
        assertThat(billet1).isNotEqualTo(billet2);
    }
}
