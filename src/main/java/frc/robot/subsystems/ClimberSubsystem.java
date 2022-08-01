// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  private CANSparkMax m_leftClimber = new CANSparkMax(10, MotorType.kBrushless);
  private CANSparkMax m_rightClimber = new CANSparkMax(11, MotorType.kBrushless);
  private DigitalInput m_leftLimitSwitch = new DigitalInput(Constants.LEFT_LIMIT_SWITCH);
  private DigitalInput m_rightLimitSwitch = new DigitalInput(Constants.RIGHT_LIMIT_SWITCH);

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    m_leftClimber.restoreFactoryDefaults();
    m_rightClimber.restoreFactoryDefaults();

    m_rightClimber.setInverted(true);

    m_rightClimber.follow(m_leftClimber);
  }

  public void set(double speed) {
    m_leftClimber.set(speed);
  }

  public boolean leftLimitSwitchPress() {
    return m_leftLimitSwitch.get();
  }

  public boolean rightLimitSwitchPress() {
    return m_rightLimitSwitch.get();
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
