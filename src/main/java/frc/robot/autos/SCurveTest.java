package frc.robot.autos;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.HashMap;

public class SCurveTest extends SequentialCommandGroup {

    public SCurveTest(DrivetrainSubsystem drivetrain, String path) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command curveTest1 = drivetrain.ramseteAutoBuilderNoPoseReset(new HashMap<>()).fullAuto(trajectory);

        addCommands(curveTest1);
    }
}
