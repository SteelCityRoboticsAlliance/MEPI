package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsytem;

/** An example command that uses an example subsystem. */
public class ShootFromDistanceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ShooterSubsytem m_shooterSubsystem;

  private final LimelightSubsystem m_limelightSubsystem;

  /**
   * Creates a new ShootFromDistanceCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShootFromDistanceCommand(
      ShooterSubsytem subsystem, LimelightSubsystem limelightsubsystem) {
    m_shooterSubsystem = subsystem;
    m_limelightSubsystem = limelightsubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(limelightsubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooterSubsystem.shootFromDistance(m_limelightSubsystem.limelightDistance());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}