package Project.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Miya on 27/03/2017.
 */
public class AddMouvementForm {

    @NotNull
    @Getter @Setter private int montant;

    @NotNull
    @Size(min = 2, max = 100)
    @Getter @Setter private String label;

    @NotNull
    @Getter @Setter private boolean signe;

    public AddMouvementForm() {}
}
