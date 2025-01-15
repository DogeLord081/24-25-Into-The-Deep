package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
@Autonomous(name="Into The Deep 24-25 AutoRed")
public class AutoRed extends LinearOpMode {

    /* Declare OpMode members. */
    protected DcMotor frontLeft;
    protected DcMotor frontRight;
    protected DcMotor backLeft;
    protected DcMotor backRight;
    protected DcMotor linearActuator;
    protected DcMotor linearSlideHorizontal;
    protected DcMotor linearSlideVertical;
    protected DcMotor intake;
    private Servo intakeMovementLeft;
    private Servo intakeMovementRight;
    private Servo clawMovement;
    private Servo clawLeft;
    private Servo clawRight;
    private Servo bucketLeft;
    private Servo bucketRight;
    private Servo hangLeft;
    private Servo hangRight;
    private ColorSensor intakeColorSensor;
    private ElapsedTime runtime = new ElapsedTime();
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, power = .8, correction;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        //frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        linearActuator = hardwareMap.get(DcMotor.class, "linearActuator");
        linearSlideHorizontal = hardwareMap.get(DcMotor.class, "linearSlideHorizontal");
        linearSlideVertical = hardwareMap.get(DcMotor.class, "linearSlideVertical");
        linearSlideHorizontal.setDirection(DcMotor.Direction.REVERSE);
        intake = hardwareMap.get(DcMotor.class, "intake");
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlideHorizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMovementLeft = hardwareMap.get(Servo.class, "intakeMovementLeft");
        intakeMovementRight = hardwareMap.get(Servo.class, "intakeMovementRight");
        clawMovement = hardwareMap.get(Servo.class, "clawMovement");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        bucketLeft = hardwareMap.get(Servo.class, "bucketLeft");
        bucketRight = hardwareMap.get(Servo.class, "bucketRight");
        intakeColorSensor = hardwareMap.get(ColorSensor.class, "intakeColorSensor");
        telemetry.addData("Status", "Initialized");
        hangLeft = hardwareMap.get(Servo.class, "hangLeft");
        hangRight = hardwareMap.get(Servo.class, "hangRight");

        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlideVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Setup
        hangLeft.setDirection(Servo.Direction.FORWARD);
        hangRight.setDirection(Servo.Direction.FORWARD);
        hangLeft.setPosition(0);
        hangRight.setPosition(1);
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        clawMovement.setPosition(0.55);
        intakeMovementLeft.setPosition(0.10);
        intakeMovementRight.setPosition(.90);
        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);
        linearSlideHorizontal.setTargetPosition(0);
        linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideHorizontal.setPower(0.8);

        // Move back
        clawMovement.setPosition(0.55);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-1800);
        frontLeft.setTargetPosition(-1800);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            correction = checkDirection();
            frontRight.setPower(power + correction);
            backRight.setPower(-(power - correction));
            frontLeft.setPower(-(power + correction));
            backLeft.setPower(power - correction);
        }
        frontRight.setPower(0 + correction);
        backRight.setPower(-(0 - correction));
        frontLeft.setPower(-(0 + correction));
        backLeft.setPower(0 - correction);

        // Turn to correct
        /*
        clawMovement.setPosition(0.7);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-50);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && opModeIsActive()) {
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
        }
        */


        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

        // Bucket and LSV
        clawMovement.setPosition(0.55);
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        linearSlideVertical.setTargetPosition(-2750);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);
        sleep(5000);


        // Back more
        clawMovement.setPosition(0.55);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-20);
        frontLeft.setTargetPosition(-20);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            correction = checkDirection();
            frontRight.setPower(power + correction);
            backRight.setPower(-(power - correction));
            frontLeft.setPower(-(power + correction));
            backLeft.setPower(power - correction);
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

        linearSlideVertical.setTargetPosition(-2750);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);

        // LSV put down
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        clawMovement.setPosition(0.55);
        sleep(5000);
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        linearSlideVertical.setTargetPosition(-1600);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.4);

        // Claw release
        sleep(2000);
        clawLeft.setPosition(.2);
        clawRight.setPosition(.8);
        sleep(2000);


        // Forward
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(200);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && opModeIsActive()) {
            frontRight.setPower(0.5);
            backRight.setPower(0.5);
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        sleep(1000);
        clawMovement.setPosition(0.55);
        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);
        sleep(2000);

        // Move right
        /*
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(2500);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && opModeIsActive()) {
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);
            frontLeft.setPower(-0.5);
            backLeft.setPower(0.5);
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        sleep(500);

        // Move back
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-1300);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && opModeIsActive()) {
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

        // Move left
        /*
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-200);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && opModeIsActive()) {
            frontRight.setPower(0.5);
            backRight.setPower(0.5);
            frontLeft.setPower(0.5);
            backLeft.setPower(-0.5);
        }
         */
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        sleep(500);

        // Setup
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        clawMovement.setPosition(0.55);
        intakeMovementLeft.setPosition(0.10);
        intakeMovementRight.setPosition(.90);
        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);
        linearSlideHorizontal.setTargetPosition(0);
        linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideHorizontal.setPower(0.8);
        sleep(100000);
    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }
}