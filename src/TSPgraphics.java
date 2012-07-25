///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
/*
 * @author: Michal Sztolcman s3826 subject: TSP z alg. Kruskala i Prima
 */

class TSPgraphics extends JPanel implements ComponentListener {

    public static int ROUTE_POINTS = 20;
    Dimension size;
    double factorX, factorY;
    Node[] Nodes; // zbior wylosowanych pojedynczych punktow (wezlow)
    ArrayList<String> output;
    boolean printed, connections;
    Set routes;
    char tmp_ch;
    TSP.alg last_alg;
    boolean last_tsp, rolled;

    class Node extends Point {

        char point;
        int tsp_degree;

        public Node(char c, int x, int y) {
            super(x, y);
            point = c;
            tsp_degree = 0;
        }

        public Node(int i, int x, int y) {
            this((char) ('A' + i), x, y);
        }

        public double getDistance(Node n) {
            return Math.sqrt(Math.pow(x - n.x, 2) + Math.pow(y - n.y, 2));
        }

        public String toString() {
            return "" + point;
        }
    }

    public TSPgraphics() {
        addComponentListener(this);
    }

    public void roll() {
        size = new Dimension(getWidth(), getHeight());
        factorX = factorY = 1;
        connections = false;
        printed = false;
        rollNodes();
        repaint();
    }

    public void connect(final TSP.alg alg, final char startNode) {
        if (alg != last_alg || last_tsp != TSP.TSPselected() || (alg == TSP.alg.PRIM && tmp_ch != startNode)) {
            printed = false;
        }
        last_alg = alg;
        last_tsp = TSP.TSPselected();
        if (!printed) {
            System.out.print("\n\nconnecting.");
        } else {
            return;
        }

        final Thread connecting = new Thread() {

            public void run() {
                NodePair p;
                Character A, B;
                dumped = false;
                output = new ArrayList<String>();
                output.add("\n\n" + (TSP.TSPselected() ? "tsp " : "") + ("" + alg).toLowerCase() + " - step log:");

                if (alg == TSP.alg.KRUSKAL) {                                 // <-- KRUSKAL
                    // rodzina jednoelementowych zbiorow z kazdym z wezlow
                    LinkedList divList = new LinkedList();
                    Set hs1;

                    // utworzenie uporzadkowanego zbioru segmentow NodePair rosnaco wg. dystansu
                    routes = new TreeSet(new Comparator<NodePair>() {

                        public int compare(NodePair p1, NodePair p2) {
                            //w przypadku wyniku 0 TreeSet nie dopuscil by roznych polaczen o tej samej odleglosci
                            return p1.distance - p2.distance < 0 ? -1 : 1;
                        }
                    });
                    // dodanie wszystkich par punktow NodePair wraz z odlegloscia do zbioru
                    for (int i = 0; i < Nodes.length; i++) {
                        for (int j = 0; j < i && i > 0; j++) {
                            routes.add(new NodePair(Nodes[j] + "" + Nodes[i], Nodes[j].getDistance(Nodes[i])));
                        }
                        // utworzenie zbioru z jednym elementem (wezlem)
                        hs1 = new HashSet();
                        hs1.add((char) ('A' + i));
                        divList.add(hs1); // dodanie do rodziny zbiorow
                    }

                    // operacja laczenia zbiorow w rodzinie zbiorow i oznaczanie polaczen
                    Iterator divIt, routeIt = routes.iterator();
                    Set pointSet, leftSet;
                    while (routeIt.hasNext() && divList.size() != 1) {
                        divIt = divList.iterator();
                        p = (NodePair) routeIt.next();
                        A = p.segment.charAt(0);
                        B = p.segment.charAt(1);
                        leftSet = null;
                        pointSet = null;
                        String tmp = "route(" + A + "," + B + ") --> ";
                        while (divIt.hasNext()) {
                            pointSet = (HashSet) divIt.next();

                            if (TSP.TSPselected() && (Nodes[idx(A)].tsp_degree >= 2 || Nodes[idx(B)].tsp_degree >= 2)) {
                                continue;
                            }
                            if (pointSet.contains(A) && pointSet.contains(B)) {
                                continue;
                            } else if (pointSet.contains(A) || pointSet.contains(B)) {
                                if (leftSet == null) {
                                    leftSet = pointSet;
                                } else {
                                    leftSet.addAll(pointSet);
                                    divIt.remove();
                                    p.connection = true;
                                    if (TSP.TSPselected()) {
                                        Nodes[idx(A)].tsp_degree++;
                                        Nodes[idx(B)].tsp_degree++;
                                    }
                                    break;
                                }
                            }
                        }
                        divIt = divList.iterator();
                        while (divIt.hasNext()) {
                            tmp += divIt.next();
                        }
                        output.add(tmp);
                    }
                    if (TSP.TSPselected()) {
                        String segment = "";
                        for (int i = 0; i < Nodes.length; i++) {
                            if (Nodes[i].tsp_degree == 1) {
                                segment += Nodes[i];
                            }
                            Nodes[i].tsp_degree = 0;
                        }
                        routeIt = routes.iterator();
                        while (routeIt.hasNext()) {
                            p = (NodePair) routeIt.next();
                            if (p.segment.equals(segment)) {
                                p.connection = true;
                                output.add("finally " + segment.charAt(0) + " -> " + segment.charAt(1));
                                break;
                            }
                        }
                    }
                } else if (alg == TSP.alg.PRIM) {
                    tmp_ch = startNode;             // <-- PRIM
                    routes = new LinkedHashSet(); // zbior do wydruku wybranych polaczen
                    PrimTree ptree = new PrimTree(); // uproszczone drzewo polaczen dla alg tsp

                    double[] distances = new double[Nodes.length];  // tablica odleglosci
                    Node[] parents = new Node[Nodes.length];        // tablica wierzcholkow rodzicow

                    // utworzenie mst prim uzywajac dwoch pomocniczych tablic, przy zalozeniu ze wszystkie punkty sie widza
                    Node curr = Nodes[idx(startNode)];
                    distances[idx(startNode)] = -1;

                    String tmp1 = "\nparent nodes array:\n", tmp2 = "\n-";
                    for (int i = 0; i < Nodes.length; i++) {
                        tmp1 += " " + Nodes[i];
                        tmp2 += "--";
                    }
                    output.add(tmp1 + "  take: " + curr + tmp2);

                    for (int min = 0; min != -1;) {
                        min = -1;
                        for (int i = 0; i < Nodes.length; i++) {
                            if (distances[i] == -1) {
                                continue;
                            }
                            if (distances[i] == 0 || curr.getDistance(Nodes[i]) < distances[i]) {
                                distances[i] = curr.getDistance(Nodes[i]);
                                parents[i] = curr;
                            }
                            if (curr != Nodes[i] && (min == -1 || distances[i] < distances[min])) {
                                min = i;
                            }
                        }
                        if (min != -1) {
                            tmp1 = "";
                            for (int i = 0; i < Nodes.length; i++) {
                                tmp1 += " " + (distances[i] != -1 ? parents[i] : ".");
                            }
                            output.add(tmp1 + "  next: " + parents[min] + " -> " + Nodes[min] + " (" + ((int) distances[min]) + "." + ("" + distances[min]).charAt(("" + distances[min]).indexOf('.') + 1) + ")");

                            if (TSP.TSPselected()) {
                                ptree.add(parents[min], Nodes[min]);
                            } else {
                                routes.add(new NodePair(parents[min] + "" + Nodes[min], parents[min].getDistance(Nodes[min]), true));
                            }
                            distances[min] = -1;
                            curr = Nodes[min];
                        }
                    }

                    if (TSP.TSPselected()) {
                        final ArrayList print = new ArrayList<String>();

                        // wlasna definicja stosu
                        myStack stack = new myStack() {

                            private stnode head;
                            private int count;

                            class stnode {

                                Node node;
                                stnode next;

                                public stnode(Node n) {
                                    count++;
                                    node = n;
                                    next = head;
                                    head = this;
                                }
                            };

                            public myStack push(Set<Node> hs) {
                                if (hs != null) {
                                    for (Iterator it = hs.iterator(); it.hasNext();) {
                                        new stnode((Node) it.next());
                                    }
                                }
                                return this;
                            }

                            public Node pop() {
                                count--;
                                Node n = head.node;
                                head = head.next;
                                popPrint(n);
                                return n;
                            }

                            public Node top() {
                                return head.node;
                            }

                            public boolean empty() {
                                return head == null;
                            }

                            private void popPrint(Node n) {
                                int i;
                                stnode pointer = head;
                                Node[] nod = new Node[count];
                                for (i = count - 1; pointer != null; i--) {
                                    nod[i] = pointer.node;
                                    pointer = pointer.next;
                                }
                                i = 0;
                                if (print.isEmpty()) {
                                    print.add(" " + n);
                                    print.add(" =");
                                    for (; i < nod.length - 1; i++) {
                                        print.add(" " + nod[i]);
                                    }
                                    print.add(" " + n);
                                } else {
                                    print.set(i, print.get(i++) + " " + n);
                                    print.set(i, print.get(i++) + " =");
                                    for (; i < nod.length + 2; i++) {
                                        if (print.size() > i) {
                                            print.set(i, print.get(i) + " " + nod[i - 2]);
                                        } else {
                                            print.add(fill(((String) print.get(0)).length() - 1) + nod[i - 2]);
                                        }
                                    }
                                    if (print.size() > i) {
                                        print.set(i, print.get(i) + " " + n);
                                    } else {
                                        print.add(fill(((String) print.get(0)).length() - 1) + n);
                                    }
                                    i++;
                                    while (i < print.size()) {
                                        print.set(i, print.get(i++) + "  ");
                                    }
                                }
                            }
                        };

                        // okreslenie trasy tsp na utworzonym wczesniej minimalnym drzewie rozpinajacym prim
                        Node parent = Nodes[idx(startNode)], next;
                        stack.push(Collections.singleton(parent));
                        stack.push(ptree.get(stack.pop()));
                        while (!stack.empty()) {
                            next = stack.pop();
                            stack.push(ptree.get(next));
                            routes.add(new NodePair(parent + "" + next, parent.getDistance(next), true));
                            parent = next;
                        }
                        routes.add(new NodePair(Nodes[idx(startNode)] + "" + parent, Nodes[idx(startNode)].getDistance(parent), true));
                        print.add("\n\ntsp prim - stack pop log:\n");
                        for (int i = print.size() - 1; i >= 0; i--) {
                            output.add("" + print.set(i, null));
                        }
                        output.add("\nfinally " + Nodes[idx(startNode)] + " -> " + parent);
                    }
                    ptree.clear();
                }

                connections = true;
                repaint();
            }
        };

        // obsluga wyjatkow przepelnienia, ktore nie powinno sie zdarzyc
        connecting.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            public void uncaughtException(Thread t, Throwable e) {
                if (e instanceof ThreadDeath) {
                } else if (e instanceof Error) {
                    System.out.println(".\n" + e + ", connecting interrupted\nhint: use less nodes");
                    System.exit(1);
                } else {
                    System.out.println(".\nwarning: " + e.getClass().getSimpleName() + ", connecting interrupted..\nplease try again..");
                }
                dumped = true;
                connections = false;
                routes.clear();
                output.clear();
                return;
            }
        });
        connecting.start();
        /*
         * (new Thread() { public void run() { progress = 0; while
         * (connecting.isAlive()) { try { this.sleep(1000); }
         * catch(InterruptedException e) { return; } if (connecting.isAlive()) {
         * progress++; if (progress == 2) { System.out.println(".\nwarning:
         * computing connections takes very long, possibility of data
         * overflow"); } else if (progress > 5) {
         * System.out.println(".\noperation aborted.\nhint: use less nodes");
         * try { connecting.stop(); } catch (SecurityException exc) { } return;
         * } else System.out.print("."); } else if (!connections && !dumped) {
         * System.out.println("\nfatal error: most propably OutOfMemoryError
         * occured: Java heap overflow\n"); System.exit(1); } } } }).start();
         * try { connecting.join(); } catch (InterruptedException exc) { }
         */
    }
    int progress;
    boolean dumped;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rolled) {
            int w = getWidth();
            int h = getHeight();
            g.setColor(new Color(224, 224, 224));
            g.setFont(new Font("Dialog", Font.PLAIN, 9));
            g.drawString("Michael Sztolcman s3826", w - 130, h - 5);

            for (int i = 0; i < Nodes.length; i++) {
                g.setColor(Color.orange);
                g.fillOval(getX(i), getY(i), 6, 6);
                g.setColor(Color.gray);
                g.drawOval(getX(i), getY(i), 6, 6);
                g.setColor(Color.lightGray);
                g.setFont(new Font("Dialog", Font.PLAIN, 11));
                g.drawString("" + Nodes[i], getX(i) + 8, getY(i) + 18);
            }
            if (connections) {
                if (!printed) {
                    System.out.println((progress == 0 ? ".." : progress == 1 ? "." : "") + "\n\n     connect distance");
                }
                Iterator routeIt = routes.iterator();
                NodePair p;
                Character A, B;
                while (routeIt.hasNext()) {
                    p = (NodePair) routeIt.next();
                    if (!printed) {
                        System.out.println(p.segment + " = " + p.connection + (p.connection ? " " : "") + "   " + p.distance);
                    }
                    if (p.connection) {
                        A = p.segment.charAt(0);
                        B = p.segment.charAt(1);
                        g.drawLine(getX(A) + 3, getY(A) + 3, getX(B) + 3, getY(B) + 3);
                    }
                }
                if (!printed) {
                    for (Iterator it = output.iterator(); it.hasNext();) {
                        System.out.println(it.next());
                    }
                }
                printed = true;
            }
        }
    }

    private int getX(char c) {
        return getX(idx(c));
    }

    private int getX(int i) {
        return (int) (Nodes[i].x * factorX);
    }

    private int getY(char c) {
        return getY(idx(c));
    }

    private int getY(int i) {
        return (int) (Nodes[i].y * factorY);
    }

    private void rollNodes() {
        rolled = false;
        Nodes = new Node[ROUTE_POINTS];
        int x, y, i = 0;
        boolean ok;

        while (i < Nodes.length) {
            x = rnd(getWidth() - 24) + 6;
            y = rnd(getHeight() - 28) + 5;
            ok = true;
            for (int j = 0; j < i && i > 0; j++) {
                if (((x <= Nodes[j].x && x > Nodes[j].x - 16)
                        || (x > Nodes[j].x && x < Nodes[j].x + 16))
                        && ((y <= Nodes[j].y && y > Nodes[j].y - 16)
                        || (y > Nodes[j].y && y < Nodes[j].y + 16))) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                Nodes[i] = new Node(i, x, y);
                i++;
            }
        }
        rolled = true;
    }

    private static int rnd(int n) {
        Random r = new Random();
        return r.nextInt(n);
    }

    private String fill(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += " ";
        }
        return s;
    }

    private int idx(char c) {
        return c - 'A';
    }

    // uproszczone drzewo polaczen dla alg tsp prima
    class PrimTree extends HashMap<Node, HashSet> {

        public void add(Node root, Node child) {
            if (containsKey(root)) {
                HashSet<Node> hs = remove(root);
                hs.add(child);
                put(root, hs);
            } else {
                put(root, new HashSet<Node>(Collections.singleton(child)));
            }
        }

        public HashSet<Node> get(Node node) {
            return super.remove(node);
        }
    };

    public void componentResized(ComponentEvent e) {
        double x = getWidth(), y = getHeight();
        if (size != null) {
            factorX = x / ((double) size.width);
            factorY = y / ((double) size.height);
        }
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    //abstract class myStack
    interface myStack {

        boolean empty();

        Node top();

        Node pop();

        myStack push(Set<Node> hs);
    }
}

class NodePair /*
 * implements Comparable
 */ {

    String segment;
    double distance;
    boolean connection;

    public NodePair(String s, double d, boolean c) {
        segment = s;
        distance = d;
        connection = c;
    }

    public NodePair(String s, double d) {
        this(s, d, false);
    }
    /*
     * public int compareTo(Object o) { return distance - ((NodePair)
     * o).distance < 0 ? -1 : 1;
	}
     */
}

class TSP extends JFrame {

    final static int SIZ_X = 600, SIZ_Y = 400;

    public static enum alg {

        KRUSKAL, PRIM
    };
    static JCheckBox CB_TSP;
    static JButton BRoute;
    static JTextField JTF;
    static JLabel JL;
    static myRadio RBKruskal, RBPrim;

    public TSP() {
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    public static void main(String args[]) {
        System.out.println("Starting TSP...");
        final TSP f = new TSP();
        final TSPgraphics t = new TSPgraphics();
        Container p = f.getContentPane();
        JToolBar j = new JToolBar();
        j.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent e) {
                //t.setSize(SIZ_X, SIZ_Y);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new ComponentEvent(t, ComponentEvent.COMPONENT_RESIZED));
            }
        });

        JButton b = new JButton("   roll   ");
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                t.roll();
            }
        });
        b.setFocusPainted(false);
        j.add(b);
        j.add(Box.createRigidArea(new Dimension(3, 0)));
        Box box = Box.createHorizontalBox();
        box.setPreferredSize(new Dimension(180, 22));
        box.setMaximumSize(new Dimension(180, 22));
        box.add(Box.createHorizontalGlue());
//		box.setBackground(Color.red);
//		box.setOpaque(true);
        JLabel jl = new JLabel("start from:");
        jl.setFont(new Font("Dialog", Font.ITALIC, 11));
        jl.setForeground(Color.gray);
        jl.setAlignmentX(Component.RIGHT_ALIGNMENT);
        box.add(JL = jl);
        box.add(Box.createRigidArea(new Dimension(3, 0)));
        final JTextField tf = new JTextField("A");
        tf.setPreferredSize(new Dimension(27, 22));
        tf.setMaximumSize(new Dimension(27, 22));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(new Font("Dialog", Font.BOLD, 12));
        tf.setForeground(Color.gray);
        tf.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent arg) {
                JTextField f = (JTextField) arg.getSource();
                f.setText("");
            }

            public void keyReleased(KeyEvent arg) {
                JTextField f = (JTextField) arg.getSource();
                f.setText(f.getText().length() > 0 ? f.getText().toUpperCase() : "A");
            }

            public void keyTyped(KeyEvent arg) {
            }
        });
        box.add(JTF = tf);
        box.add(Box.createRigidArea(new Dimension(2, 0)));
        b = new JButton(" TSP ROUTE ");
        b.setToolTipText(" find 'travelling salesman problem' route ");
        b.setMargin(new Insets(1, 4, 1, 4));
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                char ch = tf.getText().charAt(0);
                if (RBPrim.isSelected() && (ch < 'A' || ch > 'A' + TSPgraphics.ROUTE_POINTS - 1)) {
                    JOptionPane.showMessageDialog(null, "Route point '" + tf.getText() + "' not exists!", "route canceled", JOptionPane.WARNING_MESSAGE);
                } else {
                    t.connect(RBPrim.isSelected() ? alg.PRIM : alg.KRUSKAL, ch);
                }
            }
        });
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(93, 22));
        box.add(BRoute = b);
        j.add(box);
        j.add(Box.createGlue());
        j.add(Box.createRigidArea(new Dimension(3, 0)));
        jl = new JLabel("nodes:");
        jl.setFont(new Font("Dialog", Font.ITALIC, 11));
        jl.setForeground(Color.gray);
        j.add(jl);
        j.add(Box.createRigidArea(new Dimension(3, 0)));
        String tf_tmp;
        JTextField tf1 = new JTextField("" + TSPgraphics.ROUTE_POINTS);
        tf1.setPreferredSize(new Dimension(30, 22));
        tf1.setMaximumSize(new Dimension(30, 22));
        tf1.setHorizontalAlignment(JTextField.CENTER);
        tf1.setFont(new Font("Dialog", Font.BOLD, 12));
        tf1.setForeground(Color.gray);
        tf1.addFocusListener(new FocusListener() {

            JTextField j;
            String tf_tmp;
            int n;

            public void focusGained(FocusEvent e) {
                j = (JTextField) e.getSource();
                tf_tmp = j.getText();
                j.setSelectionStart(0);
                j.setSelectionEnd(tf_tmp.length());
            }

            ;

			public void focusLost(FocusEvent e) {
                j = (JTextField) e.getSource();
                if (j.getText().length() == 0) {
                    j.setText(tf_tmp);
                } else {
                    try {
                        n = Integer.parseInt(j.getText());
                        if (n < 2) {
                            n = 2;
                            j.setText("" + n);
                        } else if (n > 50) {
                            n = 50;
                            j.setText("" + n);
                        }
                        TSPgraphics.ROUTE_POINTS = n;
                    } catch (NumberFormatException ex) {
                        j.setText(tf_tmp);
                    }
                }
            }
        });
        j.add(tf1);
        j.add(Box.createRigidArea(new Dimension(4, 0)));
        j.add(Box.createGlue());
        j.add(Box.createGlue());

        ButtonGroup gr = new ButtonGroup();
        /*
         * JRadioButton rb = new JRadioButton("Kruskal", true) { public void
         * paintComponent(Graphics g) { super.paintComponent(g); if
         * (this.isSelected()) setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         * else setCursor(new Cursor(Cursor.HAND_CURSOR)); } };
		gr.add(rb);
         */

        j.add(RBKruskal = new myRadio(f, "Kruskal", gr));
        j.add(RBPrim = new myRadio(f, "Prim", gr));

        CB_TSP = new JCheckBox("TSP", true);
        CB_TSP.setFont(new Font("Dialog", Font.BOLD, 11));
        CB_TSP.setFocusPainted(false);
        CB_TSP.setOpaque(false);
        CB_TSP.setHorizontalTextPosition(JCheckBox.LEADING);
        CB_TSP.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                boolean isSet = (e.getStateChange() == ItemEvent.SELECTED);
                f.setTitle((isSet ? "TSP " : "") + (RBPrim.isSelected() ? "PRIM" : "KRUSKAL"));
                BRoute.setText(isSet ? " TSP ROUTE " : " FIND  MST ");
                BRoute.setToolTipText(" find " + (isSet ? "'travelling salesman problem' route " : "minimum spanning tree using " + (RBPrim.isSelected() ? "Prim's " : "Kruskal's ") + "algorithm "));
            }
        });
        j.add(CB_TSP);

        RBKruskal.doClick();

        p.add(j, BorderLayout.NORTH);
        p.add(t, BorderLayout.CENTER);
        p.add(new JPanel(), BorderLayout.WEST);
        p.add(new JPanel(), BorderLayout.EAST);
        f.setSize(SIZ_X, SIZ_Y);
        f.setMinimumSize(new Dimension(530, 360));
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        t.roll();
    }

    public static boolean TSPselected() {
        return CB_TSP.isSelected();
    }

    static class myRadio extends JRadioButton {

        public myRadio(final JFrame f, String n, ButtonGroup g) {
            super(n);
            setFont(new Font("Dialog", Font.BOLD, 11));
            setFocusPainted(false);
            setOpaque(false);
            setHorizontalTextPosition(JCheckBox.LEADING);
            addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    //((JFrame) getParent().getParent().getParent().getParent().getParent()).setTitle("");
                    boolean isSet = TSPselected();
                    f.setTitle((isSet ? "TSP " : "") + getText().toUpperCase());
                    BRoute.setText(isSet ? " TSP ROUTE " : " FIND  MST ");
                    BRoute.setToolTipText(" find " + (isSet ? "'travelling salesman problem' route " : "minimum spanning tree using " + getText() + "'s algorithm "));
                    JL.setVisible(RBPrim.isSelected());
                    JTF.setVisible(RBPrim.isSelected());
                }
            });
            g.add(this);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.isSelected()) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
    }
}
