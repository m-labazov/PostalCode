package ua.home.postalcode;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;

@Configuration
public class AppConfiguration {
    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private Integer port;
    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.user}")
    private String user;
    @Value("${spring.data.mongodb.password}")
    private String password;

    @Bean
    public DB db() throws Exception {
        return  new MongoClient(new ServerAddress(host, port),
                                Arrays.asList(MongoCredential.createCredential(user, database, password.toCharArray())))
                .getDB(database);
    }

    @Bean(name = "collectThreadPool")
    public Executor createThreadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        return threadPoolTaskExecutor;
    }
}
