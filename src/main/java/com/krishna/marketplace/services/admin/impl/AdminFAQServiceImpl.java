package com.krishna.marketplace.services.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.FAQDto;
import com.krishna.marketplace.model.FAQ;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.repository.FAQRepository;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.services.admin.AdminFAQService;

import java.util.Optional;

@Service
public class AdminFAQServiceImpl implements AdminFAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private ProductRepository productRepository;

    public FAQDto postFAQ(Long productId, FAQDto faqDto) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(product.get());

            return faqRepository.save(faq).getFAQDto();
        } else {
            return null;
        }

    }

}
