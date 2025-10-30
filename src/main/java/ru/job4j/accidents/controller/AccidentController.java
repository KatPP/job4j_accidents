package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("accident", new Accident());
        model.addAttribute("types", List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед")
        ));
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String edit(@RequestParam("id") int id, Model model) {
        Optional<Accident> accidentOpt = accidentService.getById(id);
        if (accidentOpt.isPresent()) {
            Accident accident = accidentOpt.get();
            model.addAttribute("accident", accident);
            model.addAttribute("types", List.of(
                    new AccidentType(1, "Две машины"),
                    new AccidentType(2, "Машина и человек"),
                    new AccidentType(3, "Машина и велосипед")
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