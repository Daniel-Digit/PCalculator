/*
 * Started Daniel and Philipp on 3/29/2017.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;

class PriceCalc{

    public static void main (String [] args) {
        Scanner in = new Scanner(System.in);
        String name;
        int stime;
        double money;
        double fee;
        int ptime = 0;
        int takeAway;
        String cmd = "";
        HashMap<String, Integer> customer = new HashMap<>();
        HashMap<String, Integer> prepaid = new HashMap<>();
        HashMap<String, Integer> pause = new HashMap<>();
        HashMap<String, Integer> pauseTotal = new HashMap<>();
        HashMap<String, Boolean> pausedMap = new HashMap<>();
        HashMap<String, Boolean> existence = new HashMap<>();
        PCalc forExistenceOnly = new PCalc();

        while (!cmd.equals("exit")) {
            cmd = in.nextLine().toLowerCase();
            System.out.println();
            if (cmd.substring(cmd.indexOf(".") + 1, cmd.length()).contains(".")) {
                System.err.println("Commands cannot contain any more than 1 '.'");
            } else {
                if (cmd.equals("new")) {
                    stime = getTime();
                    System.out.print("Name: ");
                    name = in.nextLine();
                    if (name.contains(".")) {
                        System.err.println("Name cannot contain a '.'");
                    } else if ((existence.toString().contains(name.toLowerCase()+"=true")||forExistenceOnly.search(name.toLowerCase()))&&!name.equals("")){
                            System.err.println("User '" + name + "' already exists!");
                    } else {
                        existence.put(name.toLowerCase(), true);
                        System.out.println("Regular User Successfully Registered\n");
                        name = name.toLowerCase();
                        customer.put(name, stime);
                        pause.put(name, ptime);
                        pauseTotal.put(name, 0);
                        pausedMap.put(name, false);
                    }
                } else if (cmd.contains(".end")) {
                    if ((customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null)&&!cmd.substring(0, cmd.indexOf(".")).toLowerCase().equals("")&&(prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null)&&!cmd.substring(0, cmd.indexOf(".")).toLowerCase().equals("")) {
                        System.err.print("User '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                        System.out.println();
                    } else {
                        if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                            fee = getFee(customer, pauseTotal, cmd);
                            System.out.println("Fee: " + fee + " birr");
                            customer.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), null);
                            System.out.print("Would you like to calculate change?\n Input Y/N: ");
                            String opt = in.nextLine().toLowerCase();
                            if (opt.contains("y")) {
                                calcChange(fee);
                            }
                            existence.remove(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        } else if (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                            fee = getFee(prepaid, cmd);
                            System.out.println("Fee: " + fee + " birr");
                            prepaid.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), null);
                            forExistenceOnly.remove(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                            System.out.print("Would you like to calculate change?\n Input Y/N: ");
                            String opt = in.nextLine().toLowerCase();
                            if (opt.contains("y")) {
                                calcChange(fee);
                            }
                            forExistenceOnly.remove(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        }
                    }
                } else if (cmd.contains(".pause")) {
                    if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                        if (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                            System.err.println(cmd.substring(0, cmd.indexOf(".")).toLowerCase() + " cannot be paused (Prepaid Account)");
                        } else {
                            System.err.print("User '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                            System.out.println();
                        }
                    } else if (pausedMap.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase())) {
                        System.err.println("User '" + cmd.substring(0, cmd.indexOf(".")) + "' is already paused!");

                    } else {
                        System.out.println("Customer " + cmd.substring(0, cmd.indexOf(".")) + " has been paused");
                        ptime = getTime();
                        pause.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), ptime);
                        pausedMap.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), true);
                    }
                } else if (cmd.contains(".resume")) {
                    if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                        if (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                            System.err.println(cmd.substring(0, cmd.indexOf(".")).toLowerCase() + " cannot be resumed (Prepaid Account)");
                        } else {
                            System.err.print("User '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                            System.out.println();
                        }
                    } else if (!pausedMap.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase())) {
                        System.err.println(cmd.substring(0, cmd.indexOf(".")).toLowerCase() + " is not paused!");
                    }else {
                        System.out.println("Customer " + cmd.substring(0, cmd.indexOf(".")) + " has been resumed");
                        takeAway = getTime() - pause.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        pauseTotal.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), pauseTotal.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) + takeAway);
                        pausedMap.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), false);
                    }
                } else if (cmd.contains("display")) {
                    String fullList = (Arrays.asList(customer).toString());
                    String[] fullArray = (fullList.substring(2, fullList.length() - 2).split(", "));
                    if (fullArray[0].equals("")) {
                        System.err.print("No customers found!");
                        System.out.println();
                    } else {

                            for (int i = 0; i < fullArray.length; i++) {
                                String current = fullArray[i];
                                String singlename = current.substring(0, current.indexOf("="));
                                if (!(customer.get(singlename) == null)) {
                                    int time = Integer.parseInt(current.substring(current.indexOf("=") + 1, current.length()));
                                    int hour = time / 60;
                                    int min = time % 60;
                                    System.out.println("Name: " + singlename);
                                    if (min < 10) {
                                        System.out.println("Start time: " + hour + ":0" + min);
                                    } else {
                                        System.out.println("Start time: " + hour + ":" + min);
                                    }

                                }
                                String currCmd = singlename + ".end";
                                if (customer.get(singlename) != null) {
                                    if (!(pausedMap.get(singlename))) {
                                        System.out.println("Total Pause Time: " + pauseTotal.get(singlename)+" "+"(Currently not paused)");
                                    } else {
                                        System.out.println("Total Pause Time: " + pauseTotal.get(singlename)+" "+"(Currently paused)");
                                    }
                                    System.out.println("Current fee: " + getCurrent(customer, pause, pauseTotal, currCmd, pausedMap));
                                }
                                System.out.println("");
                            }
                        }
                } else if (cmd.contains(".current")) {
                    if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                        System.err.print("User '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                        System.out.println();
                    } else {
                        int now = getTime();
                        int way = 0;
                        Boolean paused = pausedMap.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        if (paused) {
                            way = now - pause.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        }
                        pauseTotal.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), pauseTotal.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) + way);
                        ptime = now;
                        pause.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), ptime);
                        double sofar = getFee(customer, pauseTotal, cmd);
                        System.out.println("Current fee for " + cmd.substring(0, cmd.indexOf(".")) + " is " + sofar);
                        if (!(pausedMap.get(cmd.substring(0, cmd.indexOf("."))))) {
                            System.out.println("(Currently not paused)");
                        } else {
                            System.out.println("(Currently paused)");
                        }
                    }
                } else if (cmd.contains("end.all")&&cmd.contains("-")&&cmd.contains("r")) { //sample cmd: end.all-r
                    if (customer.isEmpty()) {
                        System.err.println("There are currently no regular customers!");
                    } else {
                        customer = endAll(customer, pause, pauseTotal, pausedMap);
                        existence.clear();
                    }
                } else if (cmd.contains("end.all")&&cmd.contains("-")&&cmd.contains("p")) { //sample cmd: end.all-p
                    if (prepaid.isEmpty()) {
                        System.err.println("There are currently no prepaid customers!");
                    } else {
                        prepaid = endAllPrep(prepaid);
                        forExistenceOnly.clear();
                    }
                } else if (cmd.equals("end.all")) {

                    if (customer.isEmpty()) {
                        prepaid = endAllPrep(prepaid);
                        forExistenceOnly.clear();
                    } else if (prepaid.isEmpty()) {
                        customer = endAll(customer, pause, pauseTotal, pausedMap);
                        existence.clear();
                    } else {
                        customer = endAll(customer, pause, pauseTotal, pausedMap);
                        existence.clear();
                        prepaid = endAllPrep(prepaid);
                        forExistenceOnly.clear();

                    }

                } else if (cmd.contains("prepaid")) {
                    stime = getTime();
                    System.out.print("Name: ");
                    name = in.nextLine();
                    if ((existence.toString().contains(name.toLowerCase()+"=true")||forExistenceOnly.search(name.toLowerCase()))&&!name.equals("")){
                        System.err.println("User '" + name + "' already exists!");
                    } else {
                        name = name.toLowerCase();
                        forExistenceOnly.add(name);
                        prepaid.put(name, stime);
                        System.out.print("Money Available: ");
                        while (!in.hasNextDouble()) {
                            System.out.print("\nMoney Available: ");
                            System.err.println("Invalid Input!");
                            in.next();
                        }
                        money = in.nextDouble();
                        System.out.println("Prepaid User Successfully Registered");
                        PCalc prethread = new PCalc(name, money);
                        prethread.start();
                    }
                } else if (cmd.equals("help")) {
                    System.out.println("------------------------------------------------------");
                    System.out.println("                        HELP                          ");
                    System.out.println("------------------------------------------------------");
                    System.out.println("-- new: Create a new regular user.");
                    System.out.println("-- 'User'.end: End a user's session and display fee.");
                    System.out.println("-- 'User'.pause: Pause a user's session.");
                    System.out.println("-- 'User'.resume: Resume the session of a paused user.");
                    System.out.println("-- 'User'.current: Display the current fee of a user");
                    System.out.println("-- display: Display current customers with start time, total pause time and current fee.");
                    System.out.println("-- end.all-r: End the session of all regular users and display fee for each.");
                    System.out.println("-- end.all-p: End the session of all prepaid users and display fee for each.");
                    System.out.println("-- end.all: End the session of all prepaid and regular users and display fee for each.");
                    System.out.println("-- prepaid: create a new prepaid user.");
                    System.out.println("-- change: calculate change based off inputted fee and cash given");
                    System.out.println("------------------------------------------------------");
                } else if(cmd.equals("change")) {
                    System.out.print("Input user's fee: ");
                    double manFee = in.nextDouble();
                    calcChange(manFee);
                } else {
                    if (!cmd.equals("")&&!cmd.equals("exit")) {
                        System.err.println("\"" + cmd + "\"" + " is not a valid command!\nType 'Help' for a list of commands.");
                    }
                }

            }
        }
    }
    
    private static double getCurrent(HashMap<String, Integer> customer, HashMap<String, Integer> pause, HashMap<String, Integer> pauseTotal, String cmd,HashMap<String, Boolean> pausedMap) {
        double sofar= 0.0;
        if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
            int now = getTime();
            int way = 0;
            Boolean paused = pausedMap.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
            if (paused) {
                way = now - pause.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
            }
            pauseTotal.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), pauseTotal.get(cmd.substring(0, cmd.indexOf(".")))+way);
            int ptime = now;
            pause.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), ptime);
            sofar = getFee(customer, pauseTotal, cmd);
        }

        return sofar;
    }
    private static double getCurrent(HashMap<String, Integer> prepaid, String cmd) {
        double sofar= 0.0;
        if (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
            sofar = getFee(prepaid, cmd);
        }

        return sofar;
    }
    public static int getTime(){
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
    private static double getFee(HashMap<String, Integer> customer, HashMap<String, Integer> pausedTotal, String cmd) {
        int removeTime = pausedTotal.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
        int etime = getTime();
        int duration = etime - customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase())-removeTime;
        double fee = 0.0;
        fee = fee + duration * 0.25;
        return fee;
    }
    private static double getFee(HashMap<String, Integer> customer, String cmd) {
        int etime = getTime();
        int duration = etime - customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
        double fee = 0.0;
        fee = fee + duration * 0.25;
        return fee;
    }

    private static void calcChange(double fee) {
        Scanner in = new Scanner(System.in);
        System.out.print("Cash Given: ");
        double given = in.nextDouble();
        double change = given-fee;
        if (change>=0) {
            System.out.println("Change due= " + change);
        } else {
            System.out.println("Additional money needed = " + (-1)*change);
        }
    }

    private static HashMap endAll(HashMap<String, Integer> customer, HashMap<String, Integer> pause, HashMap<String, Integer> pauseTotal, HashMap<String, Boolean> pausedMap) {
        String fullList = (Arrays.asList(customer).toString());
        String[] fullArray = (fullList.substring(2, fullList.length() - 2).split(", "));
        HashMap<String, Integer> newMap = new HashMap<>();
        System.out.println ("Regular Users: \n");
        for (int i = 0; i < fullArray.length; i++) {
            String current = fullArray[i];
            String singlename = current.substring(0, current.indexOf("="));
            if (!(customer.get(singlename) == null)) {
                int time = Integer.parseInt(current.substring(current.indexOf("=") + 1, current.length()));
                int hour = time / 60;
                int min = time % 60;
                System.out.println("Name: " + singlename);
                if (min < 10) {
                    System.out.println("Start time: " + hour + ":0" + min);
                } else {
                    System.out.println("Start time: " + hour + ":" + min);
                }
                String currCmd = singlename + ".end";
                System.out.println("Total Pause Time: " + pauseTotal.get(singlename));
                System.out.println("Total fee: " + getCurrent(customer, pause, pauseTotal, currCmd, pausedMap));
                System.out.println("");
            } else {
                System.out.println();
            }
        }


        return newMap;
    }

    private static HashMap endAllPrep(HashMap<String, Integer> prepaid) {
        String fullList = (Arrays.asList(prepaid).toString());
        String[] fullArray = (fullList.substring(2, fullList.length() - 2).split(", "));
        HashMap<String, Integer> newMap = new HashMap<>();
        System.out.println ("Prepaid Users: \n");
        for (int i = 0; i < fullArray.length; i++) {
            String current = fullArray[i];
            String singlename = current.substring(0, current.indexOf("="));
            if (!(prepaid.get(singlename) == null)) {
                int time = Integer.parseInt(current.substring(current.indexOf("=") + 1, current.length()));
                int hour = time / 60;
                int min = time % 60;
                System.out.println("Name: " + singlename);
                if (min < 10) {
                    System.out.println("Start time: " + hour + ":0" + min);
                } else {
                    System.out.println("Start time: " + hour + ":" + min);
                }
                String currCmd = singlename + ".end";
                System.out.println("Total fee: " + getCurrent(prepaid,currCmd));
                System.out.println("");
                //stop it from Thread
            } else {
                System.out.println();
            }
        }


        return newMap;
    }




}
class PCalc extends Thread {
    private double money;
    private String name;
    public static HashMap<String, Boolean> existenceP = new HashMap<>();
    public PCalc (String name, double money) {
        this.name = name;
        this.money = money;
        this.existenceP.put("", false);
    }
    public PCalc() {
        this.name = "d";
        this.money = 0.0;
        this.existenceP.put("", false);
    }
    public void run () {
        int timeLeft = (int) (money*4);
        int seconds = (timeLeft*60);
        for (int i = seconds; i>0; i--) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {}
        }
        if (!(existenceP.size()<1)&&existenceP.get(name)!=null) {
            System.out.println("\nCustomer " + name + " has finished their service at " + getTime());
            remove(name);
        }

    }
    public String getTime(){
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date currdate = new Date();
        return format.format(currdate);
    } public boolean search (String name) {
        return existenceP.toString().contains(name+"=true");
    } public void print() {
        System.out.println (existenceP.toString());
    }  public void add (String name) {
        existenceP.put(name, true);
    } public void remove (String name) {
        existenceP.remove(name);
    }
    public void clear () {
        existenceP.clear();
    }

}
