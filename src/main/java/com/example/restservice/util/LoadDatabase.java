package com.example.restservice.util;

import com.example.restservice.model.Employee;
import com.example.restservice.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDataBase(EmployeeRepository employeeRepository){
        return args -> {
            logger.info("Preloading " + employeeRepository.save(new Employee("Bilbo Baggins", "burglar")));
            logger.info("Preloading " + employeeRepository.save(new Employee("Frodo Baggins", "thief")));
        };
    }

}
