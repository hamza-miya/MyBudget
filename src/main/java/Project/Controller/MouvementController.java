package Project.Controller;


import Project.Model.Compte;
import Project.Model.Mouvement;
import Project.Model.User;
import Project.Service.MouvementService;
import Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@SessionAttributes(value = "User", types = { User.class })
public class MouvementController {

    @Autowired
    private MouvementService mouvementService;

    @Autowired
    private UserService userService;

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
