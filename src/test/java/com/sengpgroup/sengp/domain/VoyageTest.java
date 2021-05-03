package com.sengpgroup.sengp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sengpgroup.sengp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoyageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voyage.class);
        Voyage voyage1 = new Voyage();
        voyage1.setId(1L);
        Voyage voyage2 = new Voyage();
        voyage2.setId(voyage1.getId());
        assertThat(voyage1).isEqualTo(voyage2);
        voyage2.setId(2L);
        assertThat(voyage1).isNotEqualTo(voyage2);
        voyage1.setId(null);
        assertThat(voyage1).isNotEqualTo(voyage2);
    }
}
