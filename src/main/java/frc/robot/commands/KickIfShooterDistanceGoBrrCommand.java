package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ShooterLookupTable;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsytem;
import frc.robot.subsystems.TowerSubsystem;

/** An example command that uses an example subsystem. */
public class KickIfShooterDistanceGoBrrCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ShooterSubsytem m_shooterSubsystem;

  private final LimelightSubsystem m_limelightSubsystem;
  private final TowerSubsystem m_towerSubsystem;
  private final ShooterLookupTable m_shooterLookupTable;
  /**
   * Creates a new KickIfShooterGoBrrCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public KickIfShooterDistanceGoBrrCommand(
      ShooterSubsytem subsystem,
      TowerSubsystem towerSubsystem,
      LimelightSubsystem limelightSubsystem,
      ShooterLookupTable shooterLookupTable) {
    m_shooterSubsystem = subsystem;
    m_towerSubsystem = towerSubsystem;
    m_limelightSubsystem = limelightSubsystem;
    m_shooterLookupTable = shooterLookupTable;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(towerSubsystem);
    addRequirements(limelightSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double distance = m_limelightSubsystem.limelightDistance();
    m_shooterSubsystem.shootFromDistance(distance);
    if (m_shooterSubsystem.checkAtSpeed(m_shooterLookupTable.getRpmTable(distance))) {
      m_towerSubsystem.setKickerSpeed(1);
      m_towerSubsystem.setTowerSpeed(0.75);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_towerSubsystem.setTowerSpeed(0);
    m_towerSubsystem.setKickerSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooterSubsystem.checkAtSpeed(
        m_shooterLookupTable.getRpmTable(m_limelightSubsystem.limelightDistance()));
  }
}