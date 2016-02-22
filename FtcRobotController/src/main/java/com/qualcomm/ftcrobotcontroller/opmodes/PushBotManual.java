package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotManual
//
public class PushBotManual extends PushBotTelemetry

{
    //--------------------------------------------------------------------------
    //
    // PushBotManual
    //
    public PushBotManual ()

    {
    } // PushBotManual

    //--------------------------------------------------------------------------
    //
    // loop
    //
    @Override public void loop ()

    {


        //
        // Manage the drive wheel motors.
        //
        float l_left_drive_power = scale_motor_power(-gamepad1.left_stick_y);
        float l_right_drive_power = scale_motor_power(-gamepad1.right_stick_y);

        set_drive_power_ (l_left_drive_power);
        set_drive_power_ (l_right_drive_power);
    } // loop

} // PushBotManual
