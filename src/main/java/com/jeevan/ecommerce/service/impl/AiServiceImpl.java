package com.jeevan.ecommerce.service.impl;


import com.jeevan.ecommerce.entity.Product;
import com.jeevan.ecommerce.exception.AiServiceException;
import com.jeevan.ecommerce.repository.ProductRepository;
import com.jeevan.ecommerce.service.AiService;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {


    private final ChatClient chatClient;


    private final ProductRepository productRepository;



    @Override
    public String askQuestion(String question) {



        List<Product> products =
                productRepository.findAllByActiveTrue();

        if (products.isEmpty()) {
            return """
            No products are currently available in our store.

            Please ask the administrator to add products first.
            """;
        }

        StringBuilder productContext =
                new StringBuilder();



        productContext.append("""
                
                Available Products From Store Database:
                
                """);



        for(Product product : products){


            productContext.append("""
                    
                    Product Name : %s
                    Category : %s
                    Brand : %s
                    Price : %s
                    Stock : %s
                    
                    -------------------------
                    
                    """
                    .formatted(
                            product.getName(),

                            product.getCategory()
                                    .getName(),

                            product.getBrand(),

                            product.getPrice(),

                            product.getStock()
                    ));

        }




        String systemPrompt = """

                You are an AI Shopping Assistant
                for an E-Commerce application.


                IMPORTANT RULES:


                1. Recommend ONLY products provided
                   in the database context.


                2. Never create fake products.


                3. Never recommend outside products.


                4. If suitable product is not available,
                   say:
                   
                   "No suitable product is currently
                   available in our store."


                
                RESPONSE FORMAT:


                Do not write one big paragraph.


                Always use:


                ## Product Name

                Price:

                Category:

                Why Recommended:

                • Point 1
                • Point 2
                • Point 3



                For multiple products:


                🥇 Best Choice

                🥈 Second Choice

                🥉 Third Choice



                For comparison:


                Use small markdown table.


                Include:

                Feature
                Product A
                Product B



                Keep response:

                - Short
                - Clean
                - Professional
                - Easy to read


                Maximum 250 words.



                DATABASE PRODUCTS:


                %s


                User Question:


                %s


                Answer according to database only.

                """
                .formatted(
                        productContext,
                        question
                );




        try {

            return chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(question)
                    .call()
                    .content();

        } catch (Exception ex) {

            throw new AiServiceException(
                    "AI service is temporarily unavailable. Please try again later."
            );

        }

    }

}