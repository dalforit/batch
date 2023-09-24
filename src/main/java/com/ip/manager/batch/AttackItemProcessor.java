package com.ip.manager.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ip.manager.dto.AttackFileDTO;
import com.ip.manager.entity.AdresseIPEntity;
import com.ip.manager.entity.AttackEntity;
import com.ip.manager.mapper.AttackMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class AttackItemProcessor implements ItemProcessor<AttackFileDTO, AttackEntity> {

	@Autowired
	private AttackMapper attackMapper; 
	
	@Override
	public AttackEntity process(AttackFileDTO item) throws Exception {
		System.out.println("> Processor " + item.getLabel());
		AttackEntity entity = this.attackMapper.dtoToEntity(item); 
		AdresseIPEntity adresseIPEntity = new AdresseIPEntity();
		adresseIPEntity.setValueIPV4(item.getAdresseIPV4());
		entity.setAdresseIP(adresseIPEntity);
		System.out.println("> Processor " + entity.getAdresseIP().getValueIPV4());
		return entity;
	}

}
