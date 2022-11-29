package InstagramProject.InstagramProject;

import InstagramProject.InstagramProject.beans.Facades;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class InstagramProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramProjectApplication.class, args);
	}
	@Bean
	public HashMap<Long, Facades> facades(){
		return new HashMap<Long, Facades>();
	}
}
