package frc.robot.autos;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsytem;

import java.util.HashMap;

public class ShootLeaveCommunity extends SequentialCommandGroup {
    //CONSTRUCTOR
    public ShootLeaveCommunity(DrivetrainSubsystem drivetrain, ShooterSubsytem shooter, String path) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command shootAndLeaveCommunity = drivetrain.ramseteAutoBuilderNoPoseReset(new HashMap<>()).fullAuto(trajectory);
        //shoot a ball
        addCommands(new ShooterPIDCommand(shooter));

        //leave community
        addCommands(shootAndLeaveCommunity);
    }
}
