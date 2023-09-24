package com.ip.manager.batch;

import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.ip.manager.dto.AttackFileDTO;
import com.ip.manager.entity.AttackEntity;

@Configuration

public class DeveresementConfiguration {

	@Autowired
	private ItemReader<AttackFileDTO> attackItemReader; 
	
	@Autowired
	private ItemProcessor<AttackFileDTO, AttackEntity> attackItemProcessor; 
	
	@Autowired
	private ItemWriter<AttackEntity> attackItemWriter; 
	
    @Bean(name = "jobDeverssement")
    public Job jobDeverssement(JobRepository jobRepository, 
    		@Autowired @Qualifier("StepDeversement") Step StepDeversement){
        return new JobBuilder("deversement",jobRepository)
                .start(StepDeversement)
                .incrementer(new RunIdIncrementer())
                .build();
     }
    
    @Bean(name = "StepDeversement")
    public Step StepDeversement(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) throws IOException {
        return new StepBuilder("step1", jobRepository)
                .<AttackFileDTO, AttackEntity>chunk(1000,platformTransactionManager)
                .reader(attackItemReader)
                .processor(attackItemProcessor)
                .writer(attackItemWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }
    
    
    @Bean(name = "attackItemReader")
    @StepScope
    public FlatFileItemReader<AttackFileDTO> attackItemReader() 
    {
      FlatFileItemReader<AttackFileDTO> reader = new FlatFileItemReader<AttackFileDTO>();
      reader.setResource(new FileSystemResource("C:\\firewallip\\attack.txt"));
      reader.setLinesToSkip(0);       
      reader.setLineMapper(new DefaultLineMapper<>() {
        {
          setLineTokenizer(new DelimitedLineTokenizer() {
            {
              setDelimiter(";");
              setNames(new String[] { "label", "date", "severity", "adresseIPV4" });
            }
          });
          setFieldSetMapper(new BeanWrapperFieldSetMapper<AttackFileDTO>() {
            {
              setTargetType(AttackFileDTO.class);
            }
          });
        }
      });
      return reader;
    }

    
}
