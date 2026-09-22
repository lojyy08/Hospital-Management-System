import java.util.ArrayList;
import java.util.Scanner;

public class Doctor extends User {

    private String department;
    private String specialization;
    private String phoneNumber;
    private ArrayList<Patient>     assignedPatients;
    private ArrayList<Appointment> appointments;

   public Doctor(String department, String specialization, String id, String name, 
                  String username, String password, String phoneNumber) {
        
        super(id, name, username, password);
        
        this.department = department;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        
        this.assignedPatients = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

   
    @Override
    public void viewProfile() {
        System.out.println("=== Doctor Profile ===");
        System.out.println("ID            : " + getId());
        System.out.println("Name          : " + getName());
        System.out.println("Specialization: " + specialization);
        System.out.println("Department    : " + department);
        System.out.println("Phone         : " + phoneNumber);
        System.out.println("Username      : " + getUsername());
    }

    public void viewAssignedPatients() {
        if (assignedPatients.isEmpty()) {
            System.out.println("No patients assigned yet.");
        } else {
            System.out.println("=== Assigned Patients ===");
            for (Patient p : assignedPatients) {
                System.out.println(p);
            }
        }
    }

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            System.out.println("=== Your Appointments ===");
            for (Appointment app : appointments) {
                System.out.println(app);
            }
        }
    }

    public void updateAppointmentStatus(String appointmentID, String newStatus) {
        if (!newStatus.equalsIgnoreCase("Confirmed")
                && !newStatus.equalsIgnoreCase("Completed")
                && !newStatus.equalsIgnoreCase("Cancelled")) {
            System.out.println("Invalid status. Use: Confirmed, Completed, or Cancelled.");
            return;
        }
        for (Appointment app : appointments) {
            if (app.getAppointmentID().equals(appointmentID)) {
                boolean updated = app.setStatus(newStatus);
                if (updated) System.out.println("Status updated to: " + newStatus);
                return;
            }
        }
        System.out.println("Appointment ID not found in your list.");
    }

    public void showMenu(Scanner in) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Patients");
            System.out.println("3. View My Appointments");
            System.out.println("4. Update Appointment Status");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    viewAssignedPatients();
                    break;
                case "3":
                    viewAppointments();
                    break;
                case "4":
                    System.out.print("Enter Appointment ID: ");
                    String appID = in.nextLine().trim();
                    System.out.print("Enter new status (Confirmed / Completed / Cancelled): ");
                    String status = in.nextLine().trim();
                    updateAppointmentStatus(appID, status);
                    break;
                case "5":
                    System.out.println("Logged out.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    public void addPatient(Patient p)         { assignedPatients.add(p); }
    public void addAppointment(Appointment a) { appointments.add(a); }

    public String getDepartment()      { return department; }
    public String getSpecialization()  { return specialization; }
    public String getPhoneNumber()     { return phoneNumber; }
    public ArrayList<Appointment> getAppointments() { return appointments; }

    public String toCSV() {
        return getId() + "," + getName() + "," + specialization + ","
                + department + "," + getUsername() + "," + getPassword() + ","
                + (phoneNumber != null ? phoneNumber : "");
    }

    @Override
    public String toString() {
        return "Doctor { ID=" + getId() + ", Name=" + getName()
                + ", Specialization=" + specialization
                + ", Department=" + department + " }";
    }
}
