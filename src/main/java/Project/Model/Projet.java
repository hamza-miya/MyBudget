package Project.Model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Projet d'épargne s'incrémentant chaque mois d'une somme définie
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
    @Getter @Setter private float montant_objectif;

    @Column(name="montant_epargneParMois")
    @Getter @Setter private float montant_epargneParMois;

    @Column(name="montant_acquis")
    @Getter @Setter private float montant_acquis;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="echeance", columnDefinition = "datetime")
    @Getter @Setter private Date echeance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_creation_projet", columnDefinition = "datetime")
    @Getter @Setter private Date date_creation_projet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compte", nullable = false)
    @Getter @Setter private Compte compte;

    public Projet(){
        this.date_creation_projet = new Date();
    }

    public Projet(String label, float montantObj, float montant_epargneParMois){
        this();
        this.label = label;
        this.montant_objectif = montantObj;
        this.montant_epargneParMois = montant_epargneParMois;
    }

}
