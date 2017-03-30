/*
 * By Daniel on 3/29/2017.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PriceCalc {

    public static void main (String [] args) {
        Scanner in = new Scanner (System.in);
        String name="";
        int stime;
        int etime;
        int duration;
        double fee;
        String cmd = "";
        HashMap<String, Integer> customer = new HashMap<>();
        while (!cmd.equals ("exit")) {
            fee = 0.0;
            System.out.print("~");
            cmd = in.nextLine();
            if (cmd.equals("new")) {
                stime = getTime();
                System.out.print("~Name: ");
                name=in.nextLine();
                name = name.toLowerCase();
                customer.put(name, stime);
            } else if (cmd.contains(".end")) {
                if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                    System.err.print("~User '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                    System.out.println();

                } else {
                    fee = getFee(customer, cmd);
                    System.out.println("~Fee: " + fee + " birr");
                    customer.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), null);
                }
            } else if (cmd.contains(".current")) {
                if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                    System.err.print("~User '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                    System.out.println();
                } else {
                    double sofar = getCurrent(customer, cmd);
                    System.out.println("~Current fee for " + cmd.substring(0, cmd.indexOf(".")) + " is " + sofar);
                }
            }
        }
    }
    private static int getTime(){
        DateFormat format = new SimpleDateFormat("HHmm");
        Date currdate = new Date();
        String date = format.format(currdate);
        int time = Integer.parseInt(date.substring(date.length()-2, date.length()));
        if (date.length()==3) {
            time = time + (Integer.parseInt(date.substring(0,1))*60);
        } else if (date.length()==4) {
            time = time + (Integer.parseInt(date.substring(0,2))*60);
        }

        return time;
    }
    public static double getFee(HashMap<String, Integer> customer, String cmd) {
        int etime = getTime();
        int duration = etime - customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
        double fee = 0.0;
        fee = fee + duration * 0.25;
        return fee;
    }
    public static double getCurrent(HashMap<String, Integer> customer, String cmd) {

        double fee = getFee(customer, cmd);
        return fee;

    }



}
