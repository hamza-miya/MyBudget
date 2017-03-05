package Project.Controller;

import Project.Form.UserLoginForm;
import Project.Form.UserRegisterForm;
import Project.Model.Compte;
import Project.Model.User;
import Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@SessionAttributes(value = "User", types = { User.class })
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String main(ModelMap modelMap, HttpSession httpSession) {
        if (httpSession.getAttribute("userSession") == null) {
            modelMap.addAttribute("UserLoginForm", new UserLoginForm());
            modelMap.addAttribute("UserRegisterForm", new UserRegisterForm());
            return "Main/connexion";
        }else
            return "redirect:/dashboard";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard")
    public String dashboard(ModelMap modelMap, HttpSession httpSession) {
        if (httpSession.getAttribute("userSession") == null) {
            return "redirect:/";
        }else
            return "Main/dashboard";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@ModelAttribute("UserLoginForm") @Valid UserLoginForm userLoginForm, BindingResult result, ModelMap modelMap, HttpSession httpSession) {
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
                    if (userByEmail.getMdp().equals(userLoginForm.getMdpLogin())){
                        // Mise à jour de la session
                        httpSession.setAttribute("userSession", userByEmail);
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
    public String addUser(@ModelAttribute("UserRegisterForm") @Valid UserRegisterForm userRegisterForm, BindingResult result, ModelMap modelMap, HttpSession httpSession) {
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
            System.out.println("aucun User trouvé pour cet email - OK");
        }

        if (!userRegisterForm.getMdpRegister().equals(userRegisterForm.getMdp2Register())){
            modelMap.addAttribute("MessageMdp", "Les deux mots de passe ne sont pas identiques");
            return "Main/connexion";
        }

        user.setMdp(userRegisterForm.getMdpRegister());
        user.setEmail(userRegisterForm.getEmailRegister());
        user.setFname(userRegisterForm.getFnameRegister());
        user.setLname(userRegisterForm.getLnameRegister());

        compte.setUser(user);
        user.setCompte(compte);
        //saving
        userService.add(user);
        // Mise à jour de la session
        httpSession.setAttribute("userSession", user);
        // Ajouter les éléments à la vue
        modelMap.addAttribute("newAccount", true);

        return "redirect:/";
    }

    @RequestMapping( method = RequestMethod.GET , value = "/deleteSession")
    public String deleteSession(HttpSession httpSession) {
        // Suppression de la session utilisateur
        //httpSession.removeAttribute("userSession");

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
