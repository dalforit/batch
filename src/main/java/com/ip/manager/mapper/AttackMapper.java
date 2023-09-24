package com.ip.manager.mapper;

import org.mapstruct.Mapper;

import com.ip.manager.dto.AttackFileDTO;
import com.ip.manager.entity.AttackEntity;

@Mapper(componentModel = "spring")
public interface AttackMapper {
	
	AttackEntity dtoToEntity(AttackFileDTO dto); 

}
