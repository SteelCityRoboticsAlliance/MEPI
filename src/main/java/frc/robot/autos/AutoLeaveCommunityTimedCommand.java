package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoDriveCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoLeaveCommunityTimedCommand extends SequentialCommandGroup {
    public AutoLeaveCommunityTimedCommand(DrivetrainSubsystem drivetrain) {
        addCommands(new AutoDriveCommand(drivetrain, 0.2, 0)
                .withTimeout(7));
    }
}
