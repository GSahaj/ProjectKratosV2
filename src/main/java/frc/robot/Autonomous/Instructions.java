package frc.robot.Autonomous;

public class Instructions {
    public String action;
    public int value;

    public Instructions(String action, int value){
        this.action = action;
        this.value = value;
    }

    @Override
    public String toString(){
        return action + " " + value;
    }
}
