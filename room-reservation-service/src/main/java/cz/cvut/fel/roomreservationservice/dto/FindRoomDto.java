package cz.cvut.fel.roomreservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindRoomDto {

    private Date onDate;

    private String city;

    private int capacity;

}
