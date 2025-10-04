package com.hassuna.tech.htoffice.bill;

import com.hassuna.tech.htoffice.printing.EInvoiceRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PrintserverClient {
    
    private static final Logger logger = LoggerFactory.getLogger(PrintserverClient.class);
    private final WebClient webClient = WebClient.create("http://localhost:8000");

    public Mono<byte[]> createEInvoice(EInvoiceRequestDto requestDto) {
        return webClient.post()
                .uri("/generate-e-invoice-from-html")
                .bodyValue(requestDto)
                .retrieve()
                // *** FEHLERBEHANDLUNG HINZUGEFÜGT ***
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            logger.error("Fehler von Python-Service ({}): {}", clientResponse.statusCode(), errorBody);
                            return Mono.error(new RuntimeException("Validierungsfehler: " + errorBody));
                        })
                )
                .bodyToMono(byte[].class);
    }
}
