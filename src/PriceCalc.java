/*
 * By Daniel on 3/29/2017.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class PriceCalc {

    public static void main (String [] args) {
        Scanner in = new Scanner(System.in);
        String name;
        int stime;
        int etime;
        int duration;
        double fee;
        int ptime = 0;
        int takeAway;
        String cmd = "";
        HashMap<String, Integer> customer = new HashMap<>();
        HashMap<String, Integer> pause = new HashMap<>();
        HashMap<String, Integer> pauseTotal = new HashMap<>();
        HashMap<String, Boolean> pausedMap = new HashMap<>();
        while (!cmd.equals("exit")) {
            System.out.print("~");
            cmd = in.nextLine();
            if (cmd.equals("new")) {
                stime = getTime();
                System.out.print("Name: ");
                name = in.nextLine();
                name = name.toLowerCase();
                customer.put(name, stime);
                pause.put(name, ptime);
                pauseTotal.put(name, 0);
                pausedMap.put(name, false);
            } else if (cmd.contains(".end")) {
                if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                    System.err.print("User '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                    System.out.println();

                } else {
                    fee = getFee(customer, pauseTotal, cmd);
                    System.out.println("Fee: " + fee + " birr");
                    customer.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), null);
                }
            } else if (cmd.contains(".pause")) {
                ptime = getTime();
                pause.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), ptime);
                pausedMap.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), true);
            } else if (cmd.contains(".resume")) {
                takeAway = getTime() - pause.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                pauseTotal.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), pauseTotal.get(cmd.substring(0, cmd.indexOf("."))) + takeAway);
                pausedMap.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), false);
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
                            String currCmd = singlename + ".end";
                            System.out.println("Total Pause Time: " + pauseTotal.get(singlename));
                            System.out.println("Current fee: " + getCurrent(customer, pause, pauseTotal, currCmd, pausedMap));
                            System.out.println("");
                        } else {
                            /*System.out.println();*/
                        }

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
                }
            } else if (cmd.contains(".all")) {
                customer = endAll(customer, pause, pauseTotal, pausedMap);
            }
        }
    }
    
    private static double getCurrent(HashMap<String, Integer> customer, HashMap<String, Integer> pause, HashMap<String, Integer> pauseTotal, String cmd,HashMap<String, Boolean> pausedMap) {
        double sofar= 0.0;
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
            pauseTotal.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), pauseTotal.get(cmd.substring(0, cmd.indexOf(".")))+way);
            int ptime = now;
            pause.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), ptime);
            sofar = getFee(customer, pauseTotal, cmd);

        }

        return sofar;
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
    private static double getFee(HashMap<String, Integer> customer, HashMap<String, Integer> pausedTotal, String cmd) {
        int removeTime = pausedTotal.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
        int etime = getTime();
        int duration = etime - customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase())-removeTime;
        double fee = 0.0;
        fee = fee + duration * 0.25;
        return fee;
    }


    private static HashMap endAll(HashMap<String, Integer> customer, HashMap<String, Integer> pause, HashMap<String, Integer> pauseTotal, HashMap<String, Boolean> pausedMap) {
        String fullList = (Arrays.asList(customer).toString());
        String[] fullArray = (fullList.substring(2, fullList.length() - 2).split(", "));
        HashMap<String, Integer> newMap = new HashMap<>();

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
                System.err.print("No customers found!");
                System.out.println();
            }
        }

        return newMap;
    }


}
