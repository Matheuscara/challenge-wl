package comspringboot.challengewl.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String road;

    private String neighborhood;

    private Number zip_code;

    private String city;

    private String state;

    private String country;

    private String number;

    private String complement;
}
