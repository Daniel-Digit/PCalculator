/*
  Created by student on 5/22/2017.
 */
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame{
    private static String nam;
    private static String cmd;
    private int stime;
    // --Commented out by Inspection (5/24/2017 7:29 PM):private double money;
    private double fee;
    private int ptime = 0;
    private int takeAway;
    private int index = 0;
    private HashMap<String, Integer> customer = new HashMap<>();
    private HashMap<String, Integer> prepaid = new HashMap<>();
    private final HashMap<String, Integer> pause = new HashMap<>();
    private final HashMap<String, Integer> pauseTotal = new HashMap<>();
    private final HashMap<String, Boolean> pausedMap = new HashMap<>();
    private final HashMap<String, Boolean> existence = new HashMap<>();
    private final PCalc forExistenceOnly = new PCalc();
    private final PCalc forLog = new PCalc();
    private final JTextArea log = PCalc.log;
    private int n = 0;
    private ArrayList<String> currcust = new ArrayList<>();
    public ArrayList<String> newArray = new ArrayList<>();
    private HashMap<String, Integer> customerIndex = new HashMap<>();
    private JList customers = new JList(currcust.toArray());
    private DateFormat format = new SimpleDateFormat("HHmm");
    private Date currdate = new Date();
    private String date = format.format(currdate);
    private String time = date.substring(0,2)+":"+date.substring(2,date.length());





    public static void main(String[] args){

        new GUI();

    }
    private GUI(){
        //Set The Frame
        this.setLayout(new FlowLayout());
        this.setSize(660, 530);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Price Calculator");
        this.setBackground(new Color(238,238,238));
        //
        //Set Panel
        JPanel bigPanel = new JPanel();
        JPanel logPanel = new JPanel();
            //Filler Panels
                JPanel vfillerPanel = new JPanel();
                vfillerPanel.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));

                JPanel hfillerPanel = new JPanel();
                hfillerPanel.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));

                JPanel vfillerPanel2 = new JPanel();
                vfillerPanel2.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));

                JPanel vfillerPanel3 = new JPanel();
                vfillerPanel3.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));

        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.PAGE_AXIS));
        JPanel buttonPanel = new JPanel();
        JPanel clearPanel = new JPanel();
        bigPanel.setBackground(new Color(238,238,238));
        logPanel.setBackground(new Color(238,238,238));

        this.add(vfillerPanel2);
        this.add (bigPanel);
        this.add(vfillerPanel);
        this.add (logPanel);
        this.add (vfillerPanel3);
        //
        //Create List

        DefaultListModel defListModel = new DefaultListModel();
        for (String cust: currcust) {
            if (cust !=null) {
                defListModel.addElement(cust);
            }
        }
        customers.setVisibleRowCount(5);
        JScrollPane listScroll = new JScrollPane(customers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScroll.setVisible(true);
        customers.setFixedCellWidth(200);
        customers.setFixedCellHeight(30);
        TitledBorder bdr = new TitledBorder(BorderFactory.createLineBorder(new Color(127,127,127)), "Customers");
        listScroll.setBorder(bdr);
        /*
        customers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);*/


        //
        //Create Buttons
        JButton createReg = new JButton();
        createReg.setText("Regular");
        createReg.setToolTipText("Click here to register a new regular customer");

        JButton createPre = new JButton();
        createPre.setText("Prepaid");
        createPre.setToolTipText("Click here to register a new prepaid cutomer");

        JButton clearLog = new JButton();
        clearLog.setText("Clear");
        clearLog.setToolTipText("Click here to clear log.");
        //

        //Text Fields
        JTextField name = new JTextField("Enter Name", 15);
        name.setToolTipText("Input the name of the user");
        name.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                name.setText("");
            }
            public void focusLost(FocusEvent e) {
            }
        });
        JTextField nameT = new JTextField("Name", 4);
        nameT.setBackground(new Color(238,238,238));
        nameT.setEditable(false);
        Border b = BorderFactory.createLineBorder(new Color(238,238,238));
        nameT.setBorder(b);

        JTextField money = new JTextField("Enter Money (for prepaid)", 15);
        money.setToolTipText("Input the amount of money the user has. This is applicable for prepaid customers only");
        money.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                money.setText ("");
            }
            public void focusLost(FocusEvent e) {
            }
        });

        JTextField moneyT = new JTextField("Money", 4);
        moneyT.setBackground(new Color(238,238,238));
        moneyT.setEditable(false);
        moneyT.setBorder(b);

        JTextField command = new JTextField("Enter command", 20);
        command.setToolTipText("Type \"Help\" for a list of all commands");
        command.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                command.setText("");
            }
            public void focusLost(FocusEvent e) {
                command.setText("Enter command");
            }
        });
        //
        //Spacers
        Spacer one = new Spacer (10);
        Spacer two = new Spacer (2);
        Spacer three = new Spacer (10);
        Spacer four = new Spacer (10);
        Spacer five = new Spacer (2);
        Spacer six = new Spacer (10);
        Spacer seven = new Spacer (10);
        
        //
        //Add Change Dialogue
        JDialog diag = new JDialog();
        diag.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        diag.setSize(280,120);
        diag.setResizable(false);
        diag.setLocationRelativeTo(null);

            //Add Panel and Text Field
            JPanel panel = new JPanel();
            JTextField message = new JTextField("Calculate Change?");
            message.setEditable(false);
            message.setBorder(b);
            diag.add(panel);
            panel.add(message);
            panel.add(four.space());
            //Add Buttons
            JButton yes = new JButton("Yes");
            yes.addActionListener(e ->{
                diag.dispose();
                calcChange(fee);
            });
            JButton no = new JButton("No");
            no.addActionListener(e -> {
                diag.dispose();
            });
            panel.add(yes);
            panel.add(five.space());
            panel.add(no);


        //Add components
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.PAGE_AXIS));
        //bigPanel.setLayout(new BorderLayout());
        //bigPanel.add(spacer6);
        bigPanel.add(nameT);
        bigPanel.add(name);
        bigPanel.add(one.space());
        bigPanel.add(moneyT);
        bigPanel.add(money);
        bigPanel.add(six.space());
        buttonPanel.add(createReg);
        buttonPanel.add(two.space());
        buttonPanel.add(createPre);
        bigPanel.add(buttonPanel);
        bigPanel.add(three.space());
        bigPanel.add(command);
        bigPanel.add(seven.space());
        //

        //Text Area
        JTextArea filler = new JTextArea(24, 1);
        filler.setEditable(false);
        filler.setBackground(new Color(238, 238, 238));
        vfillerPanel.add(filler);

        JTextArea filler2 = new JTextArea(24, 0);
        filler2.setEditable(false);
        filler2.setBackground(new Color(238, 238, 238));
        vfillerPanel2.add(filler2);

        JTextArea filler3 = new JTextArea(24, 0);
        filler3.setEditable(false);
        filler3.setBackground(new Color(238, 238, 238));
        vfillerPanel3.add(filler3);

        JTextArea hfiller = new JTextArea(0, 12);
        hfiller.setEditable(false);
        hfiller.setBackground(new Color(238, 238, 238));
        hfillerPanel.add(hfiller);

        log.setBackground(new Color(247, 247, 247));
        Border bordr = BorderFactory.createLineBorder(new Color(127, 127, 127));
        TitledBorder border = new TitledBorder(bordr, "Logs");
        border.setTitleColor(new Color(63, 63, 63));
        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        JScrollPane logScroll = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScroll.setBorder(border);
        logScroll.setVisible(true);
        bigPanel.add(listScroll);
        logPanel.add(hfillerPanel);
        logPanel.add(logScroll);
        clearPanel.add(clearLog, BorderLayout.CENTER);
        logPanel.add(clearPanel);
        //
        //--------------------------------------------------------------
        //Actions
        clearLog.addActionListener(e -> {
            log.setText("");
        });

        createReg.addActionListener(e -> {
            log.setText("");
            nam = name.getText();
            if (nam.contains(".")) {
                PCalc.log.append("\nName cannot contain a '.'");
            } else if ((existencesearch() || forExistenceOnly.search(nam.toLowerCase()) && !nam.equals(""))) {
                PCalc.log.append("\nUser '" + nam + "' already exists!");
            } else {
                existence.put(nam.toLowerCase(), true);
                PCalc.log.append("\nRegular User Successfully Registered\n");
                currcust.add ("("+time+") "+nam);
                nam = nam.toLowerCase();
                customerIndex.put(nam, n);
                stime = getTime();
                customer.put(nam, stime);
                pause.put(nam, ptime);
                pauseTotal.put(nam, 0);
                pausedMap.put(nam, false);
                n++;
                customers.setListData(currcust.toArray());
                customers.updateUI();
            }



        });

        command.addActionListener(e -> {
            log.setText("");
            cmd = command.getText();

            if (cmd.contains(".end")) {
                if ((customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) && !cmd.substring(0, cmd.indexOf(".")).toLowerCase().equals("") && (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) && !cmd.substring(0, cmd.indexOf(".")).toLowerCase().equals("")) {
                    PCalc.log.append("\nUser '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                    System.out.println();
                } else {
                    if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                        fee = getFee(customer, pauseTotal, cmd);
                        PCalc.log.append("\nFee: " + fee + " birr");
                        customer.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), null);
                        diag.setVisible(true);
                        existence.remove(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        index = customerIndex.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        currcust.remove(index);
                        customers.setListData(currcust.toArray());
                        customers.updateUI();
                        n--;
                    } else if (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                        fee = getFee(prepaid, cmd);
                        PCalc.log.append("\nFee: " + fee + " birr");
                        prepaid.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), null);
                        forExistenceOnly.remove(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        diag.setVisible(true);
                        forExistenceOnly.remove(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        index = customerIndex.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                        currcust.remove(index);
                        customers.setListData(currcust.toArray());
                        customers.updateUI();
                        n--;
                    }
                }
            } else if (cmd.contains(".pause")) {
                if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                    if (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                        PCalc.log.append("\n"+cmd.substring(0, cmd.indexOf(".")).toLowerCase() + " cannot be paused (Prepaid Account)");
                    } else {
                        PCalc.log.append("\nUser '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                    }
                } else if (pausedMap.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase())) {
                    PCalc.log.append("\nUser '" + cmd.substring(0, cmd.indexOf(".")) + "' is already paused!");

                } else {
                    PCalc.log.append("\nCustomer " + cmd.substring(0, cmd.indexOf(".")) + " has been paused");
                    ptime = getTime();
                    pause.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), ptime);
                    pausedMap.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), true);
                    index = customerIndex.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                    currcust.set(index, currcust.get(index)+" (paused)");
                    customers.setListData(currcust.toArray());
                    customers.updateUI();
                }
            } else if (cmd.contains(".resume")) {
                if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                    if (prepaid.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) != null) {
                        PCalc.log.append("\n"+cmd.substring(0, cmd.indexOf(".")).toLowerCase() + " cannot be resumed (Prepaid Account)");
                    } else {
                        PCalc.log.append("\nUser '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
                        System.out.println();
                    }
                } else if (!pausedMap.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase())) {
                    PCalc.log.append("\n"+cmd.substring(0, cmd.indexOf(".")).toLowerCase() + " is not paused!");
                } else {
                    PCalc.log.append("\nCustomer " + cmd.substring(0, cmd.indexOf(".")) + " has been resumed");
                    takeAway = getTime() - pause.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                    pauseTotal.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), pauseTotal.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) + takeAway);
                    pausedMap.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), false);
                    index = customerIndex.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
                    currcust.set(index, currcust.get(index).substring(0, currcust.get(index).length()-9));
                    customers.setListData(currcust.toArray());
                    customers.updateUI();
                }
            } else if (cmd.contains("display")) {
                String fullList = "([])";
                if (customer.size() != 0 && prepaid.size() != 0) {
                    fullList = (Arrays.asList(customer).toString().substring(0, Arrays.asList(customer).toString().length() - 2) + ", " + Arrays.asList(prepaid).toString().substring(2, Arrays.asList(prepaid).toString().length()));
                } else if (customer.size() != 0 && prepaid.size() == 0) {
                    fullList = (Arrays.asList(customer).toString());

                } else if (customer.size() == 0 && prepaid.size() != 0) {
                    fullList = (Arrays.asList(prepaid).toString());
                }
                String[] fullArray = (fullList.substring(2, fullList.length() - 2).split(", "));
                if (fullArray[0].equals("")) {
                    PCalc.log.append("\nNo customers found!");
                } else {

                    for (String current : fullArray) {
                        String singlename = current.substring(0, current.indexOf("="));
                        if (!(customer.get(singlename) == null)) {
                            int time = Integer.parseInt(current.substring(current.indexOf("=") + 1, current.length()));
                            int hour = time / 60;
                            int min = time % 60;
                            PCalc.log.append("\n\n    Name: " + singlename);
                            PCalc.log.append("\n    Type: Regular");
                            if (min < 10) {
                                PCalc.log.append("\n    Start time: " + hour + ":0" + min);
                            } else {
                                PCalc.log.append("\n    Start time: " + hour + ":" + min);
                            }

                            String currCmd = singlename + ".end";
                            if (customer.get(singlename) != null) {
                                if (!(pausedMap.get(singlename))) {
                                    PCalc.log.append("\n    Total Pause Time: " + pauseTotal.get(singlename) + " " + "(Currently not paused)");
                                } else {
                                    PCalc.log.append("\n    Total Pause Time: " + pauseTotal.get(singlename) + " " + "(Currently paused)");
                                }
                                PCalc.log.append("\n    Current fee: " + getCurrent(customer, pause, pauseTotal, currCmd, pausedMap));
                            }
                            PCalc.log.append("");
                        } else if (!(prepaid.get(singlename) == null)) {
                            int time = Integer.parseInt(current.substring(current.indexOf("=") + 1, current.length()));
                            int hour = time / 60;
                            int min = time % 60;
                            PCalc.log.append("\n\n    Name: " + singlename);
                            PCalc.log.append("\n    Type: Prepaid");
                            if (min < 10) {
                                PCalc.log.append("\n    Start time: " + hour + ":0" + min);
                            } else {
                                PCalc.log.append("\n    Start time: " + hour + ":" + min);
                            }
                            PCalc.log.append("");
                        }
                    }
                }
            } else if (cmd.contains(".current")) {
                if (customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase()) == null) {
                    PCalc.log.append("\nUser '" + cmd.substring(0, cmd.indexOf(".")) + "' does not exist!");
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
                    PCalc.log.append("\nCurrent fee for " + cmd.substring(0, cmd.indexOf(".")) + " is " + sofar);
                    if (!(pausedMap.get(cmd.substring(0, cmd.indexOf("."))))) {
                        PCalc.log.append(" (Currently not paused)");
                    } else {
                        PCalc.log.append(" (Currently paused)");
                    }
                }
            } /*else if (cmd.contains("end.all") && cmd.contains("-") && cmd.contains("r")) { //sample cmd: end.all-r
                if (customer.isEmpty()) {
                    PCalc.log.append("\nThere are currently no regular customers!");
                } else {
                    n = 0;
                    for (String c : currcust) {
                        if (customer.get(c.substring(c.toLowerCase().indexOf(") ")+2, c.length()).toLowerCase())==null) {
                            newArray.add (c);
                            n++;
                        }
                    }
                    currcust.clear();
                    currcust = newArray;
                    customer = endAll(customer, pause, pauseTotal, pausedMap);
                    existence.clear();
                    customers.setListData(currcust.toArray());
                    customers.updateUI();
                    newArray.clear();
                }
            } else if (cmd.contains("end.all") && cmd.contains("-") && cmd.contains("p")) { //sample cmd: end.all-p
                if (prepaid.isEmpty()) {
                    PCalc.log.append("\nThere are currently no prepaid customers!");
                } else {
                    prepaid = endAllPrep(prepaid);
                    forExistenceOnly.clear();
                    for (String c : currcust) {
                        if (prepaid.get(c.toLowerCase())!=null) {
                            index = customerIndex.get(c.toLowerCase());
                            currcust.remove(index);
                            n--;
                        }
                    }
                    customers.setListData(currcust.toArray());
                    customers.updateUI();
                }
            }*/ else if (cmd.equals("end.all")) {

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
                currcust.clear();
                customers.setListData(currcust.toArray());
                customers.updateUI();
                n=0;

            } else if (cmd.toLowerCase().equals("help")) {
                log.setText("------------------------------------------------------------------------------");
                log.append("\n                                            HELP                                        ");
                log.append("\n------------------------------------------------------------------------------");
                log.append("\n-- new: Create a new regular user.\n");
                log.append("\n-- 'User'.end: End a user's session and display fee.\n");
                log.append("\n-- 'User'.pause: Pause a user's session.\n");
                log.append("\n-- 'User'.resume: Resume the session of a paused user.\n");
                log.append("\n-- 'User'.current: Display the current fee of a user\n");
                log.append("\n-- display: Display current customers with start time, total pause time and current fee.\n");
                //log.append("\n-- end.all-r: End the session of all regular users and display fee for each.");
                //log.append("\n-- end.all-p: End the session of all prepaid users and display fee for each.");
                log.append("\n-- end.all: End the session of all prepaid and regular users and display fee for each.\n");
                log.append("\n-- prepaid: create a new prepaid user.\n");
                log.append("\n-- change: calculate change based off inputted fee and cash given");
                log.append("\n------------------------------------------------------------------------------");
            } else {
                if (!cmd.equals("")&&!cmd.equals("exit")) {
                    log.setText("\"" + cmd + "\"" + " is not a valid command!\nType 'Help' for a list of commands.");
                }
            }

        });

        createPre.addActionListener(e -> {
            log.setText("");
            stime = getTime();
            nam = name.getText();
            if ((existencesearch() || forExistenceOnly.search(nam.toLowerCase()) && !nam.equals(""))) {
                PCalc.log.append ("User '" + nam + "' already exists!");
            } else {
                currcust.add ("("+time+") "+nam+", Money: "+money.getText()+" birr");
                nam = nam.toLowerCase();
                customerIndex.put(nam, n);
                forExistenceOnly.add(nam);
                prepaid.put(nam, stime);
                double mon = Double.parseDouble(money.getText());
                PCalc.log.append ("Prepaid User Successfully Registered");
                PCalc prethread = new PCalc(nam, mon);
                prethread.start();
                n++;
                customers.setListData(currcust.toArray());
                customers.updateUI();

            }
        });

        this.setVisible(true);

        //
    }

    private boolean existencesearch() {
        return existence.toString().contains("{"+nam.toLowerCase() + "=true") || existence.toString().contains(", "+nam.toLowerCase() + "=true");
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
            pause.put(cmd.substring(0, cmd.indexOf(".")).toLowerCase(), now);
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
    private static double getFee(HashMap<String, Integer> customer, String cmd) {
        int etime = getTime();
        int duration = etime - customer.get(cmd.substring(0, cmd.indexOf(".")).toLowerCase());
        double fee = 0.0;
        fee = fee + duration * 0.25;
        return fee;
    }

    private void calcChange(double fee) {
        JDialog change = new JDialog();
        change.setSize(400, 100);
        change.setLocationRelativeTo(null);
        change.setVisible(true);
        change.setTitle("Change Calculator");
        change.setResizable(false);
        change.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel cpanel = new JPanel();
        change.add(cpanel);

        JTextField cgiven = new JTextField("Cash Given", 7);
        cgiven.setEditable(false);
        cgiven.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));

        JTextField in = new JTextField(10);

        JButton calc = new JButton("Calculate");


        JTextField display = new JTextField("", 15);
        display.setEditable(false);
        display.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));

        calc.addActionListener(e -> {

                double given = Double.parseDouble(in.getText());
                double chnge = given-fee;
                if (chnge>=0) {
                    display.setText("      Change due= " + chnge+" birr");
                } else {
                    display.setText("      Additional money needed = " + (-1)*chnge+" birr");
                }
            }
        );

        JTextField spacer4 = new JTextField("", 50);
        spacer4.setEditable(false);
        spacer4.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));


        cpanel.add(cgiven);
        cpanel.add(in);
        cpanel.add(calc);
        //cpanel.add(spacer4);
        cpanel.add (display);



    }

    private HashMap endAll(HashMap<String, Integer> customer, HashMap<String, Integer> pause, HashMap<String, Integer> pauseTotal, HashMap<String, Boolean> pausedMap) {
        @SuppressWarnings("ArraysAsListWithZeroOrOneArgument") String fullList = (Arrays.asList(customer).toString());
        String[] fullArray = (fullList.substring(2, fullList.length() - 2).split(", "));
        HashMap<String, Integer> newMap = new HashMap<>();
        PCalc.log.append("\nRegular Users: ");
        for (String current : fullArray) {
            String singlename = current.substring(0, current.indexOf("="));
            if (!(customer.get(singlename) == null)) {
                int time = Integer.parseInt(current.substring(current.indexOf("=") + 1, current.length()));
                int hour = time / 60;
                int min = time % 60;
                PCalc.log.append("\n\n    Name: " + singlename);
                if (min < 10) {
                    PCalc.log.append("\n    Start time: " + hour + ":0" + min);
                } else {
                    PCalc.log.append("\n    Start time: " + hour + ":" + min);
                }
                String currCmd = singlename + ".end";
                PCalc.log.append("\n    Total Pause Time: " + pauseTotal.get(singlename));
                PCalc.log.append("\n    Total fee: " + getCurrent(customer, pause, pauseTotal, currCmd, pausedMap));
                PCalc.log.append("\n");
            } else {
                System.out.println();
            }
        }


        return newMap;
    }

    private HashMap endAllPrep(HashMap<String, Integer> prepaid) {
        @SuppressWarnings("ArraysAsListWithZeroOrOneArgument") String fullList = (Arrays.asList(prepaid).toString());
        String[] fullArray = (fullList.substring(2, fullList.length() - 2).split(", "));
        HashMap<String, Integer> newMap = new HashMap<>();
        PCalc.log.append("\n\nPrepaid Users: ");
        for (String current : fullArray) {
            String singlename = current.substring(0, current.indexOf("="));
            if (!(prepaid.get(singlename) == null)) {
                int time = Integer.parseInt(current.substring(current.indexOf("=") + 1, current.length()));
                int hour = time / 60;
                int min = time % 60;
                PCalc.log.append("\n\n    Name: " + singlename);
                if (min < 10) {
                    PCalc.log.append("\n    Start time: " + hour + ":0" + min);
                } else {
                    PCalc.log.append("\n    Start time: " + hour + ":" + min);
                }
                String currCmd = singlename + ".end";
                PCalc.log.append("\n    Total fee: " + getCurrent(prepaid, currCmd));
                PCalc.log.append("\n");
                //stop it from Thread
            } else {
                PCalc.log.append("\n");
            }
        }


        return newMap;
    }




}

class PCalc extends Thread {
    public static final JTextArea log = new JTextArea(24, 30);
    private final double money;
    private final String name;
    private static final HashMap<String, Boolean> existenceP = new HashMap<>();

    public PCalc(String name, double money) {
        this.name = name;
        this.money = money;
        existenceP.put("", false);
    }

    public PCalc() {
        this.name = "d";
        this.money = 0.0;
        existenceP.put("", false);
    }

    public void run() {
        int timeLeft = (int) (money * 4);
        int seconds = (timeLeft * 60);
        for (int i = seconds; i > 0; i--) {
            //noinspection EmptyCatchBlock
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        if (!(existenceP.size() < 1) && existenceP.get(name) != null) {
            log.append("\nCustomer " + name + " has finished their service at " + getTime());
            remove(name);
        }

    }

    private String getTime() {
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date currdate = new Date();
        return format.format(currdate);
    }

    public boolean search(String name) {
        return existenceP.toString().contains(", " + name + "=true")||existenceP.toString().contains("{" + name + "=true");
    }

    public void print() {
        log.append("\n" + existenceP.toString());
    }

    public void add(String name) {
        existenceP.put(name, true);
    }

    public void remove(String name) {
        existenceP.remove(name);
    }

    public void clear() {
        existenceP.clear();
    }
}

class Spacer {
    private JTextField spacer;

    public Spacer (int size) {
        spacer = new JTextField("", size);
        spacer.setBackground(new Color(238,238,238));
        spacer.setEditable(false);
        spacer.setBorder(BorderFactory.createLineBorder(new Color(238,238,238)));
    }
    public JTextField space () {
        return spacer;
    }
}