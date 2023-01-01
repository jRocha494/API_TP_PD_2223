package pt.isec.api_tp_pd_2223.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reserva")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "data_hora")
    String dateTime;

    @Column(name = "pago")
    int paid;

    @Column(name = "id_utilizador")
    int idUser;

    @Column(name = "id_espetaculo")
    int idShow;
}
