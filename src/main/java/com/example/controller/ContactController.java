package com.example.controller;

import com.example.model.Contact;
import com.example.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService service;

    @GetMapping("/")
    public String getContacts(Model model) {
        model.addAttribute("contacts", service.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String getContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact-form";
    }

    @GetMapping("/{id}/edit")
    public String editContact(@PathVariable long id,  Model model) {
        model.addAttribute("contact", service.findById(id));
        return "contact-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteContact(@PathVariable long id) {
        service.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/")
    public String saveContact(@ModelAttribute Contact contact) {
        service.save(contact);
        return "redirect:/";
    }
}
