import com.smile.common.SmileConstants;
import com.smile.controllers.PatientController;
import com.smile.io.FileAccess;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;

/**
 * SampleClient class used to peform both tasks
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
public final class SampleClient {

    public static void main(String[] theArgs) {

        SampleClient sampleClient = new SampleClient();
        //sampleClient().basicTask();
        sampleClient.intermediateTask(true);
        sampleClient.intermediateTask(true);
        sampleClient.intermediateTask(false);
    }

    /*
     * Solution for the basic task
     *
     * @return List<Patient> List of patients
     */
    protected List<Patient> basicTask() {
        List<Patient> patients = new PatientController().searchPatientByLastName("SMITH", true);
        patients.forEach(patient -> {
            String dob = patient.hasBirthDate() ? SmileConstants.DATE_FORMAT.format(patient.getBirthDate()) : "";
            patient.getName().forEach(p -> System.out.println(p.getUse() + " " + p.getGiven() + " " + p.getFamily() + " " + dob));
        });
        return patients;
    }

    /*
     * Solution for the intermediate Task, prints out the average times to console.
     *
     * @param useCache used to toggle cacheControlDirective
     */
    protected void intermediateTask(boolean useCache) {
        new FileAccess().getNamesFromFile("list-of-names.txt")
                        .forEach(patient -> new PatientController().searchPatientByLastName(patient, useCache));
    }
}
