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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdShow() {
        return idShow;
    }

    public void setIdShow(int idShow) {
        this.idShow = idShow;
    }
}
