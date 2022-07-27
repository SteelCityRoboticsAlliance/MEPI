// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase {
  private CANSparkMax m_leftLeader = new CANSparkMax(2, MotorType.kBrushless);
  private CANSparkMax m_leftFollower = new CANSparkMax(3, MotorType.kBrushless);
  private CANSparkMax m_rightLeader = new CANSparkMax(4, MotorType.kBrushless);
  private CANSparkMax m_rightFollower = new CANSparkMax(5, MotorType.kBrushless);
  private DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader, m_rightLeader);

  /** Creates a new DrivetrainSubsystem. */
  public DrivetrainSubsystem() {
    m_leftLeader.restoreFactoryDefaults();
    m_leftFollower.restoreFactoryDefaults();
    m_rightLeader.restoreFactoryDefaults();
    m_rightFollower.restoreFactoryDefaults();

    m_leftFollower.follow(m_leftLeader);
    m_rightFollower.follow(m_rightLeader);

    m_leftLeader.setInverted(true);
  }

  public void control(double speed, double rotation) {
    m_drive.curvatureDrive(speed, rotation, Math.abs(speed) < 0.05);
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
