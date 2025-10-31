package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("accident", new Accident());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", List.of(
                new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        ));
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] rIds = req.getParameterValues("rIds");
        if (rIds != null) {
            Set<Rule> rules = Arrays.stream(rIds)
                    .map(Integer::parseInt)
                    .map(id -> new Rule(id, ""))
                    .collect(Collectors.toSet());
            accident.setRules(rules);
        }
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String edit(@RequestParam("id") int id, Model model) {
        Optional<Accident> accidentOpt = accidentService.getById(id);
        if (accidentOpt.isPresent()) {
            Accident accident = accidentOpt.get();
            model.addAttribute("accident", accident);
            model.addAttribute("types", accidentTypeService.findAll());
            model.addAttribute("rules", List.of(
                    new Rule(1, "Статья. 1"),
                    new Rule(2, "Статья. 2"),
                    new Rule(3, "Статья. 3")
            ));
            return "editAccident";
        }
        model.addAttribute("message", "Инцидент не найден");
        return "errors/404";
    }

    @PostMapping("/editAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/index";
    }
}