import java.util.ArrayList;
import java.util.Scanner;

public class Patient extends User {

    private int    age;
    private String gender;
    private String phoneNumber;
    private ArrayList<Doctor>      assignedDoctors;
    private ArrayList<Appointment> appointments;

  
  

    public Patient(int age, String gender,
                   String id, String name,
                   String username, String password, String phoneNumber) {
        super(id, name, username, password);
        this.age             = age;
        this.gender          = gender;
        this.phoneNumber     = phoneNumber;
        this.appointments    = new ArrayList<>();
        this.assignedDoctors = new ArrayList<>();
    }

    
    @Override
    public void viewProfile() {
        System.out.println("=== Patient Profile ===");
        System.out.println("ID      : " + getId());
        System.out.println("Name    : " + getName());
        System.out.println("Age     : " + age);
        System.out.println("Gender  : " + gender);
        System.out.println("Phone   : " + phoneNumber);
        System.out.println("Username: " + getUsername());
    }

   
    public void viewAssignedDoctors() {
        System.out.println("=== Assigned Doctors ===");
        if (assignedDoctors.isEmpty()) {
            System.out.println("No doctor assigned yet.");
        } else {
            for (Doctor d : assignedDoctors) {
                System.out.println(d);
            }
        }
    }

    public void viewAppointments() {
        System.out.println("=== Your Appointments ===");
        if (appointments.isEmpty()) {
            System.out.println("No appointments booked.");
        } else {
            for (Appointment app : appointments) {
                System.out.println(app);
            }
        }
    }

   
    public void booking(String appointmentID, String date, String time) {
        if (assignedDoctors.isEmpty()) {
            System.out.println("Error: You are not assigned to any doctor.");
            return;
        }
        Doctor d = assignedDoctors.get(0); 
        Appointment.createAppointment(appointmentID, this, d.getId(), date, time);

       
        for (Appointment app : Appointment.allAppointments) {
            if (app.getAppointmentID().equals(appointmentID)) {
                if (!appointments.contains(app)) appointments.add(app);
                d.addAppointment(app);
                break;
            }
        }
    }

  
    public void cancelAppointment(String appointmentID) {
        boolean found = false;

        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentID().equals(appointmentID)) {
                appointments.get(i).setStatus("Cancelled"); 
                appointments.remove(i);
                Appointment.allAppointments.removeIf(
                        a -> a.getAppointmentID().equals(appointmentID));
                Appointment.rewriteAppointmentsFile();
                System.out.println("Appointment cancelled successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Error: Appointment ID not found.");
        }
    }

    public void showMenu(Scanner in) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Patient Menu ===");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Doctor");
            System.out.println("3. View My Appointments");
            System.out.println("4. Book Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    viewAssignedDoctors();
                    break;
                case "3":
                    viewAppointments();
                    break;
                case "4":
                    System.out.print("Enter Appointment ID: ");
                    String appID = in.nextLine().trim();
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = in.nextLine().trim();
                    System.out.print("Enter Time (HH:MM): ");
                    String time = in.nextLine().trim();
                    booking(appID, date, time);
                    break;
                case "5":
                    System.out.print("Enter Appointment ID to cancel: ");
                    String cancelID = in.nextLine().trim();
                    cancelAppointment(cancelID);
                    break;
                case "6":
                    System.out.println("Logged out.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

   
    public void addDoctor(Doctor d)           { assignedDoctors.add(d); }
    public void addAppointment(Appointment a) { appointments.add(a); }

    public boolean isAssignedTo(String doctorID) {
        for (Doctor d : assignedDoctors) {
            if (d.getId().equals(doctorID)) return true;
        }
        return false;
    }

    
    public int    getAge()         { return age; }
    public String getGender()      { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public ArrayList<Doctor> getAssignedDoctors() { return assignedDoctors; }

    public String toCSV() {
        return getId() + "," + getName() + "," + age + "," + gender + ","
                + getUsername() + "," + getPassword() + ","
                + (phoneNumber != null ? phoneNumber : "");
    }

    @Override
    public String toString() {
        return "Patient { ID=" + getId() + ", Name=" + getName()
                + ", Age=" + age + ", Gender=" + gender + " }";
    }
}
