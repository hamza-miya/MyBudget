package Project.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Compte")
public class Compte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter private long id;

    @Column(name="epargne_potentielle")
    @Getter @Setter private int epargne_potentielle;

    @Column(name="epargne_reelle")
    @Getter @Setter private int epargne_reelle;

    @OneToOne(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    @Getter @Setter private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compte")
    @Getter @Setter private List<Mouvement> mouvements;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compte")
    @Getter @Setter private List<Projet> projets;

}
