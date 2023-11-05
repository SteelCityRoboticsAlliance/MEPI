package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DriveCommandWithPID extends CommandBase {
    private final DrivetrainSubsystem m_drivetrain;

    public DriveCommandWithPID(DrivetrainSubsystem drivetrainSubsystem) {
        m_drivetrain = drivetrainSubsystem;
        addRequirements(m_drivetrain);
    }

    @Override
    public void execute() {
        m_drivetrain.smartVelocityControl(Units.inchesToMeters(48), Units.inchesToMeters(48));
    }

}
