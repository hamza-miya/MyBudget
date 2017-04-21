package Project.Controller;


import Project.Model.Compte;
import Project.Model.Mouvement;
import Project.Model.User;
import Project.Service.CompteService;
import Project.Service.MouvementService;
import Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@SessionAttributes(value = "User", types = { User.class })
public class MouvementController {

    @Autowired
    private MouvementService mouvementService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompteService compteService;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard")
    public String dashboard(ModelMap modelMap, HttpSession httpSession) {
        if (httpSession.getAttribute("userSession") == null) {
            return "redirect:/";
        }
        //Get the list of mouvement
        long idUserSession = (Long) httpSession.getAttribute("userSession");
        User user = userService.getById(idUserSession, true);
        //Sending the list of mouvement (sorting by signe)
        Compte compte = user.getCompte();
        List<Mouvement> Mouvements = compte.getMouvements();
        Collections.sort(Mouvements, new Comparator<Mouvement>() {
            @Override
            public int compare(Mouvement abc1, Mouvement abc2) {
                //noinspection Since15
                return Boolean.compare(abc2.isSigne(),abc1.isSigne());
            }
        });
        modelMap.addAttribute("Mouvements", Mouvements);
        //send the Depense (Mouvement with signe == false)
        modelMap.addAttribute("Depenses", compteService.getMouvementBy(compte, false));

        float positif = 0;
        float negatif = 0;
        for (int i = 0; i < Mouvements.size(); i++){
            if (Mouvements.get(i).isSigne()){
                positif = positif+Mouvements.get(i).getMontant();
            }else if (!Mouvements.get(i).isSigne()){
                negatif = negatif+Mouvements.get(i).getMontant();
            }
        }
        float resultat = positif-negatif;
        modelMap.addAttribute("SommeRecettes", round(positif, 2));
        modelMap.addAttribute("SommeDepenses", round(negatif, 2));
        modelMap.addAttribute("Solde", round(resultat, 2));
        httpSession.setAttribute("EpargneP", round(resultat, 2));

        return "Main/dashboard";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ModifyMouvement", produces = "application/json")
    @ResponseBody()
    public HashMap<String, Object> ModifyMouvement(@RequestParam(value = "id") long id,
                                                   @RequestParam(value = "montant") float montant,
                                                   @RequestParam(value = "label") String label,
                                                   ModelMap modelMap,
                                                   HttpSession httpSession,
                                                   HttpServletResponse response) {
        // Response
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        if (httpSession.getAttribute("userSession") == null) {
            hashMap.put("Code", "REDIRECT");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return hashMap;
        }
        try {
            //Set the new entries
            Mouvement mouvement = mouvementService.getById(id);
            mouvement.setMontant(montant);
            mouvement.setLabel(label);

            mouvementService.update(mouvement);

            hashMap.put("Code", "SUCCESS");
        }catch (Exception e){
            hashMap.put("Code", "ERROR");
        }

        return hashMap;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteMouvement", produces = "application/json")
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

        Mouvement mouvement = mouvementService.getById(id);
        mouvementService.delete(mouvement);

        return hashMap;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addMouvement", produces = "application/json")
    @ResponseBody()
    public HashMap<String, Object> addMouvement(@RequestParam(value = "montant") float montant,
                                                @RequestParam(value = "label") String label,
                                                @RequestParam(value = "signe") boolean signe,
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
            Mouvement mouvement = new Mouvement(montant, label, signe);
            mouvement.setCompte(user.getCompte());
            mouvementService.add(mouvement);
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
