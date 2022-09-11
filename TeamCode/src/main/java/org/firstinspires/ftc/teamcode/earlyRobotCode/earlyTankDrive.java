package org.firstinspires.ftc.teamcode.earlyRobotCode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;


@TeleOp(name="earlyTankDrive")
//@Disabled

public class earlyTankDrive extends LinearOpMode
{
    public DcMotor motorRF = null;
    public DcMotor motorLF = null;
    public DcMotor motorRB = null;
    public DcMotor motorLB = null;
    public DcMotor Slide0 = null;
    public DcMotor Slide1 = null;
    public Servo Claw0 = null;
    public Servo Claw1 = null;


    @Override
    public void runOpMode() throws InterruptedException
    {
        //FtcDashboard dashboard = FtcDashboard.getInstance();
        //telemetry = dashboard.getTelemetry();

        //RobotHardware robot = new RobotHardware(hardwareMap);

        //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //ElapsedTime whatever = new ElapsedTime();

        int slidePosDown = 1000;
        int slidePosJunction = 1500;
        int slidePosLow = 2000;
        int slidePosMid = 3000;
        int slidePosTall = 5000;

        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLB = hardwareMap.dcMotor.get("motorLB");
        Slide0 = hardwareMap.dcMotor.get("Slide0");
        Slide1 = hardwareMap.dcMotor.get("Slide1");
        Claw0 = hardwareMap.servo.get("Claw0");
        Claw1 = hardwareMap.servo.get("Claw1");

        motorRF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slide0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorLF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Slide0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Slide0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        while (opModeIsActive())
        {
            //Code goes here
            motorRF.setPower(gamepad1.right_stick_y);
            motorRB.setPower(-gamepad1.right_stick_y);
            motorLB.setPower(gamepad1.left_stick_y);
            motorLF.setPower(-gamepad1.left_stick_y);

            if (gamepad2.left_bumper) {
                Claw0.setPosition(1);
                Claw1.setPosition(0);
            }
            else if (gamepad2.right_bumper){
                Claw0.setPosition(0);
                Claw1.setPosition(1);
            }



            if (gamepad2.a){
                Slide0.setTargetPosition(slidePosDown);
                Slide1.setTargetPosition(slidePosDown);
                Slide0.setPower(.2);
                Slide1.setPower(.2);
                Slide0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.b){
                Slide0.setTargetPosition(slidePosJunction);
                Slide1.setTargetPosition(slidePosJunction);
                Slide0.setPower(.2);
                Slide1.setPower(.2);
                Slide0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.x){
                Slide0.setTargetPosition(slidePosLow);
                Slide1.setTargetPosition(slidePosLow);
                Slide0.setPower(.2);
                Slide1.setPower(.2);
                Slide0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.y){
                Slide0.setTargetPosition(slidePosMid);
                Slide1.setTargetPosition(slidePosMid);
                Slide0.setPower(.2);
                Slide1.setPower(.2);
                Slide0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.dpad_up){
                Slide0.setTargetPosition(slidePosTall);
                Slide1.setTargetPosition(slidePosTall);
                Slide0.setPower(.7);
                Slide1.setPower(.7);
                Slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Slide0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad2.dpad_down){
                Slide0.setTargetPosition(20000);
                Slide1.setTargetPosition(20000);
                Slide0.setPower(.7);
                Slide1.setPower(.7);
                Slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Slide0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else {
                Slide0.setPower(gamepad2.left_stick_y*0.2);
                Slide1.setPower(gamepad2.left_stick_y*0.2);
            }

            telemetry.addData("Slide0Encoder",Slide0.getCurrentPosition());
            telemetry.addData("Slide1Encoder",Slide1.getCurrentPosition());
            telemetry.addData("Slide0Power",Slide0.getPower());
            telemetry.addData("Slide1Power",Slide1.getPower());

            telemetry.update();

        }
    }
/*
    void forward (int distance, double power) {
        RobotHardware robot = new RobotHardware(hardwareMap);

        robot.motorLB.setTargetPosition(distance);

        robot.motorLB.setPower(power);

        robot.motorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }*/
}