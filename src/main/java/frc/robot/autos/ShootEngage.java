package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveCommandWithPID;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsytem;

public class ShootEngage extends SequentialCommandGroup {
    public ShootEngage(DrivetrainSubsystem drivetrain, ShooterSubsytem shooter, String path){
        // PathPlannerTrajectory shootLeaveCommunity = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);

        // STEP 1: shoot a ball
        addCommands(new ShooterPIDCommand(shooter));

        // STEP 2: move back onto charging station
        addCommands(new DriveCommandWithPID(drivetrain));

    }


}
