package comspringboot.challengewl.models;

import comspringboot.challengewl.enums.TypeUsers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "`user`")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(updatable = false)
    private Date birthDate;

    @Enumerated(EnumType.ORDINAL)
    private TypeUsers typeUser;

    private String password;

    private String cpf;

    private String image;

    private String phoneNumber;
}
