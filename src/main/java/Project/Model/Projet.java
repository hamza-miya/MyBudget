package Project.Model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Miya on 02/02/2017.
 */

@Entity
@Table(name = "Projet")
public class Projet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter private long id;

    @Column(name="label")
    @Size(min = 2, max = 100)
    @Getter @Setter private String label;

    @Column(name="montant_objectif")
    @Getter @Setter private int montant_objectif;

    @Column(name="montant_reelle")
    @Getter @Setter private int montant_reelle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="echeance", columnDefinition = "datetime")
    @Getter @Setter private Date echeance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compte", nullable = false)
    @Getter @Setter private Compte compte;

}
