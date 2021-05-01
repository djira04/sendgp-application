package com.sengpgroup.sengp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sengpgroup.sengp");

        noClasses()
            .that()
            .resideInAnyPackage("com.sengpgroup.sengp.service..")
            .or()
            .resideInAnyPackage("com.sengpgroup.sengp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.sengpgroup.sengp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
