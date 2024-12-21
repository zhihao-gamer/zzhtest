package xcmg.global.cockpit.backend.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/getfpermisson")
public class FrPermission {

        @RequestMapping("/hello")
        @ResponseBody
        public String Hello(){
            return "my hello world";
        }

}
