package frc.robot.autos;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;
import java.util.HashMap;

public class curveTest extends SequentialCommandGroup {
    public curveTest(DrivetrainSubsystem drivetrain, String path){
        PathPlannerTrajectory curveTest = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command curveTest1 = drivetrain.ramseteAutoBuilderNoPoseReset(new HashMap<>()).fullAuto(curveTest);

        // STEP 1: drive!!
        addCommands(curveTest1);
    }
}
