package project.petpals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class Reader {

        @Autowired
        ObjectMapper mapper;

        public static String readJsonFile(String filePath) throws IOException {
            File resource = new ClassPathResource(filePath).getFile();
            byte[] byteArray = Files.readAllBytes(resource.toPath());
            return new String(byteArray);
        }
}
