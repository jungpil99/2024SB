package com.example.sb1015_2.controller;

import com.example.sb1015_2.entity.MyData;
import com.example.sb1015_2.repository.MyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HeloController {

    final MyDataRepository repository;

    @GetMapping("/")
    public String index(@ModelAttribute("formModel")MyData mydata, Model model) {
        List<MyData> list = repository.findAll();
        model.addAttribute("datalist", list);
        return "index";
    }

    @PostMapping("/")
    public String form(@ModelAttribute("formModel")MyData mydata) {
        repository.save(mydata);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute MyData mydata ,Model model) {
        Optional<MyData> myData1 = repository.findById(id);
        model.addAttribute("formModel", myData1.get());
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute MyData mydata) {
        repository.save(mydata);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @ModelAttribute MyData mydata ,Model model) {
        Optional<MyData> myData2 = repository.findById(id);
        model.addAttribute("formModel", myData2.get());
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute MyData mydata) {
        repository.delete(mydata);
        return "redirect:/";
    }
}
