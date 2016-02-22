package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

//------------------------------------------------------------------------------
//
// PushBotHardware
//
/**
 * Provides a single hardware access point between custom op-modes and the
 * OpMode class for the Push Bot.
 *
 * This class prevents the custom op-mode from throwing an exception at runtime.
 * If any hardware fails to map, a warning will be shown via telemetry data,
 * calls to methods will fail, but will not cause the application to crash.
 *
 * @author SSI Robotics
 * @version 2015-08-13-20-04
 */

public class PushBotHardware extends OpMode

{
    //--------------------------------------------------------------------------
    //
    // PushBotHardware
    //

    /**
     * Construct the class.
     * <p/>
     * The system calls this member when the class is instantiated.
     */
    public PushBotHardware()

    {
        //
        // public
        // ialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotHardware

    //--------------------------------------------------------------------------
    //
    // init
    //

    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     * <p/>
     * The system calls this member once when the OpMode is enabled.
     */
    @Override
    public void init()

    {
        //
        // Use the hardwareMap to associate class members to hardware ports.
        //
        // Note that the names of the devices (i.e. arguments to the get method)
        // must match the names specified in the configuration file created by
        // the FTC Robot Controller (Settings-->Configure Robot).
        //
        // The variable below is used to provide telemetry data to a class user.
        //
        v_warning_generated = false;
        v_warning_message = "Can't map; ";

        //
        // Connect the drive wheel motors.
        //
        // The direction of the right motor is reversed, so joystick inputs can
        // be more generically applied.
        //
        try {
            v_motor_left_drive = hardwareMap.dcMotor.get("left_drive");
        } catch (Exception p_exeception) {
            m_warning_message("left_drive");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_left_drive = null;
        }

        try {
            v_motor_right_drive = hardwareMap.dcMotor.get("right_drive");
            v_motor_right_drive.setDirection(DcMotor.Direction.REVERSE);
        } catch (Exception p_exeception) {
            m_warning_message("right_drive");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_right_drive = null;
        }

    }

    //--------------------------------------------------------------------------
    //
    // a_warning_generated
    //

    /**
     * Access whether a warning has been generated.
     */
    boolean a_warning_generated()

    {
        return v_warning_generated;

    } // a_warning_generated

    //--------------------------------------------------------------------------
    //
    // a_warning_message
    //

    /**
     * Access the warning message.
     */
    String a_warning_message()

    {
        return v_warning_message;

    } // a_warning_message

    //--------------------------------------------------------------------------
    //
    // m_warning_message
    //

    /**
     * Mutate the warning message by ADDING the specified message to the current
     * message; set the warning indicator to true.
     * <p/>
     * A comma will be added before the specified message if the message isn't
     * empty.
     */
    void m_warning_message(String p_exception_message)

    {
        if (v_warning_generated) {
            v_warning_message += ", ";
        }
        v_warning_generated = true;
        v_warning_message += p_exception_message;

    } // m_warning_message

    //--------------------------------------------------------------------------
    //
    // start
    //

    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     * <p/>
     * The system calls this member once when the OpMode is enabled.
     */
    @Override
    public void start()

    {
        //
        // Only actions that are common to all Op-Modes (i.e. both automatic and
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //

    /**
     * Perform any actions that are necessary while the OpMode is running.
     * <p/>
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override
    public void loop()

    {
        //
        // Only actions that are common to all OpModes (i.e. both auto and\
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    } // loop

    //--------------------------------------------------------------------------
    //
    // stop
    //

    /**
     * Perform any actions that are necessary when the OpMode is disabled.
     * <p/>
     * The system calls this member once when the OpMode is disabled.
     */
    @Override
    public void stop() {
        //
        // Nothing needs to be done for this method.
        //

    } // stop

    //--------------------------------------------------------------------------
    //
    // scale_motor_power
    //

    /**
     * Scale the joystick input using a nonlinear algorithm.
     */


    float scale_motor_power(float p_power) {
        //
        // Assume no scaling.
        //
        float l_scale = 0.0f;

        //
        // Ensure the values are legal.
        //
        float l_power = Range.clip(p_power, -1, 1);

        float[] l_array =
                {0.00f, 0.05f, 0.09f, 0.10f, 0.12f
                        , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
                        , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
                        , 1.00f, 1.00f
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int) (l_power * 16.0);
        if (l_index < 0) {
            l_index = -l_index;
        } else if (l_index > 16) {
            l_index = 16;
        }

        if (l_power < 0) {
            l_scale = -l_array[l_index];
        } else {
            l_scale = l_array[l_index];
        }

        return l_scale;

    } // scale_motor_power

    //--------------------------------------------------------------------------
    //
    // a_left_drive_power
    //

    /**
     * Access the left drive motor's power level.
     */
    double a_left_drive_power() {
        double l_return = 0.0;

        if (v_motor_left_drive != null) {
            l_return = v_motor_left_drive.getPower();
        }

        return l_return;

    } // a_left_drive_power

    //--------------------------------------------------------------------------
    //
    // a_right_drive_power
    //

    /**
     * Access the right drive motor's power level.
     */
    double a_right_drive_power() {
        double l_return = 0.0;

        if (v_motor_right_drive != null) {
            l_return = v_motor_right_drive.getPower();
        }

        return l_return;

    } // a_right_drive_power

    //--------------------------------------------------------------------------
    //
    // set_drive_power
    //

    /**
     * Scale the joystick input using a nonlinear algorithm.
     */


    int two;
    int four;
    int answer;
    {
    two = 2;
    four = 4;
    // cant be here since answer needs to = plevel answer = 1;
    }
    void set_drive_power_2(double p_level)
	answer = p_level
    {
        if (gamepad1.a)
        {
            v_motor_left_drive.setPower (p_level*2);
            v_motor_right_drive.setPower (p_level*2);
            answer = answer * 2;
        }
        else if (gamepad1.b)
        {
            v_motor_left_drive.setPower(p_level * 4);
            v_motor_right_drive.setPower(p_level * 4);
            answer = answer * 4;
        }
        else if (gamepad1.left_bumper)
        {
            v_motor_left_drive.setPower (Math.round(p_level/answer));
            v_motor_right_drive.setPower (Math.round(p_level/answer));
        }
    }


    void set_drive_power (double p_left_power, double p_right_power)

    {
        if (v_motor_left_drive != null)
        {
            v_motor_left_drive.setPower (p_left_power);
        }
        if (v_motor_right_drive != null)
        {
            v_motor_right_drive.setPower (p_right_power);
        }

    } // set_drive_power
    //--------------------------------------------------------------------------
    //
    // run_without_left_drive_encoder
    //
    /**
     * Set the left drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_without_left_drive_encoder ()

    {
        if (v_motor_left_drive != null)
        {
            if (v_motor_left_drive.getMode () ==
                    DcMotorController.RunMode.RESET_ENCODERS)
            {
                v_motor_left_drive.setMode
                        ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                        );
            }
        }

    } // run_without_left_drive_encoder

    //--------------------------------------------------------------------------
    //
    // run_without_right_drive_encoder
    //
    /**
     * Set the right drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_without_right_drive_encoder ()

    {
        if (v_motor_right_drive != null)
        {
            if (v_motor_right_drive.getMode () ==
                    DcMotorController.RunMode.RESET_ENCODERS)
            {
                v_motor_right_drive.setMode
                        ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                        );
            }
        }

    } // run_without_right_drive_encoder
    //--------------------------------------------------------------------------
    //
    // v_warning_generated
    //
    /**
     * Indicate whether a message is a available to the class user.
     */
    private boolean v_warning_generated = false;

    //--------------------------------------------------------------------------
    //
    // v_warning_message
    //
    /**
     * Store a message to the user if one has been generated.
     */
    private String v_warning_message;

    //--------------------------------------------------------------------------
    //
    // v_motor_left_drive
    //
    /**
     * Manage the aspects of the left drive motor.
     */
    private DcMotor v_motor_left_drive;


    //--------------------------------------------------------------------------
    //
    // v_motor_right_drive
    //
    /**
     * Manage the aspects of the right drive motor.
     */
    private DcMotor v_motor_right_drive;

    //--------------------------------------------------------------------------


} // PushBotHardware
