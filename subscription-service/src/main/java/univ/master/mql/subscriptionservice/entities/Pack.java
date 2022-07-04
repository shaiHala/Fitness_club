package univ.master.mql.subscriptionservice.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long id;
    @Column
    private float prix;
    @Column
    private String duration;
    @Column
    private String details;
    @Column
    private String payementType;
    @Column
    private String packageType;
    @Column
    private Boolean status;
    @OneToMany(mappedBy = "pack",targetEntity = PackageSport.class)
    private List<PackageSport> packageSport ;

    @JsonIgnore
    @OneToMany(mappedBy = "pack",targetEntity = Offer.class)
    private List<Offer> offerList;
}
