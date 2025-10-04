package com.hassuna.tech.htoffice.bill;

import com.hassuna.tech.htoffice.bill.elements.CreateInvoice;
import com.hassuna.tech.htoffice.printing.EInvoiceRequestDto;
import com.hassuna.tech.htoffice.printing.InvoiceDataDto;
import jakarta.servlet.http.HttpServletResponse;
import org.mustangproject.Invoice;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context    ;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class BillService {

    private final TemplateEngine templateEngine;
    private final PrintserverClient printserverClient;

    public BillService(TemplateEngine templateEngine, PrintserverClient printserverClient) {
        this.templateEngine = templateEngine;
        this.printserverClient = printserverClient;
    }

    public String generateBillHtml(Model model, Invoice invoice) {
        model.addAttribute("customerName", invoice.getRecipient().getName());
        model.addAttribute("customerStreet", invoice.getRecipient().getStreet());
        model.addAttribute("customerCity", invoice.getRecipient().getZIP() + " " + invoice.getRecipient().getLocation());
        return renderInvoiceHtml(model);
    }

    public String renderInvoiceHtml(Model model) {
        Context context = new Context();
        context.setVariables(model.asMap());
        return templateEngine.process("template-bill", context);
    }

    public void generateBill(HttpServletResponse response, Model model) throws IOException {
        CreateInvoice createInvoice = new CreateInvoice();
        Invoice invoice = createInvoice.getExample();
        String html = generateBillHtml(model, invoice);
    }
}
