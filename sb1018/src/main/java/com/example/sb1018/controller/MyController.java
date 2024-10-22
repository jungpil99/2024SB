package com.example.sb1018.controller;

import com.example.sb1018.entity.Emp;
import com.example.sb1018.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyController {

    final EntityService entityService;

    @GetMapping("/")
    public String index(@ModelAttribute Emp emp, Model model) {
        List<Emp> list = entityService.selectAll();
        model.addAttribute("emplist", list);
        return "index";
    }

    @GetMapping("/insert")
    public String newEmp(Model model) {
        Emp emp = new Emp();
        model.addAttribute("formEmp", emp);
        return "newEmp";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("formEmp") Emp emp) {
        entityService.insertEmp(emp);
        return "redirect:/";
    }

    @GetMapping("/delete/{empno}")
    public String delete(@PathVariable Integer empno,Model model) {
        Emp emp = entityService.selectByEmp(empno);
        model.addAttribute("formEmp", emp);
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("formEmp") Emp emp,@RequestParam Integer empno) {
        entityService.deleteEmp(empno);
        return "redirect:/";
    }
}
