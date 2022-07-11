package com.smile.controllers;

import com.smile.common.SmileConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PatientControllerTest
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
public class PatientControllerTest {

    final PatientController patientController = new PatientController();

    @Test
    public void searchPatientByLastName_returnsNonZeroList() {
        assertTrue(patientController.searchPatientByLastName("SMITH", true).size() > 0);
    }

    @Test
    public void searchPatientByLastNameWithoutCache_returnsSuccessfully() {
        assertTrue(patientController.searchPatientByLastName("SMITH", false).size() > 0);
    }

    @Test
    public void searchPatientByLastName_findsLastName() {
        assertTrue(patientController.searchPatientByLastName("SMITH", true)
                                    .stream()
                                    .anyMatch(patient -> patient.getName()
                                                                .stream()
                                                                .anyMatch(humanName -> humanName.getFamily().equalsIgnoreCase("SMITH"))));
    }

    @Test
    public void searchPatientByLastName_whenLastNameEmpty_returnZero() {
        assertEquals(0, patientController.searchPatientByLastName("", true).size());
    }

    @Test
    public void searchPatientByLastName_whenLastNameNull_returnZero() {
        assertEquals(0, patientController.searchPatientByLastName(null, true).size());
    }

    @Test
    public void searchPatientByLastName_whenLastNameInvalid_returnException() {
        assertThrows(IllegalArgumentException.class, () -> patientController.searchPatientByLastName("87", true)).getMessage()
                                                                                                                 .contains(
                                                                                                                         SmileConstants.invalidName);
    }
}