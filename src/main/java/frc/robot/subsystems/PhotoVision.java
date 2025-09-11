package frc.robot.subsystems;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class PhotoVision extends SubsystemBase{
    Drivetrain drivetrain;

    public PhotoVision(){
        drivetrain = new Drivetrain();
    }

    public void followAprilTag(){
        var result = RobotMap.OperatorVariables.camera.getLatestResult();

        if(result.hasTargets()){
            PhotonTrackedTarget target = result.getBestTarget();
            double yaw = target.getYaw();
            double distance = target.getBestCameraToTarget().getTranslation().getX();

            double turn = 0.01;
            double forward = 0.5;

            double turning = turn * yaw;
            double movingForward = Math.min(forward * distance, 0.6);

            double leftSpeed = movingForward + turning;
            double rightSpeed = movingForward - turning;

            leftSpeed = Math.max(-1, Math.min(1, leftSpeed));
            rightSpeed = Math.max(1, Math.min(1, rightSpeed));

            drivetrain.drive(leftSpeed, rightSpeed, false);
        }
        else{
            drivetrain.drive(0,0, false);
        }

    }

    public void stopPhotonVision(){
        drivetrain.drive(0, 0, false);
    }

    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Camera State", RobotMap.OperatorVariables.cameraState);
    }
}
