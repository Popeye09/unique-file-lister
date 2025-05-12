package hu.smthy.unique_file_lister.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI uniqueFileListerAPIDescription() {
        return new OpenAPI()
                .info(new Info()
                        .title("Unique File Lister API")
                        .version("1.0.0")
                        .description("Recursively traverse the directory and find files with unique base name." +
                                " Return a JSON array of file base names with number of occurrences."
                        )
                        .contact(new Contact()
                                .name("Kovács Áron")
                                .email("kovacs.aron13@gmail.com")
                        )
                );
    }
}
