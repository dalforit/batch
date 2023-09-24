package com.ip.manager.entity;

import java.time.LocalDateTime;
import com.ip.manager.utils.enums.SeverityEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="T_ATTACK")
@Data
public class AttackEntity {

	@Id
	@Column(name= "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name= "label", length = 15)
	private String label; 
	
	@Column(name= "date")
	private LocalDateTime date; 
	
	@Column(name= "severity")
	@Enumerated(EnumType.STRING)
	private SeverityEnum severity; 
	
	@ManyToOne
    @JoinColumn(name = "idIPV4")
	private AdresseIPEntity adresseIP;  

	
}
