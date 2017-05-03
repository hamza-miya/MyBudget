package Project.Model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Utilisateur de la plateforme
 */
@Entity
@Table(name = "User")
public class User implements Serializable, Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter private long id;

    @Column(name="fname")
    @Size(min = 2, max = 100)
    @Getter @Setter private String fname;

    @Column(name="lname")
    @Size(min = 2, max = 100)
    @Getter @Setter private String lname;

    @Column(name="email")
    @Size(min = 2, max = 100)
    @Getter @Setter private String email;

    @Column(name="mdp")
    @Size(min = 6, max = 255)
    @Getter @Setter private String mdp;

    @OneToOne(cascade={CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "user")
    @Getter @Setter private Compte compte;

    public User() {}

    public User(String nom, String prenom, String email,String mdp) {
        this.lname = nom;
        this.fname = prenom;
        this.email = email;
        this.mdp = mdp;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user", "NotEmpty.user.fname");
    }
}