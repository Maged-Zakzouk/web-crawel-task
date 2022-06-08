package com.boost.webcrawel.core.crawel.entity;

import com.boost.webcrawel.core.commons.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "web_page")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WebPage extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "status", nullable = false, length = 50)
    @Convert(converter = DBStatusConverter.class)
    private Status status = Status.PENDING;

    @ManyToOne(targetEntity = Website.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "website_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_category_id"))
    private Website website;

    @OneToMany(targetEntity = WebPageLink.class, mappedBy = "source", fetch = FetchType.LAZY)
    private Set<WebPageLink> webPageLinks = new HashSet<>();

    public WebPage(String url, Website website) {
        this.url = url;
        this.website = website;
    }

    public Boolean isActive() {
        return Status.ACTIVE.equals(status);
    }
}
