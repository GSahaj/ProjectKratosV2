package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

import org.photonvision.PhotonCamera;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.ResetMode;

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
        public SparkMax leftMotor;
        public SparkMax rightMotor;

        public AHRS navx;

        public SparkClosedLoopController leftpid;
        public SparkClosedLoopController rightpid;

        public SparkMaxConfig leftConfig;
        public SparkMaxConfig rightConfig;

        public boolean squareInput;
        public boolean curvatureDrive;

        public SlewRateLimiter speedLimiter;
        public SlewRateLimiter turnLimiter;

        public DifferentialDrive drive;

        public static PhotonCamera camera;
        public static boolean cameraState;

        double rightPositionFactor;
        double rightVelocityFactor;

        double leftPositionFactor;
        double leftVelocityFactor;

        public OperatorVariables(){
            leftMotor = new SparkMax(OperatorConstants.LEFT_CHANNEL, MotorType.kBrushless);
            rightMotor = new SparkMax(OperatorConstants.RIGHT_CHANNEL, MotorType.kBrushless);

            leftpid = leftMotor.getClosedLoopController();
            rightpid = rightMotor.getClosedLoopController();

            leftConfig = new SparkMaxConfig();
            rightConfig = new SparkMaxConfig();

            leftConfig
                .inverted(false)
                .idleMode(IdleMode.kBrake);
            
            leftConfig.encoder 
                .positionConversionFactor(1000) //need to change factor
                .velocityConversionFactor(1000); //need to change factor

            leftConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pid(1.0, 0.0, 0.0); //need to change pid values

            leftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

            rightConfig
                .inverted(true)
                .idleMode(IdleMode.kBrake);
            
            rightConfig.encoder 
                .positionConversionFactor(1000) //need to change factor
                .velocityConversionFactor(1000); //need to change factor

            rightConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pid(1.0, 0.0, 0.0); //need to change pid values
            
            rightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

            leftConfig.signals.primaryEncoderPositionPeriodMs(5); // change value
            rightConfig.signals.primaryEncoderPositionPeriodMs(5); // change value

            rightPositionFactor = rightMotor.configAccessor.encoder.getPositionConversionFactor();
            rightVelocityFactor = rightMotor.configAccessor.encoder.getVelocityConversionFactor();

            leftPositionFactor = leftMotor.configAccessor.encoder.getPositionConversionFactor();
            leftVelocityFactor = leftMotor.configAccessor.encoder.getVelocityConversionFactor();

            drive = new DifferentialDrive(leftMotor, rightMotor);

            drive.setSafetyEnabled(true);
            drive.setExpiration(0.1);

            navx = new AHRS(SPI.Port.kMXP);


            speedLimiter = new SlewRateLimiter(OperatorConstants.SLEW_RATE_LIMITER);
            turnLimiter = new SlewRateLimiter(OperatorConstants.SLEW_RATE_LIMITER);

            curvatureDrive = false;
            squareInput = true;
            
            navx.reset();

            camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");
            cameraState = false;
        }
    }

    public static final OperatorVariables variables = new OperatorVariables();
}
