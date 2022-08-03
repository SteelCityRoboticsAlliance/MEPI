// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import frc.robot.commands.ClimberCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.HopperCommand;
import frc.robot.commands.RunIntakeCommand;
import frc.robot.commands.SetShooterSpeedCommand;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.commands.ToggleIntakeCommand;
import frc.robot.commands.TowerDownCommand;
import frc.robot.commands.TowerKickerCommand;
import frc.robot.commands.TowerUpCommand;
// import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsytem;
import frc.robot.subsystems.TowerSubsystem;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.ShooterPIDCommand;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final XboxController m_joystick = new XboxController(0);
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  // private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
  private final ShooterSubsytem m_shooterSubsytem = new ShooterSubsytem();
  private final TowerSubsystem m_towerSubsystem = new TowerSubsystem();
  private final HopperSubsystem m_hopperSubsystem = new HopperSubsystem();


  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final RunIntakeCommand m_intakeInCommand = new RunIntakeCommand(m_intakeSubsystem, 0.75, m_hopperSubsystem);
  private final RunIntakeCommand m_intakeOutCommand = new RunIntakeCommand(m_intakeSubsystem,
  -0.75, m_hopperSubsystem);
  private final RunIntakeCommand m_intakeStopCommand = new RunIntakeCommand(m_intakeSubsystem,
  0, m_hopperSubsystem);
  private final DriveCommand m_driveCommand = new DriveCommand(m_drivetrainSubsystem,
  m_joystick);
  private final ToggleIntakeCommand m_toggleIntakeCommand =
  new ToggleIntakeCommand(m_intakeSubsystem);
  private final TowerUpCommand m_towerUpCommand = new TowerUpCommand(m_towerSubsystem);

  private final TunableNumber m_tunableShooterGoal = new TunableNumber("Shooter Goal", 1000);

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_intakeSubsystem.setDefaultCommand(m_intakeStopCommand);
    m_hopperSubsystem.setDefaultCommand(new HopperCommand(m_hopperSubsystem, 0));
    m_drivetrainSubsystem.setDefaultCommand(m_driveCommand);
    m_drivetrainSubsystem.setDefaultCommand(m_driveCommand);
    // m_climberSubsystem.setDefaultCommand(new ClimberCommand(m_climberSubsystem, 0));
    ShuffleboardTab testCommands = Shuffleboard.getTab("test commands");

    // testCommands.add("ClimbUp", new ClimberCommand(m_climberSubsystem, 0.25));
    // testCommands.add("ClimbDown", new ClimberCommand(m_climberSubsystem, -0.25));
    testCommands.add("IntakeIn", new RunIntakeCommand(m_intakeSubsystem, 0.75, m_hopperSubsystem));
    testCommands.add("IntakeOut", new RunIntakeCommand(m_intakeSubsystem, -0.75, m_hopperSubsystem));
    testCommands.add("Shoot RPM", new ShooterPIDCommand(m_shooterSubsytem, m_tunableShooterGoal.get()));
    testCommands.add("Tower Up", new TowerUpCommand(m_towerSubsystem));
    testCommands.add("Tower Down", new TowerDownCommand(m_towerSubsystem));
    testCommands.add("Tower Kicker", new TowerKickerCommand(m_towerSubsystem));
    testCommands.add("Hopper Up", new HopperCommand(m_hopperSubsystem, 0.5));
    testCommands.add("Hopper Down", new HopperCommand(m_hopperSubsystem, -0.5));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(m_joystick, XboxController.Button.kRightBumper.value)
    .whenHeld(m_intakeInCommand);
    new JoystickButton(m_joystick, XboxController.Button.kLeftBumper.value)
    .whenHeld(m_towerUpCommand);
    new JoystickButton(m_joystick, XboxController.Button.kB.value)
    .whenPressed(m_intakeOutCommand);
    new Button(() -> m_joystick.getLeftTriggerAxis() > 0.5).whileHeld(new TowerKickerCommand(m_towerSubsystem));
    new Button(() -> m_joystick.getRightTriggerAxis() > 0.5).whileHeld(new ShooterPIDCommand(m_shooterSubsytem, m_tunableShooterGoal.get()));
    new JoystickButton(m_joystick, XboxController.Button.kX.value)
    .whenHeld(new SetShooterSpeedCommand(m_shooterSubsytem)).whenReleased(() -> m_shooterSubsytem.setShooterSpeed(0));
    // new JoystickButton(m_joystick, XboxController.Button.kY.value)
    // .whenPressed(new ClimberCommand(m_climberSubsystem, 0.5));
    // new JoystickButton(m_joystick, XboxController.Button.kA.value)
    // .whenPressed(new ClimberCommand(m_climberSubsystem, -0.5));
    new POVButton(m_joystick, 0).whenPressed(new ToggleIntakeCommand(m_intakeSubsystem));
    new POVButton(m_joystick, 180).whileHeld(new TowerDownCommand(m_towerSubsystem));


  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
