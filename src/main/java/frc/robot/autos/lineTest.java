package frc.robot.autos;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.*;

import java.util.HashMap;

public class lineTest extends SequentialCommandGroup {
    //CONSTRUCTOR
    public lineTest(DrivetrainSubsystem drivetrain, String path) {
        PathPlannerTrajectory LineTest = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command LineTest1 = drivetrain.ramseteAutoBuilderNoPoseReset(new HashMap<>()).fullAuto(LineTest);
        // STEP 1: drive!
        addCommands(LineTest1);

    }
}
