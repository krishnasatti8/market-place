package com.krishna.marketplace.services.admin;

import com.krishna.marketplace.dto.FAQDto;

public interface AdminFAQService {
public FAQDto postFAQ(Long productId, FAQDto faqDto);
}
