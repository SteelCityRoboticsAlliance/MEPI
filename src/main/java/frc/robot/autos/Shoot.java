package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.subsystems.ShooterSubsytem;

public class Shoot extends SequentialCommandGroup {
    public Shoot(ShooterSubsytem shooterSubsytem){
        addCommands(new ShooterPIDCommand(shooterSubsytem));
    }
}
