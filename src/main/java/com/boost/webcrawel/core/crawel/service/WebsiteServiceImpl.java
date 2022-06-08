package com.boost.webcrawel.core.crawel.service;

import com.boost.webcrawel.core.commons.exception.LogicalException;
import com.boost.webcrawel.core.crawel.entity.Website;
import com.boost.webcrawel.core.crawel.repo.WebsiteRepo;
import com.boost.webcrawel.utils.CrawelingUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.boost.webcrawel.core.commons.exception.ServerError.*;

@Service
public class WebsiteServiceImpl implements WebsiteService {

    private final WebsiteRepo websiteRepo;

    public WebsiteServiceImpl(WebsiteRepo websiteRepo) {
        this.websiteRepo = websiteRepo;
    }

    @Override
    public Website getByUrl(String link) {
        Optional<Website> websiteOptional = websiteRepo.findByUrl(link);
        if (websiteOptional.isEmpty()) {
            throw new LogicalException(UNREGISTERED_URL);
        }
        return websiteOptional.get();
    }

    @Override
    public void validateWebsiteDuplication(String link) {
        String hostUrl = CrawelingUtils.getHostUrl(link);
        Boolean isExisted = websiteRepo.existsByUrl(hostUrl);
        if (isExisted) {
            throw new LogicalException(REGISTERED_BEFORE_URL);
        }
    }

    @Override
    public Website createNewWebsiteInCaseOfNotExisted(String link) {
        Optional<Website> websiteOptional = websiteRepo.findByUrl(link);
        if (websiteOptional.isPresent()) {
            return websiteOptional.get();
        }

        Website website = new Website();
        website.setUrl(link);
        return websiteRepo.saveAndFlush(website);
    }

    @Override
    public Website getById(Long id) {
        Optional<Website> websiteOptional = websiteRepo.findById(id);

        if (websiteOptional.isEmpty()) {
            throw new LogicalException(UN_EXISTED_WEBSITE);
        }

        return websiteOptional.get();
    }
}
