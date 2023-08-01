package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommandWithPID;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsytem;

public class ShootLeaveCommunityEngage extends SequentialCommandGroup {
    public ShootLeaveCommunityEngage(ShooterSubsytem shooter, DrivetrainSubsystem drivetrain){
        // STEP 1: shoot a ball
        addCommands(new ShooterPIDCommand(shooter));

        // STEP 2: leave community and engage
        addCommands(new DriveCommandWithPID(drivetrain));

    }
}
