package com.algafoods.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@Embeddable
public class AddressModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "address_cep")
    private String cep;

    @Column(name = "address_publicPlace")
    private String publicPlace;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_district")
    private String district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    private CityModel city;

}
