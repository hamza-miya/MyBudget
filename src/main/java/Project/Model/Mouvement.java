package Project.Model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Mouvement :  represente une entré ou une sorti d'argent dans le compte
 */
@Entity
@Table(name = "Mouvement")
public class Mouvement implements Serializable, Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter private long id;

    @Column(name="montant", scale = 10, precision = 2)
    @Getter @Setter private float montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compte", nullable = false)
    @Getter @Setter private Compte compte;

    @Column(name="label")
    @Size(min = 2, max = 100)
    @Getter @Setter private String label;

    @Column(name="signe")
    @Getter @Setter private boolean signe;

    public Mouvement() {}

    public Mouvement(float p_montant, String p_label, boolean p_signe) {
        this.montant = p_montant;
        this.label = p_label;
        this.signe = p_signe;
    }

    @Override
    public boolean supports(Class<?> clazz) { return Mouvement.class.equals(clazz); }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mouvement", "NotEmpty.mouvement.label");
    }
}
