package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase{
    public Drivetrain(){}

    public void drive(double speed, double turn, boolean squareInput){
        speed = applyDeadband(speed, 0.02);
        turn = applyDeadband(turn, 0.02);

        speed = RobotMap.variables.speedLimiter.calculate(speed);
        turn = RobotMap.variables.turnLimiter.calculate(turn);

        RobotMap.variables.drive.arcadeDrive(speed, turn, squareInput);
    }

    private double applyDeadband(double value, double deadband){
        if(Math.abs(value) < deadband){
            return 0.0;
        }
        return value;
    }

    public void curvatureDrive(double speed, double turn, boolean squareInput){
        if(!RobotMap.variables.curvatureDrive){
            drive(speed, turn, squareInput);
            return;
        }

        RobotMap.variables.drive.curvatureDrive(speed, turn, squareInput);
    }

    public boolean isCurvatureDrive(){
        return RobotMap.variables.curvatureDrive;
    }

    public void toggleCurvatureDrive(){
        RobotMap.variables.curvatureDrive = !RobotMap.variables.curvatureDrive;
    }

    public double getGyroAngle(){
        return RobotMap.variables.navx.getAngle();
    }

    public void resetGyro(){
        RobotMap.variables.navx.reset();
    }

    public double getRightEncoderPosition(){
        double position = RobotMap.variables.rightMotor.getEncoder().getPosition();
        return position;
    }

    public double getLeftEncoderPosition(){
        double position = RobotMap.variables.leftMotor.getEncoder().getPosition();
        return position;
    }

    public double getRightEncoderVelocity(){
        double velocity = RobotMap.variables.rightMotor.getEncoder().getVelocity();
        return velocity;
    }

    public double getLeftEncoderVelocity(){
        double velocity = RobotMap.variables.leftMotor.getEncoder().getVelocity();
        return velocity;
    }

    public void stop(){
        RobotMap.variables.speedLimiter.reset(0);
        RobotMap.variables.turnLimiter.reset(0);
        RobotMap.variables.drive.arcadeDrive(0, 0);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Gyro Angle", getGyroAngle());
        SmartDashboard.putBoolean("Curvature Drive", isCurvatureDrive());
    }
}
