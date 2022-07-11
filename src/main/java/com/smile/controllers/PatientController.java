package com.smile.controllers;

import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.api.SortOrderEnum;
import ca.uhn.fhir.rest.api.SortSpec;
import com.smile.common.SmileConstants;
import com.smile.connections.ClientSingleton;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PatientController class used to service the client side api
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
public class PatientController {

    /*
     * This method is used to search for the last name, sorted by the given name in ascending order
     *
     * @param lastName the last name of the patient to be searched
     * @return useCache set to true to use cacheControlDirective
     */
    public List<Patient> searchPatientByLastName(String lastName, boolean useCache) {
        // There are multiple types of names, official, usual, maiden etc. The search looks at all of them, which is good.
        // An option to choose between them would be useful.
        // The sort functions doesn't seem to work well, needs discussion and needs to be fixed on the service rather than the client side.
        
        if (lastName == null || lastName.equals("")) {
            return Collections.emptyList();
        }
        if (lastName.matches(SmileConstants.nameRegex)) {
            return toPatients(ClientSingleton.INSTANCE.getClient()
                                                      .search()
                                                      .forResource(SmileConstants.END_POINT)
                                                      .cacheControl(new CacheControlDirective().setNoCache(!useCache))
                                                      .where(Patient.FAMILY.matches().value(lastName.trim()))
                                                      .sort(new SortSpec("given", SortOrderEnum.ASC))
                                                      .returnBundle(Bundle.class)
                                                      .execute());
        } else {
            throw new IllegalArgumentException(SmileConstants.invalidName);
        }
    }

    /*
     * This method is used to convert a bundle to a list of patients
     *
     * @param bundle the resource to convert
     * @return List<Patient> List of patients
     */
    private List<Patient> toPatients(Bundle bundle) {
        return bundle.getEntry()
                     .stream()
                     .map(Bundle.BundleEntryComponent::getResource)
                     .map(resource -> (Patient) resource)
                     .collect(Collectors.toList());
    }

}
