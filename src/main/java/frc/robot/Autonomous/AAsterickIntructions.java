package frc.robot.Autonomous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AAsterickIntructions{
    public List<Node> path;
    public static final int SQUARE_SCALE = 5;

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
}
