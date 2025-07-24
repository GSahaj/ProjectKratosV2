package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public final class RobotMap {

    public static class OperatorConstants {
        public static final int LEFT_CHANNEL = 0;
        public static final int RIGHT_CHANNEL = 1;

        public static final double SLEW_RATE_LIMITER = 1.0;

        public static final int JOYSTICK_PORT = 0;

        public static final int CURVATURE_DRIVE_BUTTON = 4;
        public static final int RESET_FIELD_BUTTON = 5;
    }

    public static class OperatorVariables {
        public VictorSP leftMotor;
        public VictorSP rightMotor;

        public AHRS navx;

        public boolean squareInput;
        public boolean curvatureDrive;

        public SlewRateLimiter speedLimiter;
        public SlewRateLimiter turnLimiter;

        public DifferentialDrive drive;

        public OperatorVariables(){
            leftMotor = new VictorSP(OperatorConstants.LEFT_CHANNEL);
            rightMotor = new VictorSP(OperatorConstants.RIGHT_CHANNEL);

            rightMotor.setInverted(true);
            leftMotor.setInverted(false);
             
            drive = new DifferentialDrive(leftMotor, rightMotor);

            drive.setSafetyEnabled(true);
            drive.setExpiration(0.1);

            navx = new AHRS(SPI.Port.kMXP);


            speedLimiter = new SlewRateLimiter(OperatorConstants.SLEW_RATE_LIMITER);
            turnLimiter = new SlewRateLimiter(OperatorConstants.SLEW_RATE_LIMITER);

            curvatureDrive = false;
            squareInput = true;
            
            navx.reset();
        }

    }

    public static final OperatorVariables variables = new OperatorVariables();
}
