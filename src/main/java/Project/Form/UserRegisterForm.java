package Project.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Miya on 02/03/2017.
 */
public class UserRegisterForm {
    @NotNull
    @Size(min = 2, max = 100)
    @Getter @Setter private String fnameRegister;

    @NotNull
    @Size(min = 2, max = 100)
    @Getter @Setter private String lnameRegister;

    @NotNull
    @Size(min = 2, max = 100)
    @Getter
    @Setter
    private String emailRegister;

    @NotNull
    @Size(min = 6, max = 50)
    @Getter @Setter private String mdpRegister;

    @NotNull
    @Size(min = 6, max = 50)
    @Getter @Setter private String mdp2Register;

    public UserRegisterForm() {}
}
