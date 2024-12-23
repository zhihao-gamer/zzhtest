package xcmg.global.cockpit.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xcmg.global.cockpit.backend.service.UserService;
import xcmg.global.cockpit.backend.vo.param.UserParamVO;

@RestController
@RequestMapping("/api/getfpermisson")
public class FrPermission {

        @Autowired
        private UserService userService;

        @RequestMapping("/hello")
        @ResponseBody
        public String Hello(){
            return "my hello world";
        }

        @RequestMapping("/testDemo")
        @ResponseBody
        public String testDemo(@RequestBody UserParamVO name){
           String ok = userService.insertBaseInfo(name);
           return ok;
        }


}
