package Project.Controller;

import Project.Model.Compte;
import Project.Model.Projet;
import Project.Model.User;
import Project.Service.CompteService;
import Project.Service.ProjetService;
import Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@SessionAttributes(value = "User", types = { User.class })
public class ProjectController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjetService projetService;

    @RequestMapping(method = RequestMethod.GET, value = "/epargne")
    public String dashboard(ModelMap modelMap, HttpSession httpSession) {
        if (httpSession.getAttribute("userSession") == null) {
            return "redirect:/";
        }

        //Récupération de la liste des projets
        long idUserSession = (Long) httpSession.getAttribute("userSession");
        User user = userService.getById(idUserSession, true);
        Compte compte = user.getCompte();
        List<Projet> Projets = compte.getProjets();

        modelMap.addAttribute("Projets", Projets);

        return "Main/epargne";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ModifyProjet", produces = "application/json")
    @ResponseBody()
    public HashMap<String, Object> ModifyMouvement(@RequestParam(value = "id") long id,
                                                   @RequestParam(value = "montant_mois") float montant_mois,
                                                   @RequestParam(value = "montant_obj") float montant_obj,
                                                   @RequestParam(value = "montant_acquis") float montant_acquis,
                                                   @RequestParam(value = "label") String label,
                                                   ModelMap modelMap,
                                                   HttpSession httpSession,
                                                   HttpServletResponse response) {
        // Response handler
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        if (httpSession.getAttribute("userSession") == null) {
            hashMap.put("Code", "REDIRECT");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return hashMap;
        }
        try {
            //Set the new entries
            Projet projet = projetService.getById(id);
            projet.setMontant_objectif(montant_obj);
            projet.setMontant_epargneParMois(montant_mois);
            projet.setMontant_acquis(montant_acquis);
            projet.setLabel(label);

            projetService.update(projet);

            hashMap.put("Code", "SUCCESS");
        }catch (Exception e){
            hashMap.put("Code", "ERROR");
        }

        return hashMap;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteProjet", produces = "application/json")
    @ResponseBody()
    public HashMap<String, Object> deleteMouvement(@RequestParam(value = "id") long id,
                                                   ModelMap modelMap,
                                                   HttpSession httpSession,
                                                   HttpServletResponse response)
    {
        // Response
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        if (httpSession.getAttribute("userSession") == null) {
            hashMap.put("Code", "REDIRECT");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return hashMap;
        }

        Projet projet = projetService.getById(id);
        projetService.delete(projet);

        return hashMap;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addProjet", produces = "application/json")
    @ResponseBody()
    public HashMap<String, Object> addMouvement(@RequestParam(value = "montantObj") float montantObj,
                                                @RequestParam(value = "montantEp") float montantEp,
                                                @RequestParam(value = "label") String label,
                                                ModelMap modelMap,
                                                HttpSession httpSession,
                                                HttpServletResponse response)
    {
        // Response
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        if (httpSession.getAttribute("userSession") == null) {
            hashMap.put("Code", "REDIRECT");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return hashMap;
        }

        long idUserSession = (Long) httpSession.getAttribute("userSession");
        User user = userService.getById(idUserSession, true);

        try {
            Projet projet = new Projet(label, montantObj, montantEp);
            projet.setCompte(user.getCompte());
            long projetId = projetService.add(projet);
            hashMap.put("NewProjetId", projetId);

        }catch (Exception e){
            hashMap.put("Code", "ERROR");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return hashMap;
        }

        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        hashMap.put("Code", "SUCCESS");
        return hashMap;
    }


}
