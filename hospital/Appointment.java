import java.util.ArrayList;

public class Appointment {

    private String appointmentID;
    private String patientID;
    private String doctorID;
    private String date;
    private String time;
    private String status;

    public static ArrayList<Appointment> allAppointments = new ArrayList<>();

    public Appointment() {}

    public Appointment(String appointmentID, String patientID, String doctorID,
                       String date, String time, String status) {
        this.appointmentID = appointmentID;
        this.patientID     = patientID;
        this.doctorID      = doctorID;
        this.date          = date;
        this.time          = time;
        this.status        = status;
    }

    public static void createAppointment(String id, Patient p, String dID,
                                         String newDate, String newTime) {
        if (newDate == null || newDate.isEmpty() || newTime == null || newTime.isEmpty()) {
            System.out.println("Error: Date and Time cannot be empty!");
            return;
        }
        if (!p.isAssignedTo(dID)) {
            System.out.println("Error: Patient is not assigned to this doctor!");
            return;
        }
        for (Appointment app : allAppointments) {
            if (app.getDoctorID().equals(dID)
                    && app.getDate().equals(newDate)
                    && app.getTime().equals(newTime)) {
                System.out.println("Error: Doctor already has an appointment at this date and time!");
                return;
            }
        }
        Appointment myApp = new Appointment(id, p.getId(), dID, newDate, newTime, "Confirmed");
        allAppointments.add(myApp);
        FileManager fm = new FileManager();
        fm.writeToFile("appointments.txt", myApp.toCSV(), true);
        System.out.println("Appointment booked successfully!");
    }

    public boolean setStatus(String newStatus) {
        if (this.status.equalsIgnoreCase("Cancelled")
                && newStatus.equalsIgnoreCase("Completed")) {
            System.out.println("Error: A cancelled appointment cannot be marked as completed!");
            return false;
        }
        this.status = newStatus;
        rewriteAppointmentsFile();
        return true;
    }

 
    public static void rewriteAppointmentsFile() {
        FileManager fm = new FileManager();
        boolean first = true;
        for (Appointment app : allAppointments) {
            fm.writeToFile("appointments.txt", app.toCSV(), !first);
            first = false;
        }
    }

    public String toCSV() {
        return appointmentID + "," + patientID + "," + doctorID + ","
                + date + "," + time + "," + status;
    }

    @Override
    public String toString() {
        return "ID: " + appointmentID + " | Patient: " + patientID
                + " | Doctor: " + doctorID + " | Date: " + date
                + " | Time: " + time + " | Status: " + status;
    }

    public String getAppointmentID() { return appointmentID; }
    public String getPatientID()     { return patientID; }
    public String getDoctorID()      { return doctorID; }
    public String getDate()          { return date; }
    public String getTime()          { return time; }
    public String getStatus()        { return status; }
}
