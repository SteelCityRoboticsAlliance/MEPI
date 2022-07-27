// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsytem extends SubsystemBase {
  /** Creates a new Shooter. */
  private final CANSparkMax m_shooterMotor;
  private final RelativeEncoder m_encoder;
  private final SparkMaxPIDController m_PIDController;

  public ShooterSubsytem() {
    m_shooterMotor = new CANSparkMax(Constants.SHOOTER_SPARK, MotorType.kBrushless);
    m_encoder = m_shooterMotor.getEncoder();
    m_PIDController = m_shooterMotor.getPIDController();
    m_shooterMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    m_shooterMotor.restoreFactoryDefaults();

    
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  public void setPidRpm() {
    m_PIDController.setP(0);
    m_PIDController.setD(0);
    m_PIDController.setFF(0);
    // probably change later
  }
}
