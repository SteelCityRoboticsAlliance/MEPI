package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommandWithPID;
import frc.robot.commands.RunIntakeCommand;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.subsystems.*;

public class ShootLeaveCommunityGetNewPiece extends SequentialCommandGroup {
    // CONSTRUCTOR
    public ShootLeaveCommunityGetNewPiece(DrivetrainSubsystem drivetrain, ShooterSubsytem shooter, IntakeSubsystem intake, HopperSubsystem hopper, TowerSubsystem tower){
        // STEP 1: shoot one ball
        addCommands(new ShooterPIDCommand(shooter));

        // STEP 2: leave community
        addCommands(new DriveCommandWithPID(drivetrain));

        // STEP 3: intake a new piece
        addCommands(new RunIntakeCommand(intake, 0.5, hopper, tower, 1.0 ));


    }

}
