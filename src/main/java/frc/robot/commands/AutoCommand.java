package frc.robot.commands;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Autonomous.AAsterickIntructions;
import frc.robot.Autonomous.AAsterickUI;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Autonomous.Instructions;

public class AutoCommand extends Command {
    private Drivetrain drivetrain;
    private List<Instructions> instructions;
    private int currentIndex = 0;
    private boolean isTurning = false;
    private boolean isMoving = false;
    private double initializeGyroAngle = 0;
    private double targetDistance = 0;
    private double initalizeLeftEncoder = 0;
    private double initalizeRightEncoder = 0;

    private static final double WHEET_CIRCUMFERENCE = 0.478; //to be changed
    private static final double GEAR_RATIO = 1.0; //to be changes 

    public AutoCommand(Drivetrain drivetrain){
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override 
    public void initialize(){
        AAsterickUI ui = new AAsterickUI();
        ui.findPath();
        List<int[]> points = AAsterickIntructions.getPoints(ui.path);
        instructions = AAsterickIntructions.generateInstructions(points);

        currentIndex = 0;
        isTurning = false;
        isMoving = false;
        
        drivetrain.stop();
    }

    @Override
    public void execute(){
        if(currentIndex >= instructions.size()){
            drivetrain.stop();
            return;
        }

        Instructions current = instructions.get(currentIndex);

        switch(current.getAction()){
            case "TURN":
                if(!isTurning){
                    initializeGyroAngle = drivetrain.getGyroAngle();
                    isTurning = true;
                }

                double targetAngle = initializeGyroAngle + current.getValue();
                double error = targetAngle - drivetrain.getGyroAngle();

                if(Math.abs(error) > 2){
                    double turnSpeed = Math.copySign(0.4, error);
                    drivetrain.drive(0, turnSpeed, false);
                }
                else{
                    drivetrain.drive(0, 0, false);
                    isTurning = false;
                    currentIndex ++;
                }
                break;
            
            case "MOVE":
                if(!isMoving){
                    initalizeLeftEncoder = drivetrain.getLeftEncoderPosition();
                    initalizeRightEncoder = drivetrain.getLeftEncoderPosition();
                    targetDistance = current.getValue() / 100.0;
                    isMoving = true;
                }

                double averageDistance = (((drivetrain.getLeftEncoderPosition() - initalizeLeftEncoder) + (drivetrain.getRightEncoderPosition() - initalizeRightEncoder)) / 2.0);

                if (averageDistance < targetDistance){
                    drivetrain.drive(0.3, 0, false);
                }

                else{
                    drivetrain.drive(0, 0, false);
                    isMoving = false;
                    currentIndex ++;
                }
                break;
        }
    }

    @Override
    public boolean isFinished(){
        return currentIndex >= instructions.size();
    }

    @Override
    public void end(boolean interrupt){
        drivetrain.stop();
    }
}
