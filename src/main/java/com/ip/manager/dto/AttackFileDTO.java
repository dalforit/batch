package com.ip.manager.dto;

import java.time.LocalDateTime;
import com.ip.manager.utils.enums.SeverityEnum;

import lombok.Data;

@Data
public class AttackFileDTO {
	
	private String label; 
	
	private String date; 

	private String severity; 

	private String adresseIPV4; 
	
}
