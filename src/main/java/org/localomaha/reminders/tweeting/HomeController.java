package org.localomaha.reminders.tweeting;

import org.localomaha.reminders.tweeting.model.TweetsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/add")
    public String addGet(Model model) {
        TweetsDTO tweet = new TweetsDTO();
        model.addAttribute("tweet",tweet);
        setCurrentDate(model);
        return "add";
    }

    /*
    @PostMapping("/add")
    public String addPost(Model model, TweetsDTO tweet, BindingResult bindingResult) {
        setCurrentDate(model);
        model.addAttribute("tweet",tweet);
        return "add";
    }
    */

    private void setCurrentDate(Model model) {
        String currentDate = (new Date()).toString();
        model.addAttribute("currentDate", currentDate);
    }
}
