// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AutoAimCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.HopperCommand;
import frc.robot.commands.KickIfShootSetRPMCommand;
import frc.robot.commands.KickIfShooterDistanceGoBrrCommand;
import frc.robot.commands.RunIntakeCommand;
import frc.robot.commands.SetShooterSpeedCommand;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.commands.ToggleIntakeCommand;
import frc.robot.commands.TowerDownCommand;
import frc.robot.commands.TowerKickerCommand;
import frc.robot.commands.TowerUpCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsytem;
import frc.robot.subsystems.TowerSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.TooManyFields")
public class RobotContainer {

    private final XboxController m_driverJoystick = new XboxController(0);
    private final XboxController m_controlJoystick = new XboxController(1);
    // The robot's subsystems and commands are defined here...
    private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
    private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
    // private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
    private final ShooterSubsytem m_shooterSubsystem = new ShooterSubsytem();
    private final TowerSubsystem m_towerSubsystem = new TowerSubsystem();
    private final HopperSubsystem m_hopperSubsystem = new HopperSubsystem();
    private final LimelightSubsystem m_limelightSubsystem = new LimelightSubsystem();
    private final ShooterLookupTable m_shooterLookupTable = new ShooterLookupTable();

    private final RunIntakeCommand m_intakeInCommand =
        new RunIntakeCommand(m_intakeSubsystem, 0.75, m_hopperSubsystem);
    private final RunIntakeCommand m_intakeOutCommand =
        new RunIntakeCommand(m_intakeSubsystem, -0.75, m_hopperSubsystem);
    private final RunIntakeCommand m_intakeStopCommand =
        new RunIntakeCommand(m_intakeSubsystem, 0, m_hopperSubsystem);
    private final DriveCommand m_driveCommand =
        new DriveCommand(m_drivetrainSubsystem, m_driverJoystick);
    private final TowerKickerCommand m_towerKickerCommand = new
        TowerKickerCommand(m_towerSubsystem);
    private final ShooterPIDCommand m_shooterPIDCommand =
        new ShooterPIDCommand(m_shooterSubsystem);

    public RobotContainer() {
        CameraServer.startAutomaticCapture();
        // Configure the button bindings
        configureButtonBindings();
        m_intakeSubsystem.setDefaultCommand(m_intakeStopCommand);
        m_hopperSubsystem.setDefaultCommand(new HopperCommand(m_hopperSubsystem, 0));
        // m_shooterSubsystem.setDefaultCommand(new ShooterPIDCommand(m_shooterSubsystem, 0));
        m_drivetrainSubsystem.setDefaultCommand(m_driveCommand);
        //m_climberSubsystem.setDefaultCommand(new ClimberCommand(m_climberSubsystem, 0));
        // ShuffleboardTab testCommands = Shuffleboard.getTab("test commands");

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
        new JoystickButton(m_driverJoystick, XboxController.Button.kLeftBumper.value)
            .whileTrue(m_intakeInCommand);
        new JoystickButton(m_driverJoystick, XboxController.Button.kRightBumper.value)
            .whileTrue(m_intakeOutCommand);
        new Trigger(() -> m_driverJoystick.getLeftTriggerAxis() > 0.5)
            .whileTrue(new ToggleIntakeCommand(m_intakeSubsystem));
        new Trigger(() -> m_driverJoystick.getRightTriggerAxis() > 0.5)
            .whileTrue(new AutoAimCommand(m_drivetrainSubsystem, m_limelightSubsystem));

        // control joystic
        new Trigger(() -> m_controlJoystick.getLeftY() > 0.75)
            .whileTrue(new TowerUpCommand(m_towerSubsystem));
        new Trigger(() -> m_controlJoystick.getLeftY() < -0.75)
            .whileTrue(new TowerDownCommand(m_towerSubsystem));
        // new Trigger(() -> m_controlJoystick.getRightY() > 0.75)
        //     .whileTrue(new ClimberCommand(m_climberSubsystem, 0.25));
        // new Trigger(() -> m_controlJoystick.getRightY() < -0.75)
        //     .whileTrue(new ClimberCommand(m_climberSubsystem, -0.25));
        new Trigger(() -> m_controlJoystick.getLeftTriggerAxis() > 0.5)
            .whileTrue(new KickIfShootSetRPMCommand(m_shooterSubsystem, m_towerSubsystem)).onFalse(new SetShooterSpeedCommand(m_shooterSubsystem, 0));
        new Trigger(() -> m_controlJoystick.getRightTriggerAxis() > 0.5)
            .whileTrue(new KickIfShooterDistanceGoBrrCommand(m_shooterSubsystem, m_towerSubsystem, m_limelightSubsystem, m_shooterLookupTable)).onFalse(new SetShooterSpeedCommand(m_shooterSubsystem, 0));
        new JoystickButton(m_controlJoystick, XboxController.Button.kLeftBumper.value)
            .whileTrue(m_shooterPIDCommand).onFalse(new
                SetShooterSpeedCommand(m_shooterSubsystem, 0));

        new JoystickButton(m_controlJoystick, XboxController.Button.kX.value)
            .whileTrue(m_towerKickerCommand);

        // new JoystickButton(m_joystick, XboxController.Button.kB.value)
        // .onTrue(m_intakeOutCommand);
        // new Trigger(() -> m_joystick.getLeftTriggerAxis() > 0.5).whileTrue(new
        // TowerKickerCommand(m_towerSubsystem));
        // new Trigger(() -> m_joystick.getRightTriggerAxis() > 0.5).whileTrue(new
        // ShooterPIDCommand(m_shooterSubsytem, m_tunableShooterGoal.get()));
        // new JoystickButton(m_joystick, XboxController.Button.kX.value)
        // .whileTrue(new SetShooterSpeedCommand(m_shooterSubsytem)).onFalse(() ->
        // m_shooterSubsytem.setShooterSpeed(0));
        // new JoystickButton(m_controlJoystick, XboxController.Button.kLeftBumper.value)
        //     .onTrue(new ClimberCommand(m_climberSubsystem, 0.5));
        // new JoystickButton(m_controlJoystick, XboxController.Button.kRightBumper.value)
        //     .onTrue(new ClimberCommand(m_climberSubsystem, -0.5));
        // new POVButton(m_joystick, 0).onTrue(new ToggleIntakeCommand(m_intakeSubsystem));
        // new POVButton(m_joystick, 180).whileTrue(new TowerDownCommand(m_towerSubsystem));

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
