import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {

    private Scanner              in;
    private ArrayList<Doctor>      doctors;
    private ArrayList<Patient>     patients;

 

    public Admin(String id, String name, String username, String password) {
        super(id, name, username, password);
        this.doctors  = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.in       = new Scanner(System.in);
    }

    @Override
    public void viewProfile() {
        System.out.println("=== Admin Profile ===");
        System.out.println("ID      : " + getId());
        System.out.println("Name    : " + getName());
        System.out.println("Username: " + getUsername());
    }

 
    public void addDoctor() {
        System.out.print("Doctor ID       : "); 
        String id    = in.nextLine().trim();
        System.out.print("Name            : ");
        String name  = in.nextLine().trim();
        System.out.print("Department      : ");
        String dept  = in.nextLine().trim();
        System.out.print("Specialization  : "); 
        String spec  = in.nextLine().trim();
        System.out.print("Username        : "); 
        String uname = in.nextLine().trim();
        System.out.print("Phone number    : ");
        String phone = in.nextLine().trim();
        System.out.print("Password        : ");
        String pass  = in.nextLine().trim();

       
        for (Doctor d : doctors) {
            if (d.getId().equals(id)) {
                System.out.println("Error: A doctor with this ID already exists.");
                return;
            }
        }

        Doctor d = new Doctor(dept, spec, id, name, uname, pass, phone);
        doctors.add(d);

        FileManager fm = new FileManager();
        fm.writeToFile("doctors.txt", d.toCSV(), true);
        fm.writeToFile("users.txt", uname + "," + pass + ",Doctor", true);
        System.out.println("Doctor added successfully.");
    }

 
    public void registerPatient() {
        System.out.print("Patient ID  : "); 
        String id    = in.nextLine().trim();
        System.out.print("Name        : "); 
        String name  = in.nextLine().trim();
        System.out.print("Age         : ");
        int age = 0;
        try { age = Integer.parseInt(in.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid age."); return; }
        System.out.print("Gender      : "); 
        String gender = in.nextLine().trim();
        System.out.print("Username    : "); 
        String uname  = in.nextLine().trim();
        System.out.print("Phone number: ");
        String phone  = in.nextLine().trim();
        System.out.print("Password    : "); 
        String pass   = in.nextLine().trim();

     
        for (Patient p : patients) {
            if (p.getId().equals(id)) {
                System.out.println("Error: A patient with this ID already exists.");
                return;
            }
        }

        Patient p = new Patient(age, gender, id, name, uname, pass, phone);
        patients.add(p);

        FileManager fm = new FileManager();
        fm.writeToFile("patients.txt", p.toCSV(), true);
        fm.writeToFile("users.txt", uname + "," + pass + ",Patient", true);
        System.out.println("Patient registered successfully.");
    }

 
    public Patient searchPatientByID(String id) {
        for (Patient p : patients) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public Doctor searchDoctorByID(String id) {
        for (Doctor d : doctors) {
            if (d.getId().equals(id)) return d;
        }
        return null;
    }

   
    public void assignPatientToDoctor() {
        System.out.print("Patient ID: ");
        String pID = in.nextLine().trim();
        System.out.print("Doctor  ID: ");
        String dID = in.nextLine().trim();

        Patient p = searchPatientByID(pID);
        Doctor  d = searchDoctorByID(dID);

        if (p == null) { System.out.println("Patient not found."); return; }
        if (d == null) { System.out.println("Doctor not found.");  return; }

        d.addPatient(p);
        p.addDoctor(d);
        rewritePatientsFile();
        System.out.println("Patient assigned to doctor successfully.");
    }
    
    public void createAppointment() {
        System.out.print("Appointment ID: "); 
        String appID = in.nextLine().trim();
        System.out.print("Patient ID    : "); 
        String pID   = in.nextLine().trim();
        System.out.print("Doctor ID     : "); 
        String dID   = in.nextLine().trim();
        System.out.print("Date (YYYY-MM-DD): "); 
        String date = in.nextLine().trim();
        System.out.print("Time (HH:MM)  : "); 
        String time  = in.nextLine().trim();

        Patient p = searchPatientByID(pID);
        Doctor  d = searchDoctorByID(dID);

        if (p == null) { System.out.println("Patient not found."); return; }
        if (d == null) { System.out.println("Doctor not found.");  return; }

        Appointment.createAppointment(appID, p, dID, date, time);

        for (Appointment app : Appointment.allAppointments) {
            if (app.getAppointmentID().equals(appID)) {
                if (!p.getAssignedDoctors().isEmpty()) {
                }
                p.addAppointment(app);
                d.addAppointment(app);
                break;
            }
        }
    }

    public void viewAllAppointments() {
        System.out.println("=== All Appointments ===");
        if (Appointment.allAppointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment app : Appointment.allAppointments) {
                System.out.println(app);
            }
        }
    }

    public void viewAllDoctors() {
        System.out.println("=== All Doctors ===");
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
        } else {
            for (Doctor d : doctors) System.out.println(d);
        }
    }

    public void viewAllPatients() {
        System.out.println("=== All Patients ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
        } else {
            for (Patient p : patients) System.out.println(p);
        }
    }

 
    public void generateReports() {
        System.out.println("\n========== Hospital Report ==========");
        System.out.println("Total Doctors  : " + doctors.size());
        System.out.println("Total Patients : " + patients.size());

        int confirmed = 0, completed = 0, cancelled = 0;
        for (Appointment app : Appointment.allAppointments) {
            switch (app.getStatus().toLowerCase()) {
                case "confirmed":  confirmed++;  break;
                case "completed":  completed++;  break;
                case "cancelled":  cancelled++;  break;
            }
        }
        System.out.println("\nTotal Appointments : " + Appointment.allAppointments.size());
        System.out.println("  Confirmed  : " + confirmed);
        System.out.println("  Completed  : " + completed);
        System.out.println("  Cancelled  : " + cancelled);

     
        System.out.println("\n--- Top 3 Doctors by Appointments ---");
        ArrayList<Doctor> sorted = new ArrayList<>(doctors);
   
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() - 1 - i; j++) {
                if (sorted.get(j).getAppointments().size()
                        < sorted.get(j + 1).getAppointments().size()) {
                    Doctor temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        int limit = Math.min(3, sorted.size());
        for (int i = 0; i < limit; i++) {
            Doctor d = sorted.get(i);
            System.out.println((i + 1) + ". " + d.getName()
                    + " — " + d.getAppointments().size() + " appointment");
        }
        System.out.println("=====================================");
    }

 
    public void saveData() {
        FileManager fm = new FileManager();

        boolean firstDoc = true;
        for (Doctor d : doctors) {
            fm.writeToFile("doctors.txt", d.toCSV(), !firstDoc);
            firstDoc = false;
        }

        boolean firstPat = true;
        for (Patient p : patients) {
            fm.writeToFile("patients.txt", p.toCSV(), !firstPat);
            firstPat = false;
        }

        Appointment.rewriteAppointmentsFile();
        System.out.println("All data saved successfully.");
    }

    public void loadData() {
        FileManager fm = new FileManager();

        for (String[] row : fm.getParsedData("doctors.txt")) {
            if (row.length < 6) continue;
            String phone = row.length >= 7 ? row[6] : "";
            Doctor d = new Doctor(row[3], row[2], row[0], row[1], row[4], row[5], phone);
            doctors.add(d);
        }

   
        for (String[] row : fm.getParsedData("patients.txt")) {
            if (row.length < 6) continue;
            int age = 0;
            try { age = Integer.parseInt(row[2]); } catch (NumberFormatException ignored) {}
            String phone = row.length >= 7 ? row[6] : "";
            Patient p = new Patient(age, row[3], row[0], row[1], row[4], row[5], phone);
            patients.add(p);
        }

    
        for (String[] row : fm.getParsedData("appointments.txt")) {
            if (row.length < 6) continue;
            Appointment app = new Appointment(row[0], row[1], row[2], row[3], row[4], row[5]);
            Appointment.allAppointments.add(app);

          
            for (Doctor d : doctors) {
                if (d.getId().equals(row[2])) { d.addAppointment(app); break; }
            }
            for (Patient p : patients) {
                if (p.getId().equals(row[1])) { p.addAppointment(app); break; }
            }
        }

       
        for (Appointment app : Appointment.allAppointments) {
            Doctor matchedDoctor  = null;
            Patient matchedPatient = null;

            for (Doctor d : doctors) {
                if (d.getId().equals(app.getDoctorID())) { matchedDoctor = d; break; }
            }
            for (Patient p : patients) {
                if (p.getId().equals(app.getPatientID())) { matchedPatient = p; break; }
            }

            if (matchedDoctor != null && matchedPatient != null) {
          
                if (!matchedPatient.isAssignedTo(matchedDoctor.getId())) {
                    matchedDoctor.addPatient(matchedPatient);
                    matchedPatient.addDoctor(matchedDoctor);
                }
            }
        }

        System.out.println("Data loaded successfully. Doctors: " + doctors.size()
                + ", Patients: " + patients.size()
                + ", Appointments: " + Appointment.allAppointments.size());
    }

   
    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n========== Admin Menu ==========");
            System.out.println(" 1. Add Doctor");
            System.out.println(" 2. Register Patient");
            System.out.println(" 3. Assign Patient to Doctor");
            System.out.println(" 4. Create Appointment");
            System.out.println(" 5. View All Doctors");
            System.out.println(" 6. View All Patients");
            System.out.println(" 7. View All Appointments");
            System.out.println(" 8. Search Patient by ID");
            System.out.println(" 9. Search Doctor by ID");
            System.out.println("10. Generate Reports");
            System.out.println("11. Save Data");
            System.out.println("12. Logout");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1":  addDoctor();            break;
                case "2":  registerPatient();      break;
                case "3":  assignPatientToDoctor(); break;
                case "4":  createAppointment();    break;
                case "5":  viewAllDoctors();       break;
                case "6":  viewAllPatients();      break;
                case "7":  viewAllAppointments();  break;
                case "8":
                    System.out.print("Patient ID: ");
                    Patient p = searchPatientByID(in.nextLine().trim());
                    System.out.println(p != null ? p : "Patient not found.");
                    break;
                case "9":
                    System.out.print("Doctor ID: ");
                    Doctor d = searchDoctorByID(in.nextLine().trim());
                    System.out.println(d != null ? d : "Doctor not found.");
                    break;
                case "10": generateReports();  break;
                case "11": saveData();         break;
                case "12":
                    System.out.println("Logged out.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

 
    public ArrayList<Doctor>  getDoctors()  { return doctors; }
    public ArrayList<Patient> getPatients() { return patients; }

    private void rewritePatientsFile() {
        FileManager fm = new FileManager();
        boolean first = true;
        for (Patient p : patients) {
            fm.writeToFile("patients.txt", p.toCSV(), !first);
            first = false;
        }
    }
}
