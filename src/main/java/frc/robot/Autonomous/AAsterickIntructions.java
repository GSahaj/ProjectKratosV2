package frc.robot.Autonomous;

import java.util.ArrayList;
import java.util.List;

public class AAsterickIntructions{
    public List<Node> path;
    public static final int SQUARE_SCALE = 5; // To be changed

    public AAsterickIntructions(List<Node> path){
        this.path = path;
    }

    public static List<int[]> getPoints(List<Node> path){
        List<int[]> points = new ArrayList<>();

        for (Node point: path){
            points.add(new int[]{point.x * SQUARE_SCALE, point.y * SQUARE_SCALE});
            System.out.println("(" + point.x + "," + point.y + ")");
        }
        return points;
    }

    //All possible robot directions
    private static final int[][] dirs = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    //To determine the direction of the movement between poitns 
    private static int getDirectionIndex(int x, int y){
        x = Integer.signum(x); //normalize to -1, 0, 1
        y = Integer.signum(y);

        for (int i = 0; i < dirs.length; i++) {
            if(dirs[i][0] == x && dirs[i][1] == y){
                return i;
            }
        }

        return -1;
    }

    //Starts at the first point facing the default orientation of Up
    //Calculated using getDirectionIndex() -- if chanegs then uses a trun instructions
    //Generates a move instruction with distance

    public static List<Instructions> generateInstructions(List<int[]> points){
        List<Instructions> instructions = new ArrayList<>();

        //no path to follow
        if(points.size() < 2){
            return instructions; 
        }

        int[] previous = points.get(0);
        int currentDirection = 0; //assume up

        for (int i = 0; i < points.size(); i++) {
            int[] current = points.get(i);

            //determine setp direction
            int dx = current[0] - previous[0];
            int dy = current[1] - previous[1];
            int nextDirection = getDirectionIndex(dx, dy);


            //If the direction is changes then turn
            if(nextDirection != currentDirection){
                int turnAmount = ((nextDirection - currentDirection + 8) % 8)*45;
                if(turnAmount > 180){
                    turnAmount -= 360;
                }
                instructions.add(new Instructions("TURN", turnAmount));
                currentDirection = nextDirection;
            }

            //Add move direction
            int distance = (int)Math.sqrt(dx * dx + dy * dy);
            instructions.add(new Instructions("MOVE", distance));

            previous = current;
        }

        return instructions;
    }
}
