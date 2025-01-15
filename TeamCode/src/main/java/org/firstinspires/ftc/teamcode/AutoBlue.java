package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Into The Deep 24-25 AutoBlue")
public class AutoBlue extends LinearOpMode {

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
        frontRight.setTargetPosition(-600);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setTargetPosition(-600);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            frontRight.setPower(0.8);
            backRight.setPower(-0.8);
            frontLeft.setPower(0.8111);
            backLeft.setPower(-0.8111);
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

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
        clawLeft.setPosition(0.55);
        clawRight.setPosition(0.45);
        clawMovement.setPosition(0.55);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setTargetPosition(-20);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setTargetPosition(-20);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(frontRight.isBusy() && frontLeft.isBusy() && opModeIsActive()) {
            frontRight.setPower(0.8);
            backRight.setPower(-0.8);
            frontLeft.setPower(0.8111);
            backLeft.setPower(-0.8111);
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
}