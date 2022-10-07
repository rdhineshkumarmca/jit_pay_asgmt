package com.asm.jit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.asm.jit.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
