package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Into The Deep 24-25")
public class TeleOp extends OpMode {
    protected DcMotor frontLeft;
    protected DcMotor frontRight;
    protected DcMotor backLeft;
    protected DcMotor backRight;
    protected DcMotor linearActuator;
    protected DcMotor linearSlideVertical;
    protected DcMotor linearSlideHorizontal;
    protected DcMotor intake;
    protected DcMotor intakeMovementLeft;
    protected DcMotor intakeMovementRight;
    private Servo mainHand;
    private Servo clawRight;
    private Servo clawLeft;
    private Servo clawMovement;

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        linearActuator = hardwareMap.get(DcMotor.class, "linearActuator");
        mainHand = hardwareMap.get(Servo.class, "mainHand");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");

        //      frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //      backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearActuator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("Status", "Initialized");


    }

    @Override
    public void loop() {

//Telemetry


        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearActuator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        clawLeft.setDirection(Servo.Direction.REVERSE);


// Movement Code this is field centric


        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;

        // holonomic formulas

        float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = (float) Range.clip(FrontRight, -0.8, 0.8);
        FrontLeft = (float) Range.clip(FrontLeft, -0.8, 0.8);
        BackLeft = (float) Range.clip(BackLeft, -0.8, 0.8);
        BackRight = (float) Range.clip(BackRight, -0.8, 0.8);

        // write the values to the motors
        frontRight.setPower(FrontRight);
        frontLeft.setPower(FrontLeft);
        backLeft.setPower(BackLeft);
        backRight.setPower(BackRight);


//Linear Actuator Movement


        //Move linear actuator up and down.
        if (gamepad1.dpad_up) {
            // Move linear actuator up (adjust as needed)
            linearActuator.setPower(1.0);
        } else if (gamepad1.dpad_down) {
            // Move linear actuator down (adjust as needed)
            linearActuator.setPower(-1.0);
        } else {
            // Stop linear actuator
            linearActuator.setPower(0);
        }
        //Move Linear actuator pan motor using dpad

        if (gamepad1.left_bumper) {
            // Move linear pan left (adjust as needed)
        } else

        if (gamepad1.right_bumper) {
            // Move linear pan right (adjust as needed)
        }

//        linearPan.setPower(0);
        // Stop linear pan


        //Servos


//Intake Servos
/*        while (gamepad2.cross) {
            // intake servo in (adjust as needed)
            intakeRight.setPower(1);
            intakeLeft.setPower(-1);
        }
        while (gamepad2.triangle) {
           // intake servo out (adjust as needed)
            intakeRight.setPower(-1.0);
            intakeLeft.setPower(1);
        }
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
*/
/*
        if (gamepad2.cross) {
            intakeRight.setPower(1);
            intakeLeft.setPower(-1);
        } else if (gamepad2.square) {
            intakeRight.setPower(-1);
            intakeLeft.setPower(1);
        } else {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }


//Drop servos
        if (gamepad2.triangle) {
            // intake servo in (adjust as needed)
            backDoorRight.setPosition(0.0);
            backDoorLeft.setPosition(0.35);
        }
//Reset servo position
        if (gamepad2.circle) {
            backDoorRight.setPosition(0.35);
            backDoorLeft.setPosition(0);
        }
*/
        if(gamepad2.cross) {
            clawLeft.setPosition(1);
            clawRight.setPosition(1);
        }
        if (gamepad2.square) {
            clawLeft.setPosition(.5);
            clawRight.setPosition(.5);
        }

//Main hand movement
        //main hand right
        if (gamepad2.dpad_up) {

        } else if (gamepad2.left_bumper) {

        } else if (gamepad2.right_bumper) {
            mainHand.setPosition(0);
        }
//Truss movement


//Paper airplane launch
        if (gamepad2.dpad_right) {

        } else if (gamepad2.dpad_left) {

        }


//Telemetry


        //   double ticks4 = 5,281.1;
        // ticks 0,1,2,3,5 = 537.7


        int position0 = frontLeft.getCurrentPosition();
        int position2 = backLeft.getCurrentPosition();
        int position4 = linearActuator.getCurrentPosition();
        telemetry.addData("FrontLeft", position0);
        telemetry.addData("BackLeft", position2);
        telemetry.addData("LinearActuator", position4);
        telemetry.update();


    }
}