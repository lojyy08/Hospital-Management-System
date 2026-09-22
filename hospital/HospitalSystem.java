import java.util.Scanner;

public class HospitalSystem {

    private Admin   admin;
    private Scanner in;

    public HospitalSystem() {
  
        admin = new Admin("A001", "Admin", "admin", "admin123");
        in    = new Scanner(System.in);
    }

    public void start() {
        admin.loadData();
        showMainMenu();
    }

    private void showMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n====== Hospital Appointments System ======");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Doctor");
            System.out.println("3. Login as Patient");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1": loginAdmin();   break;
                case "2": loginDoctor();  break;
                case "3": loginPatient(); break;
                case "4":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

  
    private void loginAdmin() {
        System.out.print("Username: "); String u = in.nextLine().trim();
        System.out.print("Password: "); String p = in.nextLine().trim();

        if (u.equals(admin.getUsername()) && p.equals(admin.getPassword())) {
            System.out.println("Welcome, " + admin.getName() + "!");
            admin.showMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private void loginDoctor() {
        System.out.print("Username: "); String u = in.nextLine().trim();
        System.out.print("Password: "); String p = in.nextLine().trim();

        for (Doctor d : admin.getDoctors()) {
            if (d.getUsername().equals(u) && d.getPassword().equals(p)) {
                System.out.println("Welcome, Dr. " + d.getName() + "!");
                d.showMenu(in);
                return;
            }
        }
        System.out.println("Doctor not found or wrong password.");
    }

    private void loginPatient() {
        System.out.print("Username: "); String u = in.nextLine().trim();
        System.out.print("Password: "); String p = in.nextLine().trim();

        for (Patient pt : admin.getPatients()) {
            if (pt.getUsername().equals(u) && pt.getPassword().equals(p)) {
                System.out.println("Welcome, " + pt.getName() + "!");
                pt.showMenu(in);
                return;
            }
        }
        System.out.println("Patient not found or wrong password.");
    }
}
