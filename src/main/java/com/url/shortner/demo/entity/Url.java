package com.url.shortner.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url extends AuditableMongo<String> implements Serializable {

    @Id
    private String id;

    @Column(nullable = false, length = 1024)
    private String originalUrl;

    @Column(nullable = false, length = 10)
    private String shortUrl;

    @Column(nullable = false)
    private Instant lastAccessedDate;

    @Column(nullable = false)
    private Integer accessCount;


}