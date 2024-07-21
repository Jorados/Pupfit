package jorados.pupfit;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String test(){
        return "<h1>test</h1>";
    }

    @GetMapping("home")
    public String home(){
        return "<h1>home</h1>";
    }
}
