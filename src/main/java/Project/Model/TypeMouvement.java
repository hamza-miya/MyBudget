package Project.Model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Miya on 02/02/2017.
 */

@Entity
@Table(name = "TypeMouvement")
public class TypeMouvement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter private long id;

    @Column(name="label")
    @Size(min = 2, max = 100)
    @Getter @Setter private String label;

    @Column(name="signe")
    @Getter @Setter private boolean signe;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeMouvement")
    @Getter @Setter private List<Mouvement> mouvements;

}
