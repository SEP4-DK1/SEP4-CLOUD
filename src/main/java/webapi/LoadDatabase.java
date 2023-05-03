package webapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DataRepository repository){
        return args ->{
            log.info("Preloading: " + repository.save(new Data("25", "45", "40", LocalDateTime.now().toString())));
            log.info("Preloading: " + repository.save(new Data("25", "45", "40", LocalDateTime.now().toString())));
        };
    }
}
