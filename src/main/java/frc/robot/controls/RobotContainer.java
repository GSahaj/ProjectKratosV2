package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;
import frc.robot.commands.Driver;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
    private static final int JOYSTICK_PORT = 0;
    private final Joystick controller = new Joystick(JOYSTICK_PORT);
    private final Drivetrain drivetrain = new Drivetrain();
    private final Driver driver = new Driver(drivetrain, controller, RobotMap.OperatorConstants.CURVATURE_DRIVE_BUTTON);

    public RobotContainer(){
        setConfigurations();
    }

    private void setConfigurations(){
        drivetrain.setDefaultCommand(driver);
    }

    public void disable(){
        drivetrain.stop();
    }
}