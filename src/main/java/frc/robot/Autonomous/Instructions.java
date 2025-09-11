package frc.robot.Autonomous;

public class Instructions {
    private String action;
    private int value;

    public Instructions(String action, int value){
        this.action = action;
        this.value = value;
    }

    public String getAction(){
        return action;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){
        return action + " " + value;
    }
}
