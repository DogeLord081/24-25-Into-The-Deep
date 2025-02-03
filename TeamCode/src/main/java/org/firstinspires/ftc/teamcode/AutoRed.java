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
    double globalAngle, power = .8;

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

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.addData("InitAngle", getCurrentAngle());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Setup
        hangLeft.setDirection(Servo.Direction.FORWARD);
        hangRight.setDirection(Servo.Direction.FORWARD);
        hangLeft.setPosition(0);
        hangRight.setPosition(1);
        sleep(1000);
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        clawMovement.setPosition(0.65);
        intakeMovementLeft.setPosition(0.10);
        intakeMovementRight.setPosition(.90);
        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);
        linearSlideHorizontal.setTargetPosition(0);
        linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideHorizontal.setPower(0.8);

        // Move back
        clawMovement.setPosition(0.65);
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        linearSlideVertical.setTargetPosition(-2550);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-815);
        frontLeft.setTargetPosition(-815);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        double targetBearing = getCurrentAngle();
        telemetry.addData("Ah... eto...", "Bleh! >w<");
        telemetry.update();
        telemetry.addData("targetBearing", targetBearing);
        telemetry.update();
        backStraight(0);
        driveStop();
        sleep(2000);


        // Back more
        clawMovement.setPosition(0.55);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-130);
        frontLeft.setTargetPosition(-130);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backStraight(0);
        driveStop();
        sleep(500);

        linearSlideVertical.setTargetPosition(-2550);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);

        // LSV put down
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        clawMovement.setPosition(0.55);
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        sleep(3500);
        linearSlideVertical.setTargetPosition(-1649);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.4);
        sleep(1500);

        // Claw release
        clawLeft.setPosition(.2);
        clawRight.setPosition(.8);
        sleep(500);

        // Forward
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(355);
        frontLeft.setTargetPosition(355);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        forwardStraight(0);
        driveStop();
        sleep(500);
        clawMovement.setPosition(0.55);
        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);

        // Move right
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-2660);
        frontLeft.setTargetPosition(2660);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        strafeRight(0);
        driveStop();
        sleep(100);

        // Turn Right
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(1800);
        frontLeft.setTargetPosition(-1800);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turnRight();
        driveStop();
        sleep(100);

        // Correction
        correction(180);

        // Intake
        linearSlideHorizontal.setTargetPosition(950);
        linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideHorizontal.setPower(0.8);
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        sleep(250);
        intakeMovementLeft.setPosition(0.83);
        intakeMovementRight.setPosition(0.17);
        sleep(1500);
        intake.setTargetPosition(-241);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setPower(0.3);
        sleep(1000);
        intakeMovementLeft.setPosition(0.07);
        intakeMovementRight.setPosition(.93);
        sleep(100);
        linearSlideHorizontal.setTargetPosition(0);
        linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideHorizontal.setPower(0.8);
        sleep(750);

        // Move to bucket
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(200);
        frontLeft.setTargetPosition(-200);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        strafeLeft(180);
        driveStop();
        sleep(100);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-150);
        frontLeft.setTargetPosition(-150);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backStraight(180);
        driveStop();
        sleep(500);

        intake.setTargetPosition(-0);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intake.setPower(0.3);
        sleep(1500);

        // Into Bucket
        bucketLeft.setPosition(0);
        bucketRight.setPosition(1);
        linearSlideVertical.setTargetPosition(-2800);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideVertical.setPower(0.8);
        sleep(500);


        sleep(20000);


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

        telemetry.addData("CurrentAngle", getCurrentAngle());
        telemetry.update();
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
        hangLeft.setDirection(Servo.Direction.FORWARD);
        hangRight.setDirection(Servo.Direction.FORWARD);
        hangLeft.setPosition(0.5);
        hangRight.setPosition(0.5);
        sleep(100000);
    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    private void driveStop() {
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    private void turnRight() {
        while (frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            frontRight.setPower(power);
            backRight.setPower(power);
            frontLeft.setPower(power);
            backLeft.setPower(-power);
        }
    }

    private void correction(double targetBearing) {
        double currentAngle = getCurrentAngle();

        // Normalize angles to be within -180 to 180 range
        double delta1 = Math.abs(targetBearing - currentAngle);
        double delta2 = Math.abs((-targetBearing) - currentAngle);
        telemetry.addData("targetBearing:", targetBearing);
        telemetry.addData("-targetBearing:", -targetBearing);
        telemetry.addData("currentAngle:", currentAngle);
        telemetry.addData("delta1:", delta1);
        telemetry.addData("delta2:", delta2);
        telemetry.update();
        sleep(5000);

        // Choose the closest target direction
        if (delta2 < delta1) {
            targetBearing = -targetBearing;

            // Update telemetry after changing targetBearing
            telemetry.addData("Target bearing changed to:", targetBearing);
            telemetry.update();
            sleep(3000);
        }

        // Correction loop
        while (Math.abs((currentAngle = getCurrentAngle()) - targetBearing) > 0.3) {
            telemetry.addData("Correction Loop", "currentAngle: " + currentAngle);
            telemetry.addData("Target", targetBearing); // This should now display the updated value
            telemetry.addData("Diff", Math.abs(currentAngle - targetBearing));
            telemetry.update();

            if (currentAngle > targetBearing + 0.2) {
                // Turn left (counterclockwise)
                frontRight.setPower(-0.4);
                backRight.setPower(-0.4);
                frontLeft.setPower(0.4);
                backLeft.setPower(0.4);
            } else if (currentAngle < targetBearing - 0.2) {
                // Turn right (clockwise)
                frontRight.setPower(0.4);
                backRight.setPower(0.4);
                frontLeft.setPower(-0.4);
                backLeft.setPower(-0.4);
            } else {
                // Stop motors
                frontRight.setPower(0);
                backRight.setPower(0);
                frontLeft.setPower(0);
                backLeft.setPower(0);
            }
            sleep(10);
        }

        driveStop();
        sleep(500);
    }

    private void backStraight(double targetBearing) {
        while (frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            double currentAngle = getCurrentAngle();
            if (currentAngle > (targetBearing + 0.5)) {
                frontRight.setPower(power + 0.1);
                backRight.setPower(-power - 0.1);
                frontLeft.setPower(power - 0.1);
                backLeft.setPower(-power + 0.1);
            } else if (currentAngle < (targetBearing - 0.5)) {
                frontRight.setPower(power - 0.1);
                backRight.setPower(-power + 0.1);
                frontLeft.setPower(power + 0.1);
                backLeft.setPower(-power - 0.1);
            } else {
                frontRight.setPower(power);
                backRight.setPower(-power);
                frontLeft.setPower(power);
                backLeft.setPower(-power);
            }
            telemetry.addData("Ah... eto...", "Bleh! >w<");
            telemetry.addData("targetBearing", targetBearing);
            telemetry.addData("CurrentAngle", getCurrentAngle());
            telemetry.update();
        }
    }

    private void forwardStraight(double targetBearing) {
        while (frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            double currentAngle = getCurrentAngle();
            if (currentAngle > (targetBearing + 0.5)) {
                frontRight.setPower(power - 0.1);
                backRight.setPower(power - 0.1);
                frontLeft.setPower(power + 0.1);
                backLeft.setPower(power + 0.1);
            } else if (currentAngle < (targetBearing - 0.5)) {
                frontRight.setPower(power + 0.1);
                backRight.setPower(power + 0.1);
                frontLeft.setPower(power - 0.1);
                backLeft.setPower(power - 0.1);
            } else {
                frontRight.setPower(power);
                backRight.setPower(power);
                frontLeft.setPower(power);
                backLeft.setPower(power);
            }
            telemetry.addData("Ah... eto...", "Bleh! >w<");
            telemetry.addData("targetBearing", targetBearing);
            telemetry.addData("CurrentAngle", getCurrentAngle());
            telemetry.update();
        }
    }

    private void strafeRight(double targetBearing) {
        while (frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            double currentAngle = getCurrentAngle();
            if (currentAngle > (targetBearing + 0.5)) {
                frontRight.setPower(power + 0.1);
                backRight.setPower(power - 0.1);
                frontLeft.setPower(power + 0.1);
                backLeft.setPower(-power + 0.1);
            } else if (currentAngle < (targetBearing - 0.5)) {
                frontRight.setPower(power - 0.1);
                backRight.setPower(power + 0.1);
                frontLeft.setPower(power - 0.1);
                backLeft.setPower(-power - 0.1);
            } else {
                frontRight.setPower(power);
                backRight.setPower(power);
                frontLeft.setPower(power);
                backLeft.setPower(-power);
            }
            telemetry.addData("Ah... eto...", "Bleh! >w<");
            telemetry.addData("targetBearing", targetBearing);
            telemetry.addData("CurrentAngle", getCurrentAngle());
            telemetry.update();
        }
    }

    private void strafeLeft(double targetBearing) {
        while (frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            double currentAngle = getCurrentAngle();
            if (currentAngle > (targetBearing + 0.5)) {
                frontRight.setPower(power - 0.1);
                backRight.setPower(-power - 0.1);
                frontLeft.setPower(power - 0.1);
                backLeft.setPower(power + 0.1);
            } else if (currentAngle < (targetBearing - 0.5)) {
                frontRight.setPower(power + 0.1);
                backRight.setPower(-power + 0.1);
                frontLeft.setPower(power + 0.1);
                backLeft.setPower(power - 0.1);
            } else {
                frontRight.setPower(power);
                backRight.setPower(power);
                frontLeft.setPower(power);
                backLeft.setPower(-power);
            }
            telemetry.addData("Ah... eto...", "Bleh! >w<");
            telemetry.addData("targetBearing", targetBearing);
            telemetry.addData("CurrentAngle", getCurrentAngle());
            telemetry.update();
        }
    }

    private void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     *
     * @return Angle in degrees. + = left, - = right.
     */
    private double getCurrentAngle() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

    private double getAngle() {
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
}

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    /*
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
*/