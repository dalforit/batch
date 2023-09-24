package com.ip.manager.batch;

import java.util.Optional;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ip.manager.entity.AdresseIPEntity;
import com.ip.manager.entity.AttackEntity;
import com.ip.manager.mapper.AdresseIPMapper;
import com.ip.manager.mapper.AttackMapper;
import com.ip.manager.repository.AdresseIPRepository;
import com.ip.manager.repository.AttackRepository;
import com.ip.manager.utils.enums.StatusEnum;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class AttackItemWriter implements ItemWriter<AttackEntity> {

    @Autowired
    private AdresseIPRepository adresseIPRepository;
    
    @Autowired
    private AttackRepository attackRepository;
    
    @Autowired
    private AttackMapper attackMapper;

	@Override
	public void write(Chunk<? extends AttackEntity> chunk) throws Exception {
		for (AttackEntity attackEntity : chunk) {
			/*
			System.out.println("> > > Writer " + attackEntity.getLabel());
			System.out.println("> Writer " + attackEntity.getDate());
			System.out.println("> Writer " + attackEntity.getSeverity());
			System.out.println("> Writer " + attackEntity.getAdresseIP());
			*/
			this.save(attackEntity);
		}
	}

	private void save(AttackEntity e) {
		String ipV4 = e.getAdresseIP().getValueIPV4();
		Optional<AdresseIPEntity> adresseIPEntityInDBOptional = this.adresseIPRepository.findById(ipV4);
		if (adresseIPEntityInDBOptional.isPresent()) {
			log.warn("L'adresse IP {} est déja répertoriée dans le système.", ipV4);
			AdresseIPEntity adresseIPEntityInDB = adresseIPEntityInDBOptional.get();
			Integer nbAttacks = adresseIPEntityInDB.getNbAttacks();
			if (nbAttacks != null) {
				int incrementedValue = nbAttacks.intValue() + 1;
				nbAttacks = Integer.valueOf(incrementedValue);
			} else {
				nbAttacks = 1;
			}
			adresseIPEntityInDB.setNbAttacks(nbAttacks);
			adresseIPEntityInDB.setStatus(this.determinateStatus(adresseIPEntityInDB.getNbAttacks()));
			this.adresseIPRepository.save(adresseIPEntityInDB);
			e.setAdresseIP(adresseIPEntityInDB);
			this.attackRepository.save(e);
		} else {
			log.warn("L'adresse IP {} est identifiée pour la première fois, lancement de la sauvgarde dans le système.", ipV4);
			AdresseIPEntity adresseIPEntityToSave = e.getAdresseIP();
			adresseIPEntityToSave.setNbAttacks(1);
			adresseIPEntityToSave.setStatus(this.determinateStatus(1));
			this.adresseIPRepository.save(adresseIPEntityToSave);
			e.setAdresseIP(adresseIPEntityToSave);
			this.attackRepository.save(e);
		}

	}

	private StatusEnum determinateStatus(Integer nbAttacks) {
		if (Integer.valueOf(1).equals(nbAttacks)) {
			return StatusEnum.INVESTIGATION;
		} else if (Integer.valueOf(1) < nbAttacks && nbAttacks < Integer.valueOf(10)) {
			return StatusEnum.WARNING;
		}
		return StatusEnum.BLOCKED;
	}
}
