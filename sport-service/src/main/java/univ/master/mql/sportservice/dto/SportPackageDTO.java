package univ.master.mql.sportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.master.mql.sportservice.entities.Sport;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SportPackageDTO {

    private Long id;

    private Sport sport;

    private Long id_pack;

    private int nb_courses;

}
