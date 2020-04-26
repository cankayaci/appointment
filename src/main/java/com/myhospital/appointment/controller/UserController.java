package com.myhospital.appointment.controller;

import com.myhospital.appointment.entity.Doctor;
import com.myhospital.appointment.entity.User;
import com.myhospital.appointment.model.bindModel.doctor.EditDoctorModel;
import com.myhospital.appointment.model.bindModel.user.BaseUserModel;
import com.myhospital.appointment.model.bindModel.user.EditUserModel;
import com.myhospital.appointment.model.bindModel.user.UserAddModel;
import com.myhospital.appointment.service.UserService;
import com.myhospital.appointment.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_SIZE = 5;

    @Autowired
    private UserService userService;

    @GetMapping(value="/user")
    public String userRegisterForm(Model model){
        model.addAttribute("userAddModel", new BaseUserModel());
        return "views/user/user-create";
    }

    @PostMapping(value="/user")
    public String userRegisterSubmit(@Valid @ModelAttribute("userAddModel") BaseUserModel baseUserModel,
    BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("result","error");
            return "views/user/user-create";
        }
        userService.save(baseUserModel);
        model.addAttribute("result","success");
        return "views/user/user-create";
    }
    @GetMapping("/user/search")
    public String searchUsers(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam("username") Optional<String> username,
                              @RequestParam("email") Optional<String> email,
                              @ModelAttribute("result") Optional<String> result){

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() ;
        final int pageSize =  (size.orElse(0) < 1) ? INITIAL_SIZE : size.get();

        String name = username.orElse("");
        String mail = email.orElse("");

        Page<User> pageUser = userService.findByCriteria(name, mail, PageRequest.of(currentPage, pageSize));

        String url = String.format("/user/search?username=%s&email=%s",username,email);

        PageWrapper<User> userPageWrapper = new PageWrapper<>(pageUser,url);

        model.addAttribute("pageUser", pageUser);
        model.addAttribute("page", userPageWrapper);
        model.addAttribute("username",name);
        model.addAttribute("email",mail);
        model.addAttribute("result",result.orElse(""));
        return "views/user/user-search";

    }
    @GetMapping(value="/user/edit/{userId}")
    public String getPatientForEdit(@PathVariable(name = "userId") Integer userId,Model model){

        EditUserModel editUserModel =  userService.findById(userId);
        model.addAttribute("editUserModel",editUserModel);
        return "views/user/user-edit";
    }
    @PostMapping(value="/user/edit/{userId}")
    public String postPatientForEdit(@Valid @ModelAttribute EditUserModel editUserModel
            , BindingResult bindingResult, Model model, @PathVariable(name = "userId") Integer userId, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors() ){
            model.addAttribute("result","error");
            return "views/user/user-edit";
        }
        editUserModel.setId(userId);
        userService.save(editUserModel);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/user/search";

    }
}
