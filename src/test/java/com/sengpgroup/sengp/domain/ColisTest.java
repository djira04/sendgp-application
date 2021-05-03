package com.sengpgroup.sengp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sengpgroup.sengp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ColisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colis.class);
        Colis colis1 = new Colis();
        colis1.setId(1L);
        Colis colis2 = new Colis();
        colis2.setId(colis1.getId());
        assertThat(colis1).isEqualTo(colis2);
        colis2.setId(2L);
        assertThat(colis1).isNotEqualTo(colis2);
        colis1.setId(null);
        assertThat(colis1).isNotEqualTo(colis2);
    }
}
