package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsytem;
import frc.robot.subsystems.TowerSubsystem;

/** An example command that uses an example subsystem. */
public class KickIfShootSetRPMCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ShooterSubsytem m_shooterSubsystem;

  private final TowerSubsystem m_towerSubsystem;
  /**
   * Creates a new KickIfShooterGoBrrCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public KickIfShootSetRPMCommand(ShooterSubsytem subsystem, TowerSubsystem towerSubsystem) {
    m_shooterSubsystem = subsystem;
    m_towerSubsystem = towerSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(towerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooterSubsystem.setPidRpm(ShooterSubsytem.FENDER_RPM);
    if (m_shooterSubsystem.checkAtSpeed(ShooterSubsytem.FENDER_RPM)) {
      m_towerSubsystem.setKickerSpeed(1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooterSubsystem.checkAtSpeed(ShooterSubsytem.FENDER_RPM);
  }
}
