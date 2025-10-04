package com.hassuna.tech.htoffice.bill;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BillController {

  @Value("classpath:fonts/RobotoFlex.ttf")
  private Resource fontResource;

  private final BillService billService;

  public BillController(BillService billService) {
    this.billService = billService;
  }

  /**
   * Get the bill as PDF
   *
   * @param response - response for user
   * @param model - thymeleaf model for filling the html with data
   * @throws IOException
   */
  @GetMapping("/bill")
  public void getBill(HttpServletResponse response, Model model) throws IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=\"Bill.pdf\"");

    billService.generateBill(response, model);
  }
}
