package Project.Model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Miya on 02/02/2017.
 */

@Entity
@Table(name = "Mouvement")
public class Mouvement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter private long id;

    @Column(name="montant")
    @Getter @Setter private int montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compte", nullable = false)
    @Getter @Setter private Compte compte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_typeMouvement", nullable = false)
    @Getter @Setter private TypeMouvement typeMouvement;

}
