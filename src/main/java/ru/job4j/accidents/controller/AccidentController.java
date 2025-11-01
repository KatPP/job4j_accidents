package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("accident", new Accident());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] rIds = req.getParameterValues("rIds");
        if (rIds != null) {
            Set<Integer> ruleIds = Arrays.stream(rIds)
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());

            List<Rule> allRules = ruleService.findByIds(ruleIds);

            if (allRules.size() != ruleIds.size()) {
                Set<Integer> foundIds = allRules.stream().map(Rule::getId).collect(Collectors.toSet());
                Set<Integer> missingIds = ruleIds.stream()
                        .filter(id -> !foundIds.contains(id))
                        .collect(Collectors.toSet());
                throw new RuntimeException("Не найдены статьи с ID: " + missingIds);
            }

            accident.setRules(new HashSet<>(allRules));
        }
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String edit(@RequestParam("id") int id, Model model) {
        Optional<Accident> accidentOpt = accidentService.getById(id);
        if (accidentOpt.isPresent()) {
            model.addAttribute("accident", accidentOpt.get());
            model.addAttribute("types", accidentTypeService.findAll());
            model.addAttribute("rules", ruleService.findAll());
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