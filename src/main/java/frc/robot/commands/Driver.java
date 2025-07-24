package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;

public class Driver extends Command{
    private final Drivetrain drivetrain;
    private final Joystick controller;

    private final int curvatureDriveButton;

    public Driver(Drivetrain drivetrain, Joystick controller, int curvatureDriveButton){
        this.drivetrain = drivetrain;
        this.controller = controller;
        this.curvatureDriveButton = curvatureDriveButton;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize(){
        drivetrain.resetGyro();
    }

    @Override
    public void execute(){
        //Set the speed and turn parameter to joystick inputs
        double speed = -controller.getY();
        double turn = controller.getX();

        //Toggle between Field Orientated
        if(controller.getRawButton(curvatureDriveButton)){
            drivetrain.toggleCurvatureDrive();
        }
        

        //Disable SquareInput
        if(controller.getRawButton(2)){
            RobotMap.variables.squareInput = !RobotMap.variables.squareInput;
        }

        //Check if Field Orientated
        if(drivetrain.isCurvatureDrive()){
            drivetrain.curvatureDrive(speed, turn, RobotMap.variables.squareInput);
        }else{
            drivetrain.drive(speed, turn, RobotMap.variables.squareInput);
        }

    }

    @Override
    public void end(boolean interrupted){
        drivetrain.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}