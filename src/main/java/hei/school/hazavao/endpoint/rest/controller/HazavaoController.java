package hei.school.hazavao.endpoint.rest.controller;

import hei.school.hazavao.service.HazavaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HazavaoController {
  private final HazavaoService hazavaoService;

  public HazavaoController(HazavaoService hazavaoService) {
    this.hazavaoService = hazavaoService;
  }

  @GetMapping("/hazavao")
  public String hazavao(@RequestParam String teny) {
    try {
      return hazavaoService.getDefinition(teny);
    } catch (Exception e) {
      return "Erreur lors de la génération : " + e.getMessage();
    }
  }
}
