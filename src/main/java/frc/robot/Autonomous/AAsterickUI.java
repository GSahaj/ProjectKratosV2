package frc.robot.Autonomous;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AAsterickUI extends JPanel{
    private static final int ROWS = 40;
    private static final int COLS = 40;
    private static final int CELL_SIZE = 10;
    private int[][] grid;
    private int currentPathIndex = 0;
    private int currentExploredIndex = 0;
    private List<Node> path;
    private List<Node> exploredNodes;

    private Node start;
    private Node goal;
    private Timer timer;

    public AAsterickUI(){
        grid = new int[ROWS][COLS];
        path = new ArrayList<>();
        exploredNodes = new ArrayList<>();
        start = new Node(ROWS - 1,0);
        goal = new Node(0, COLS - 1);

        startVisualization();
    }


    private void findPath(){
        AAsterick astar = new AAsterick();
        path = astar.findPath(grid, start, goal, exploredNodes);
    }


    private void startVisualization(){
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentExploredIndex < exploredNodes.size()){
                    currentExploredIndex++;
                }
                else if(currentPathIndex < path.size()){
                    currentPathIndex++;
                }
                else{
                    timer.stop();
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.LIGHT_GRAY);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        g.setColor(Color.CYAN);
        for (int i = 0; i < currentExploredIndex; i++) {
            Node node = exploredNodes.get(i);
            g.fillRect(node.y * CELL_SIZE, node.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        g.setColor(Color.YELLOW);
        for (Node node : path) {
            g.fillRect(node.y * CELL_SIZE, node.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        g.setColor(Color.GREEN);
        g.fillRect(start.y * CELL_SIZE, start.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.RED);
        g.fillRect(goal.y * CELL_SIZE, goal.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private static class ButtonClickListener implements ActionListener{
        private final int row;
        private final int col;
        private AAsterickUI variablesAccess;


        public ButtonClickListener(int row, int col, AAsterickUI variableAccess){
            this.row = row;
            this.col = col;
            this.variablesAccess = variableAccess;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button clicked at: (" + row + ", " + col + ")");
            variablesAccess.grid[row][col] = 1;

            variablesAccess.grid[variablesAccess.start.x][variablesAccess.start.y] = 0;
            variablesAccess.grid[variablesAccess.goal.x][variableAccess.goal.y] = 0;
            
        }
    }

    private static void setUp(){
        JFrame frame = new JFrame();
        AAsterickUI ui = new AAsterickUI();
        frame.add(ui);
        frame.setSize(COLS * CELL_SIZE * 2 , ROWS * CELL_SIZE * 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        for (int i = 0; i <ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton button = new JButton(i + ", " + j);
                button.addActionListener(new ButtonClickListener(i, j, ui));
                frame.add(button);
            }
        }
    }

    public static void main(String[] args){
        setUp();
    }


}
