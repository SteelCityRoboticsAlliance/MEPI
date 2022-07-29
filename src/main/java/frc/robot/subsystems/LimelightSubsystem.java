package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import javax.swing.text.html.AccessibleHTML.TableElementInfo.TableAccessibleContext;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightSubsystem extends SubsystemBase{
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry m_horizontalAngle = table.getEntry("tx");
    NetworkTableEntry m_verticalAngle = table.getEntry("ty");
    NetworkTableEntry m_targetArea = table.getEntry("ta");

    public LimelightSubsystem(){

    }

    public double limelightDistance(){
        double targetOffsetAngle_Vertical = m_verticalAngle.getDouble(0.0);

        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 25.0;

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 20.0;

        // distance from the target to the floor
        double goalHeightInches = 104;

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches)/Math.tan(angleToGoalRadians);

    }
    public double limelightAngle(){
        return m_horizontalAngle.getDouble(0);
    }

}
