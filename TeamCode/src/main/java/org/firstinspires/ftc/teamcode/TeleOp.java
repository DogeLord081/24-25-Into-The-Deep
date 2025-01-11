package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.concurrent.TimeUnit;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Into The Deep 24-25")
public class TeleOp extends OpMode {
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


    @Override
    public void init() {
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
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideHorizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMovementLeft = hardwareMap.get(Servo.class, "intakeMovementLeft");
        intakeMovementRight = hardwareMap.get(Servo.class, "intakeMovementRight");
        clawMovement = hardwareMap.get(Servo.class, "clawMovement");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        bucketLeft = hardwareMap.get(Servo.class, "bucketLeft");
        bucketRight = hardwareMap.get(Servo.class, "bucketRight");
        hangLeft = hardwareMap.get(Servo.class, "hangLeft");
        hangRight = hardwareMap.get(Servo.class, "hangRight");
        intakeColorSensor = hardwareMap.get(ColorSensor.class, "intakeColorSensor");
        telemetry.addData("Status", "Initialized");

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlideVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        linearSlideVertical.setTargetPosition(0);
        linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setTargetPosition(0);




    }

    @Override
    public void loop() {

//Telemetry


        // clawLeft.setDirection(Servo.Direction.REVERSE);


// Movement Code this is field centric


        float axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        float lateral = gamepad1.left_stick_x;
        float yaw = gamepad1.right_stick_x;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        float FrontLeft = axial + lateral + yaw;
        float FrontRight = axial - lateral - yaw;
        float BackLeft = axial - lateral + yaw;
        float BackRight = axial + lateral - yaw;

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

        /*
        if (gamepad1.cross) {
            linearSlideHorizontal.setTargetPosition(-1200);
            linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideHorizontal.setPower(0.8);
        }

        if (gamepad1.circle) {
            linearSlideHorizontal.setTargetPosition(0);
            linearSlideHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideHorizontal.setPower(0.8);
        }
         */
        if (gamepad1.options) {
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
            clawLeft.setPosition(0.55);
            clawRight.setPosition(0.45);
            clawMovement.setPosition(0.7);
            hangLeft.setDirection(Servo.Direction.FORWARD);
            hangRight.setDirection(Servo.Direction.FORWARD);
            hangLeft.setPosition(0);
            hangRight.setPosition(1);
            intakeMovementLeft.setPosition(0.10);
            intakeMovementRight.setPosition(.90);
            linearSlideVertical.setTargetPosition(0);
            linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideVertical.setPower(0.8);
            linearSlideHorizontal.setPower(-0.8);
        }

        // linearSlideHorizontal
        if (gamepad2.right_trigger != 0) {
            linearSlideHorizontal.setPower(0.8);
        }

        // linearSlideVertical
        if (gamepad1.right_bumper) {
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
            linearSlideVertical.setTargetPosition(-2592);
            linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideVertical.setPower(0.8);
        } else if (gamepad1.right_trigger != 0) {
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
            clawLeft.setPosition(0.55);
            clawRight.setPosition(0.45);
            linearSlideVertical.setTargetPosition(-2338);
            linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideVertical.setPower(0.8);
        } else if (gamepad1.left_trigger != 0) {
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
            linearSlideVertical.setTargetPosition(-1649);
            linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideVertical.setPower(0.8);
        } else if (gamepad1.left_bumper) {
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
            linearSlideVertical.setTargetPosition(-338);
            linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideVertical.setPower(0.8);
            clawLeft.setPosition(0.55);
            clawRight.setPosition(0.45);
        }

        if (gamepad2.right_bumper) {
            intake.setTargetPosition(-241);
            intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intake.setPower(0.3);
        } else if (gamepad2.left_bumper) {
            intake.setTargetPosition(-0);
            intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intake.setPower(0.3);
        } else if (gamepad1.dpad_right) {
            while (gamepad1.dpad_right) {
                intake.setPower(0.3);
            }
        } else if (gamepad1.dpad_left) {
            while (gamepad1.dpad_left) {
                intake.setPower(-0.3);
            }
        }

        // intakeMovement up
        if (gamepad2.dpad_up) {
            intakeMovementLeft.setPosition(0.07);
            intakeMovementRight.setPosition(.93);
            //intakeMovement down
        } else if (gamepad2.dpad_down) {
            intakeMovementLeft.setPosition(0.83);
            intakeMovementRight.setPosition(0.17);
        } else if (gamepad2.left_trigger != 0) {            // Auto transfer intake --> bucket
            intakeMovementLeft.setPosition(0.10);
            intakeMovementRight.setPosition(.90);
            linearSlideVertical.setTargetPosition(0);
            linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideVertical.setPower(0.8);
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
            linearSlideHorizontal.setPower(-0.8);
        }

        // Auto transfer bucket --> high chamber
        if (gamepad2.triangle) {
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
            intakeMovementLeft.setPosition(0.5);
            intakeMovementRight.setPosition(0.5);
            linearSlideVertical.setTargetPosition(-2592);
            linearSlideVertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlideVertical.setPower(0.8);
        }

        // claws

        if (gamepad1.dpad_up) {
            clawLeft.setPosition(0.55);
            clawRight.setPosition(0.45);
        } else if (gamepad1.dpad_down) {
            clawLeft.setPosition(.2);
            clawRight.setPosition(.8);
        }


        //bucket
        if (gamepad2.dpad_right) {
            bucketLeft.setPosition(0.7);
            bucketRight.setPosition(0.3);
        } else if (gamepad2.dpad_left) {
            bucketLeft.setPosition(0);
            bucketRight.setPosition(1);
        }

        /*
        if (gamepad1.touchpad) {
            // Move linear actuator up (adjust as needed)
            linearActuator.setPower(1.0);
        } else if (gamepad1.ps) {
            // Move linear actuator down (adjust as needed)
            linearActuator.setPower(-1.0);
        } else {
            // Stop linear actuator
            linearActuator.setPower(0);
        }
         */



        // Get the current position of the encoders
        double linearSlideHorizontalPosition = linearSlideHorizontal.getCurrentPosition();
        double linearSlideVerticalPosition = linearSlideVertical.getCurrentPosition();
        double intakeMovementLeftPosition = intakeMovementLeft.getPosition();
        double intakeMovementRightPosition = intakeMovementRight.getPosition();
        double intakeMovement = intake.getCurrentPosition();


        telemetry.addData("linearSlideHorizontal Encoder Position", linearSlideHorizontalPosition);
        telemetry.addData("linearSlideVertical Encoder Position", linearSlideVerticalPosition);
        telemetry.addData("intakeMovementLeft Encoder Position", intakeMovementLeftPosition);
        telemetry.addData("intakeMovementRight Encoder Position", intakeMovementRightPosition);
        telemetry.addData("intake Encoder Position", intakeMovement);
/*

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
/*
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

*/
    }
}