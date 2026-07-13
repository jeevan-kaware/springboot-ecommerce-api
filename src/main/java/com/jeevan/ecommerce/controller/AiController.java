package com.jeevan.ecommerce.controller;

import com.jeevan.ecommerce.dto.request.AiRequest;
import com.jeevan.ecommerce.dto.response.AiResponse;
import com.jeevan.ecommerce.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/ask")
    public AiResponse askQuestion(
            @Valid @RequestBody AiRequest request
    ) {

        String answer = aiService.askQuestion(
                request.getQuestion()
        );

        return new AiResponse(answer);
    }
}