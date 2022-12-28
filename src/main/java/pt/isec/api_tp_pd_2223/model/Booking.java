package pt.isec.api_tp_pd_2223.model;

import jakarta.persistence.*;

public class Booking {
    int id;
    String dateTime;
    int paid;
    int idUser;
    int idEspetaculo;
    //List<Seat> seatList;
}
