// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.HopperCommand;
import frc.robot.commands.KickIfShootSetRPMCommand;
import frc.robot.commands.RunIntakeCommand;
import frc.robot.commands.SetShooterSpeedCommand;
import frc.robot.commands.StopIntakeCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsytem;
import frc.robot.subsystems.TowerSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final XboxController m_primary = new XboxController(0);
  // The robot's subsystems and commands are defined here...
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
  private final ShooterSubsytem m_shooterSubsystem = new ShooterSubsytem();
  private final TowerSubsystem m_towerSubsystem = new TowerSubsystem();
  private final HopperSubsystem m_hopperSubsystem = new HopperSubsystem();
  //   private final LimelightSubsystem m_limelightSubsystem = new LimelightSubsystem();
  //   private final ShooterLookupTable m_shooterLookupTable = new ShooterLookupTable();

  private final RunIntakeCommand m_intakeInCommand =
      new RunIntakeCommand(m_intakeSubsystem, 0.5, m_hopperSubsystem, m_towerSubsystem, 0.5);
  private final RunIntakeCommand m_intakeOutCommand =
      new RunIntakeCommand(m_intakeSubsystem, -0.5, m_hopperSubsystem, m_towerSubsystem, -0.5);
  private final StopIntakeCommand m_intakeStopCommand =
      new StopIntakeCommand(m_intakeSubsystem, m_hopperSubsystem);
  private final DriveCommand m_driveCommand = new DriveCommand(m_drivetrainSubsystem, m_primary);
  // private final KickIfShootSetRPMCommand m_kickIfShootSetRPMCommand =
  //     new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem);

  public RobotContainer() {
    CameraServer.startAutomaticCapture();
    // Configure the button bindings
    configureButtonBindings();
    m_intakeSubsystem.setDefaultCommand(m_intakeStopCommand);
    m_hopperSubsystem.setDefaultCommand(new HopperCommand(m_hopperSubsystem, 0));
    m_shooterSubsystem.setDefaultCommand(new SetShooterSpeedCommand(m_shooterSubsystem, 0));
    m_drivetrainSubsystem.setDefaultCommand(m_driveCommand);
    m_climberSubsystem.setDefaultCommand(new ClimberCommand(m_climberSubsystem, 0));
    ShuffleboardTab testCommands = Shuffleboard.getTab("test commands");

    // testCommands.add("ClimbUp", new ClimberCommand(m_climberSubsystem, 0.25));
    // testCommands.add("ClimbDown", new ClimberCommand(m_climberSubsystem, -0.25));
    // testCommands.add("IntakeIn", new RunIntakeCommand(m_intakeSubsystem, 0.75,
    // m_hopperSubsystem));
    // testCommands.add(
    //     "IntakeOut", new RunIntakeCommand(m_intakeSubsystem, -0.75, m_hopperSubsystem));
    // testCommands.add(
    //     "Shoot RPM", new ShooterPIDCommand(m_shooterSubsystem, m_tunableShooterGoal.get()));
    // testCommands.add("Tower Up", new TowerUpCommand(m_towerSubsystem));
    // testCommands.add("Tower Down", new TowerDownCommand(m_towerSubsystem));
    // testCommands.add("Tower Kicker", new TowerKickerCommand(m_towerSubsystem));
    // testCommands.add("Hopper Up", new HopperCommand(m_hopperSubsystem, 0.5));
    // testCommands.add("Hopper Down", new HopperCommand(m_hopperSubsystem, -0.5));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // driver joystick
    new Button(() -> m_primary.getRightTriggerAxis() > 0.05).whileHeld(m_intakeInCommand);
    new JoystickButton(m_primary, XboxController.Button.kRightBumper.value)
        .whileHeld(m_intakeOutCommand);
    new JoystickButton(m_primary, XboxController.Button.kLeftBumper.value)
        .whileHeld(new ClimberCommand(m_climberSubsystem, 0.75));
    new Button(() -> m_primary.getLeftTriggerAxis() > 0.05)
        .whileHeld(new ClimberCommand(m_climberSubsystem, -0.75));

    new JoystickButton(m_primary, XboxController.Button.kB.value)
        .whileHeld(new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem, 1000));
    new JoystickButton(m_primary, XboxController.Button.kA.value)
        .whileHeld(new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem, 2000));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
