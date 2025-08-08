package org.shark.hierarchy.controller;

import java.util.Map;

import org.shark.hierarchy.model.dto.BbsDTO;
import org.shark.hierarchy.model.dto.PageDTO;
import org.shark.hierarchy.service.BbsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequestMapping("/bbs")
@RequiredArgsConstructor
@Controller
public class BbsController {

  private final BbsService bbsService;

  @GetMapping("/list")
  public String list(PageDTO dto
                   , Model model) {
    Map<String, Object> map = bbsService.getBbsList(dto);
    model.addAttribute("bbsList", map.get("bbsList"));
    model.addAttribute("pageDTO", map.get("pageDTO"));
    return "bbs/list";
  }
  
  @PostMapping("/writeBbs")
  public String writeBbs(BbsDTO bbsDTO
                       , Model model
                       , RedirectAttributes redirectAttr) {
    try {
      boolean result = bbsService.addBbs(bbsDTO);
      redirectAttr.addFlashAttribute("msg", result ? "게시글 등록 성공" : "게시글 등록 실패");
      return "redirect:/bbs/list";
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("errorBbs", e.getMessage());
      return "bbs/list";
    }
  }
  
  @PostMapping("/writeReply")
  public String writeReply(BbsDTO bbsDTO
                         , Model model
                         , RedirectAttributes redirectAttr) {
    try {
      boolean result = bbsService.addReply(bbsDTO);
      redirectAttr.addFlashAttribute("msg", result ? "답글 등록 성공" : "답글 등록 실패");
      return "redirect:/bbs/list";
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("errorReply", e.getMessage());
      return "bbs/list";
    }
  }
  
  @GetMapping("/remove")
  public String remove(Integer bbsId
                     , RedirectAttributes redirectAttr) {
    boolean result = bbsService.removeBbs(bbsId);
    redirectAttr.addFlashAttribute("msg", result ? "게시글/답글 삭제 성공" : "게시글/답글 삭제 실패");
    return "redirect:/bbs/list";
  }
  
}
