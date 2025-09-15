package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.PhotoVision;

public class PhotonVisionCommand extends  Command{
    private final PhotoVision vision;
    private final Joystick joystick;

    public PhotonVisionCommand(PhotoVision vision, Joystick joystick){
        this.vision = vision;
        this.joystick = joystick;
        addRequirements(vision);
    }

    @Override
    public void execute(){
        if (joystick.getRawButton(6)){
            RobotMap.OperatorVariables.cameraState = !RobotMap.OperatorVariables.cameraState;
        }

        vision.followAprilTag();
    }

    @Override
    public void end(boolean interrupted){
        vision.stopPhotonVision();
    }

    @Override
    public boolean isFinished(){
        return false;
    }   
}
