package account.spring.hardware.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "hardware")
@Data
public class Hardware {
    @Id
    private Long device_id;
    private String name;
}