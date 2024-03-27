package com.quant_socket.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface SG_controller {
    @GetMapping("")
    ModelAndView index(ModelAndView mv);
    @GetMapping("/edit")
    ModelAndView update(ModelAndView mv, @RequestParam(name = "no") Long no);
    @GetMapping("/write")
    ModelAndView insert(ModelAndView mv);
    @GetMapping("/view")
    ModelAndView view(ModelAndView mv, @RequestParam(name = "no") Long no);
    @PostMapping("/process")
    ModelAndView process(RedirectAttributes ra, ModelAndView mv, @RequestParam(name = "cmd") String cmd);
    @PostMapping("/ajax")
    @ResponseBody
    Object ajax(@RequestParam(name = "cmd") String cmd);
}
