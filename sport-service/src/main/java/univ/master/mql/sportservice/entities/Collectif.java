package univ.master.mql.sportservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("C")
public class Collectif extends Sport{
    @JsonIgnore
    @OneToMany(mappedBy = "sport",targetEntity = Group.class)
    private List<Schedule> group_list;
}
