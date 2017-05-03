package Project.Controller;

import Project.Form.UserLoginForm;
import Project.Form.UserRegisterForm;
import Project.Model.Compte;
import Project.Service.CompteService;
import Project.Model.Mouvement;
import Project.Model.User;
import Project.Service.MouvementService;
import Project.Service.UserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Ctrl permettant la gestion des session utilisateur, l'inscription, la connexion et les redirections
 */
@Controller
@SessionAttributes(value = "User", types = { User.class })
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompteService compteService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String main(ModelMap modelMap, HttpSession httpSession) {
        if (httpSession.getAttribute("userSession") == null) {
            modelMap.addAttribute("UserLoginForm", new UserLoginForm());
            modelMap.addAttribute("UserRegisterForm", new UserRegisterForm());
            return "Main/connexion";
        }else
            return "redirect:/dashboard";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@ModelAttribute("UserLoginForm") @Valid UserLoginForm userLoginForm,
                        BindingResult result,
                        ModelMap modelMap,
                        HttpSession httpSession)
    {
        //Si la session existe déjà redirection vers le dashboard
        if (httpSession.getAttribute("userSession") != null) {
            return "Main/dashboard";
        }else {
            modelMap.addAttribute("UserRegisterForm", new UserRegisterForm());
            // Vérification Erreurs @Valid
            if (result.hasErrors()) {
                return "Main/connexion";
            }

            try{
                User userByEmail = userService.getByEmail(userLoginForm.getEmailLogin());
                if (userByEmail != null) {
                    //test la correspondance du mot de passe saisi
                    if (userByEmail.getMdp().equals(encodeSHA512(userLoginForm.getMdpLogin()))){
                        // Mise à jour de la session
                        httpSession.setAttribute("userSession", userByEmail.getId());
                        return "redirect:/dashboard";
                    } else { //Si ok redirection vers le dashboard sinon envoi d'un message d'erreur en page de login
                        modelMap.addAttribute("MessageIdMdpInvalid", "Identifiant ou mot de passe incorrect, veuillez réessayer");
                        return "Main/connexion";
                    }
                } else {
                    modelMap.addAttribute("MessageIdMdpInvalid", "Identifiant ou mot de passe incorrect, veuillez réessayer");
                    return "Main/connexion";
                }

            }catch (Exception e){
                System.out.println("Aucun User trouvé pour cet email");
                modelMap.addAttribute("MessageIdMdpInvalid", "Identifiant ou mot de passe incorrect, veuillez réessayer");
                return "Main/connexion";
            }

        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addUser")
    public String addUser(@ModelAttribute("UserRegisterForm") @Valid UserRegisterForm userRegisterForm,
                          BindingResult result,
                          ModelMap modelMap,
                          HttpSession httpSession)
    {
        // Vérification Erreurs @Valid
        modelMap.addAttribute("UserLoginForm", new UserLoginForm());
        if (result.hasErrors()) {
            return "Main/connexion";
        }
        Compte compte = new Compte();
        User user = new User();

        try{
            User userByEmail = userService.getByEmail(userRegisterForm.getEmailRegister());
            if (userByEmail != null) {
                modelMap.addAttribute("MessageUserExist", "Cet email est déjà attribué, veuillez en choisir un autre");
                return "Main/connexion";
            }
        }catch (Exception e){
            System.out.println("Aucun User trouvé pour cet email");
        }

        if (!userRegisterForm.getMdpRegister().equals(userRegisterForm.getMdp2Register())){
            modelMap.addAttribute("MessageMdp", "Les deux mots de passe ne sont pas identiques");
            return "Main/connexion";
        }

        user.setMdp(encodeSHA512(userRegisterForm.getMdpRegister()));
        user.setEmail(userRegisterForm.getEmailRegister());
        user.setFname(userRegisterForm.getFnameRegister());
        user.setLname(userRegisterForm.getLnameRegister());

        compte.setUser(user);
        user.setCompte(compte);
        //saving
        long id = userService.add(user);
        // Mise à jour de la session
        httpSession.setAttribute("userSession", id);
        // Ajouter les éléments à la vue
        modelMap.addAttribute("newAccount", true);

        return "redirect:/";
    }

    private String encodeSHA512(String password) {
        return new ShaPasswordEncoder(512).encodePassword(password, null);
    }

    @RequestMapping( method = RequestMethod.GET , value = "/deleteSession")
    public String deleteSession(HttpSession httpSession) {
        // Suppression de la session utilisateur
        httpSession.removeAttribute("userSession");

        //Detruit toutes les sessions
        httpSession.invalidate();
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String test() {
        //Sand Controller for testing and tools
        User user = userService.getById(1, true);
        User userp = userService.getByEmail("eeee@free.fr");
        System.out.println(user.getCompte().getEpargne_reelle());

        return "Main/home";
    }

}
