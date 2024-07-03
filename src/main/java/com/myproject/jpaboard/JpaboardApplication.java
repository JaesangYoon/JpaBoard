package com.myproject.jpaboard;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class JpaboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaboardApplication.class, args);
    }

    @Configuration
    public static class QuerydslConfig {

        @Bean
        public JPAQueryFactory queryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }
    }

}
