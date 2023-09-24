package com.ip.manager.entity;

import java.util.List;

import com.ip.manager.utils.enums.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="T_ADRESSE_IP")
@Data
public class AdresseIPEntity {
	
	@Id
	@Column(name= "value_ipv4", nullable = false, length = 15)
	private String valueIPV4; 
	
	@Column(name= "status", length = 15)
	@Enumerated(EnumType.STRING)
	private StatusEnum status; 
	
	@OneToMany(mappedBy = "adresseIP")
	private List<AttackEntity> listAttacks; 
	
	@Column(name= "nb_attacks")
	private Integer nbAttacks; 
	

}
