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
    private JButton[][] gridButtons;

    private Node start;
    private Node goal;
    private Timer timer;

    public AAsterickUI(){
        grid = new int[ROWS][COLS];
        path = new ArrayList<>();
        gridButtons = new JButton[ROWS][COLS];
        exploredNodes = new ArrayList<>();
        goal = new Node(0, 0);
        start = new Node(ROWS-1, COLS -1);
    }


    private void findPath(){
        exploredNodes.clear();
        path.clear();
        path = AAsterick.findPath(grid, start, goal, exploredNodes);
    }


    public void startVisualization(){
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentExploredIndex < exploredNodes.size()){
                    Node node = exploredNodes.get(currentExploredIndex);
                    colorCell(node.x, node.y, Color.CYAN);
                    currentExploredIndex++;
                }
                else if(currentPathIndex < path.size()){
                    Node node = path.get(currentPathIndex);
                    colorCell(node.x, node.y, Color.BLUE);
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

    public void stopVisualization(){
        if(timer != null && timer.isRunning()){
            timer.stop();
        }
    }

    private void colorCell(int row, int col, Color color){
        gridButtons[row][col].setBackground(color);
    }

    public JPanel BuiltUI(){
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel gridpanel = new JPanel(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton cell = new JButton();
                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cell.setMargin(new Insets(0, 0, 0, 0));
                cell.setBackground(Color.WHITE);

                final int row = i;
                final int col = j;

                cell.addActionListener(
                    e -> {
                        if (grid[row][col] == 0){
                            grid[row][col] = 1;
                            cell.setBackground(Color.BLACK);
                        }
                        else{
                            grid[row][col] = 0;
                            cell.setBackground(Color.WHITE);
                        }
                    }
                );

                gridButtons[i][j] = cell;
                gridpanel.add(cell);
            }
        }

        colorCell(start.x, start.y, Color.GREEN);
        colorCell(goal.x, goal.y, Color.RED);

        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("start");
        JButton stopButton = new JButton("stop");

        startButton.addActionListener(
            e -> {
                stopVisualization(); 
                exploredNodes.clear();
                path.clear();
                currentExploredIndex = 0;
                currentPathIndex = 0;
                findPath();
                startVisualization();
            }
        );

        stopButton.addActionListener(
            e -> {
                stopVisualization();
            }
        );

        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        mainPanel.add(gridpanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() ->{
            JFrame frame = new JFrame("A* PathFinder Visualizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            AAsterickUI ui = new AAsterickUI();

            frame.setContentPane(ui.BuiltUI());

            frame.pack();
            frame.setVisible(true);
        });
    }
}
