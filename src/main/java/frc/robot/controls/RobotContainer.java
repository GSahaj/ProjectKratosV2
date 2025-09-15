package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.Driver;
import frc.robot.commands.PhotonVisionCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.PhotoVision;

public class RobotContainer {
    private static final int JOYSTICK_PORT = 0;
    private final Joystick controller = new Joystick(JOYSTICK_PORT);
    private final Drivetrain drivetrain = new Drivetrain();
    private final Driver driver = new Driver(drivetrain, controller, RobotMap.OperatorConstants.CURVATURE_DRIVE_BUTTON);
    private final PhotoVision vision = new PhotoVision();
    private final PhotonVisionCommand visionCommand = new PhotonVisionCommand(vision, controller);
    private final AutoCommand autoCommand = new AutoCommand(drivetrain);

    public RobotContainer(){
        setConfigurations();
    }

    private void setConfigurations(){
        drivetrain.setDefaultCommand(driver);
        vision.setDefaultCommand(visionCommand);
    }

    public void disable(){
        drivetrain.stop();
    }

    public AutoCommand getAutonomousCommand(){
        return autoCommand;
    }
}