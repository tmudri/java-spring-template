package de.stuttgart.tmudri.templates.java.spring.architecture;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * Architecture tests which verify following dependency rules: 1. Input adapters can access core 2.
 * Output adapters can't access core except output port interfaces 3. Input adapters use input ports
 * 4. Output adapters implement output ports 5. Core cannot access adapters (in and out) 6. Core
 * implements input ports
 */
@AnalyzeClasses(
    packages = {"de.stuttgart.tmudri.templates.java.spring"},
    importOptions = ImportOption.DoNotIncludeTests.class)
public class DepencenciesAT {

  // Rule 1
  @ArchTest
  public static final ArchRule adapters_canAccess_core =
      classes()
          .that()
          .resideInAPackage("de.stuttgart.tmudri.templates.java.spring.core..")
          .should()
          .onlyBeAccessed()
          .byClassesThat()
          .resideInAnyPackage(
              "de.stuttgart.tmudri.templates.java.spring.adapters..",
              "de.stuttgart.tmudri.templates.java.spring.core..");

  // Rule 2
  @ArchTest
  public static final ArchRule outputAdapters_canAccess_onlyOutputPortsInCore =
      noClasses()
          .that()
          .resideInAnyPackage("de.stuttgart.tmudri.templates.java.spring.adapters.output..")
          .should()
          .dependOnClassesThat(
              resideInAPackage("de.stuttgart.tmudri.templates.java.spring.core..")
                  .and(
                      resideOutsideOfPackage(
                          "de.stuttgart.tmudri.templates.java.spring.core.ports.output.."))
                  .and(
                      resideOutsideOfPackage(
                          "de.stuttgart.tmudri.templates.java.spring.core.domain..")));

  // Rule 3
  @ArchTest
  public static final ArchRule inputAdapters_use_inputPorts =
      classes()
          .that()
          .resideInAPackage("de.stuttgart.tmudri.templates.java.spring.core.ports.input..")
          .and()
          .areInterfaces()
          .should()
          .onlyBeAccessed()
          .byClassesThat()
          .resideInAPackage("de.stuttgart.tmudri.templates.java.spring.adapters.input..");

  // Rule 4
  @ArchTest
  public static final ArchRule outputAdapters_implement_outputPorts =
      classes()
          .that()
          .implement(
              resideInAPackage("de.stuttgart.tmudri.templates.java.spring.core.ports.output.."))
          .should()
          .resideInAPackage("de.stuttgart.tmudri.templates.java.spring.adapters.output..");

  public static final DescribedPredicate<JavaClass> isProjectClassesOutsideCore =
      resideInAPackage("de.stuttgart.tmudri.templates.java.spring..")
          .and(resideOutsideOfPackage("de.stuttgart.tmudri.templates.java.spring.core.."));

  // Rule 5
  @ArchTest
  public static final ArchRule coreCannot_access_outside =
      noClasses()
          .that()
          .resideInAPackage("de.stuttgart.tmudri.templates.java.spring.core..")
          .should()
          .dependOnClassesThat(isProjectClassesOutsideCore);

  // Rule 6
  @ArchTest
  public static final ArchRule core_implements_inputPorts =
      classes()
          .that()
          .implement(
              resideInAPackage("de.stuttgart.tmudri.templates.java.spring.core.ports.input.."))
          .should()
          .resideInAPackage("de.stuttgart.tmudri.templates.java.spring.core.services..");
}
