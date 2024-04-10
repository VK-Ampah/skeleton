
public class Doctor extends User{
    private String medicalLicenseNumber;
    private String specialization;
    // private int doc_id;

    public Doctor(int id, String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
        super(id, firstName, lastName, email, password, isDoctor);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
        // this.doc_id = id;
    }

    // public boolean createDoctor() {
        
    //     boolean bool = false;
        
    //     String query = "INSERT INTO doctors (user_id, medical_license_number, specialization) VALUES (?, ?, ?)";
    // }

    // Getters and setters for the new properties
    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

