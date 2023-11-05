// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShooterLookupTable;

public class ShooterSubsytem extends SubsystemBase {
    private static final double ENCODER_REDUCTION = 0.5;

    /**
     * Creates a new Shooter.
     */
    private final CANSparkMax m_shooterMotor;

    private final CANSparkMax m_hoodMotor;

    private final ShooterLookupTable m_shooterLookupTable;
    private final RelativeEncoder m_encoder;
    private final SparkMaxPIDController m_pidController;
    private final PidProperty m_pidProperty;
    private final GosDoubleProperty m_tunableAllowableError =
            new GosDoubleProperty(false, "Shooter(AllowableError))", 50);
    public static final double FENDER_RPM = 1500;

    public ShooterSubsytem() {
        m_shooterMotor = new CANSparkMax(Constants.SHOOTER_SPARK, MotorType.kBrushless);
        m_hoodMotor = new CANSparkMax(Constants.SHOOTER_HOOD_SPARK, MotorType.kBrushless);
        m_shooterMotor.setSmartCurrentLimit(50);
        m_hoodMotor.setSmartCurrentLimit(30);
        m_shooterLookupTable = new ShooterLookupTable();
        m_encoder = m_shooterMotor.getEncoder();
        m_pidController = m_shooterMotor.getPIDController();
        m_pidProperty = new RevPidPropertyBuilder("Shooter", false, m_pidController, 0)
                .addP(0)
                .addI(0)
                .addD(0)
                .addFF(0.00045)
                .build();

        m_shooterMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_shooterMotor.restoreFactoryDefaults();
        m_shooterMotor.setInverted(true);
        m_shooterMotor.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        m_pidProperty.updateIfChanged();
        SmartDashboard.putNumber("shooterRpm", getRPM());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void setPidRpm(double rpm) {
        m_pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public boolean checkAtSpeed(double goal) {
        double error = Math.abs(goal - getRPM());
        return m_tunableAllowableError.getValue() > error;
    }

    public double getRPM() {
        return m_encoder.getVelocity() * ENCODER_REDUCTION;
    }

    public void shootFromDistance(double distance) {
        setPidRpm(m_shooterLookupTable.getRpmTable(distance));
    }

    public void setShooterSpeed(double speed) {
        m_shooterMotor.set(speed);
    }
}
