package org.firstinspires.ftc.teamcode.odometry;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="basicOdometryMath")

public class basicOdometryMath extends LinearOpMode{

//odometry constants (tune these)
double L = 8.30118339;   //distance between left and right odometers (in inches)
double B = -3.1;   //distance from center of left/right encoders to the perpendicular encoder (in inches)
double R = 0.999631136110775;   //wheel radius (in inches)
double N = 8192;  //encoder ticks per revoluton
double inPerTick = 2.0 * Math.PI * R / N;
  
//changes starting location (in inches)
double X = 0;
double Y = 0;
double Heading = 0;
  
//track encoder values between loops
int currentRightPos = 0;
int currentLeftPos = 0;
int currentPerpendicularPos = 0;
int oldRightPos = 0;
int oldLeftPos = 0;
int oldPerpendicularPos = 0;

  
  public void runOpMode() throws InterruptedException {

  //init hardware map
  odometryRobotHardware robot = new odometryRobotHardware(hardwareMap);
  telemetry.addData("here",0);
  telemetry.update();

  //sleep(3000);

  //robot.resetDriveEncoders();
    robot.motorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.motorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.motorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.motorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    robot.motorLF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    robot.motorLB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    robot.motorRF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    robot.motorRB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    telemetry.addData("here",1);
    telemetry.update();

    //sleep(3000);

    odometryMethod odometry = new odometryMethod();
    telemetry.addData("here",2);
    telemetry.update();

    waitForStart();

    while (opModeIsActive()) {

      robot.motorRF.setPower(.5*((-gamepad1.right_stick_y - gamepad1.right_stick_x) - (-gamepad1.left_stick_x)));
      robot.motorRB.setPower(.5*(-(-gamepad1.right_stick_x + gamepad1.right_stick_y) - (-gamepad1.left_stick_x)));
      robot.motorLB.setPower(.5*(-((gamepad1.right_stick_y + gamepad1.right_stick_x) - (-gamepad1.left_stick_x))));
      robot.motorLF.setPower(.5*(-(-gamepad1.right_stick_x + gamepad1.right_stick_y) - (gamepad1.left_stick_x)));
      
      //record last loop's encoder reading
      oldRightPos = currentRightPos;
      oldLeftPos = currentLeftPos;
      oldPerpendicularPos = currentPerpendicularPos;
      
      //record a new encoder reading this loop
      currentRightPos = -robot.rightEncoder.getCurrentPosition();
      currentLeftPos = -robot.leftEncoder.getCurrentPosition();
      currentPerpendicularPos = -robot.perpendicularEncoder.getCurrentPosition();
      
      //find the delta encoder values of each encoder
      int dn1 = currentLeftPos - oldLeftPos;
      int dn2 = currentRightPos - oldRightPos;
      int dn3 = currentPerpendicularPos - oldPerpendicularPos;
      
      //find the delta of x,y,heading reletive to the robot
      double dtheta = inPerTick * (dn2 - dn1) / L;
      double dx = inPerTick * (dn1 + dn2) / 2.0;
      double dy = inPerTick * (dn3 - (dn2 - dn1) * B / L);
      
      //add the robots movement this loop to the global location
      double theta = (dtheta / 2.0);
      X += dx * Math.cos(theta) - dy * Math.sin(theta);
      Y += dx * Math.sin(theta) + dy * Math.cos(theta);
      Heading += dtheta;

      Heading = odometry.angleWrapRad(Heading);
        
      //telemetry:
      telemetry.addData("rightEncoder", currentRightPos);
      telemetry.addData("LeftEncoder", currentLeftPos);
      telemetry.addData("PerpendicularEncoder", currentPerpendicularPos);
      
      telemetry.addData("X", X);
      telemetry.addData("Y", Y);
      telemetry.addData("Heading", Math.toDegrees(Heading));

      telemetry.update();
    }
  }
}
