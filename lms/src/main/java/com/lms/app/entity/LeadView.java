package com.lms.app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class LeadView implements Serializable {

    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "area")
    private String area;
    @Column(name = "existing_broadband")
    private String existingBroadband;
    @Column(name = "lead_type")
    private String leadType;
    @Column(name = "creared_date")
    private Date date;
    @Column(name = "remarks")
    private String remarks;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by", referencedColumnName = "id", nullable = false)
    private AppUsers appUsers;


}
