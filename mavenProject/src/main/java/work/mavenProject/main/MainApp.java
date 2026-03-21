package work.mavenProject.main;

import java.util.Scanner;
import work.mavenProject.dao.*;
import work.mavenProject.entity.*;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserDAO udao = new UserDAO();
        ExamSlotDAO sdao = new ExamSlotDAO();
        BookingDAO bdao = new BookingDAO();

        while (true) {

            System.out.println("\n--- ONLINE EXAM SLOT BOOKING ---");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Delete User");
            System.out.println("---------------------------");

            System.out.println("4. Add Slot");
            System.out.println("5. View Slots");
            System.out.println("6. Delete Slot");
            System.out.println("---------------------------");

            System.out.println("7. Book Slot");
            System.out.println("8. View Bookings");
            System.out.println("9. Delete Booking");
            System.out.println("---------------------------");

            System.out.println("10. View Available Slots");
            System.out.println("11. Smart Slot Suggestion");
            System.out.println("12. Remove Expired Slots");
            System.out.println("13. User Booking History");
            System.out.println("14. Exit");

            System.out.print("Enter choice: ");

            // INPUT VALIDATION
            if (!sc.hasNextInt()) {
                System.out.println("Enter numbers only!");
                sc.next();
                continue;
            }

            int ch = sc.nextInt();

            switch (ch) {

            case 1:
                System.out.print("User ID: ");
                int uid = sc.nextInt();
                sc.nextLine();

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();
                

                System.out.println(
                    udao.addUser(new User(uid, name, email)) > 0
                    ? "User Added!"
                    : "Failed"
                );
                break;

            case 2:
                udao.viewUsers();
                break;

            case 3:
                System.out.print("Enter User ID to delete: ");
                int duid = sc.nextInt();

                System.out.println(
                    udao.deleteUser(duid) > 0
                    ? "Deleted!"
                    : "Not Found"
                );
                break;

            case 4:
                System.out.print("Slot ID: ");
                int sid = sc.nextInt();

                System.out.print("Date (yyyy-mm-dd): ");
                String date = sc.next();

                sc.nextLine(); // clear buffer

                System.out.print("Time: ");
                String time = sc.nextLine();

                System.out.print("Location: ");
                String location = sc.nextLine();

                System.out.println(
                    sdao.addSlot(new ExamSlot(sid, date, time, "Available", location)) > 0
                    ? "Slot Added!"
                    : "Failed"
                );
                break;

            case 5:
                sdao.viewSlots();
                break;

            case 6:
                System.out.print("Enter Slot ID to delete: ");
                int dsid = sc.nextInt();

                System.out.println(
                    sdao.deleteSlot(dsid) > 0
                    ? "Deleted!"
                    : "Not Found"
                );
                break;

            case 7:
                System.out.print("Booking ID: ");
                int bid = sc.nextInt();

                System.out.print("User ID: ");
                int buid = sc.nextInt();

                System.out.print("Slot ID: ");
                int bsid = sc.nextInt();

                System.out.println(
                    bdao.addBooking(new Booking(bid, buid, bsid)) > 0
                    ? "Booked!"
                    : "Failed"
                );
                break;

            case 8:
                bdao.viewBookings();
                break;

            case 9:
                System.out.print("Enter Booking ID to delete: ");
                int dbid = sc.nextInt();

                System.out.println(
                    bdao.deleteBooking(dbid) > 0
                    ? "Deleted!"
                    : "Not Found"
                );
                break;

            case 10:
                sdao.viewAvailableSlots();
                break;

            case 11:
                sdao.suggestSlot();
                break;

            case 12:
                sdao.removeExpiredSlots();
                break;

            case 13:
                System.out.print("Enter User ID: ");
                int u = sc.nextInt();
                bdao.viewUserBookings(u);
                break;

            case 14:
                System.out.println("Thank You!");
                sc.close();
                System.exit(0);

            default:
                System.out.println("Invalid choice!");
            }
        }
    }
}