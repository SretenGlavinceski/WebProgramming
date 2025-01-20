package mk.ukim.finki.wp.kol2024g1.service.impl;

import mk.ukim.finki.wp.kol2024g1.model.Reservation;
import mk.ukim.finki.wp.kol2024g1.model.RoomType;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidReservationIdException;
import mk.ukim.finki.wp.kol2024g1.repository.ReservationRepository;
import mk.ukim.finki.wp.kol2024g1.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static mk.ukim.finki.wp.kol2024g1.service.specifications.FieldFilterSpecification.filterContainsText;
import static mk.ukim.finki.wp.kol2024g1.service.specifications.FieldFilterSpecification.filterEquals;
import static mk.ukim.finki.wp.kol2024g1.service.specifications.FieldFilterSpecification.filterEqualsV;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final HotelServiceImpl hotelService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, HotelServiceImpl hotelService) {
        this.reservationRepository = reservationRepository;
        this.hotelService = hotelService;
    }

    @Override
    public List<Reservation> listAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(InvalidReservationIdException::new);
    }

    @Override
    public Reservation create(String guestName, LocalDate dateCreated, Integer daysOfStay, RoomType roomType, Long hotelId) {
        Reservation reservation = new Reservation(guestName, dateCreated, daysOfStay, roomType, hotelService.findById(hotelId));
        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public Reservation update(Long id, String guestName, LocalDate dateCreated, Integer daysOfStay, RoomType roomType, Long hotelId) {
        Reservation reservation = this.findById(id);
        reservation.setGuestName(guestName);
        reservation.setDateCreated(dateCreated);
        reservation.setDaysOfStay(daysOfStay);
        reservation.setRoomType(roomType);
        reservation.setHotel(hotelService.findById(hotelId));

        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public Reservation delete(Long id) {
        Reservation reservation = this.findById(id);
        reservationRepository.delete(reservation);
        return reservation;
    }

    @Override
    public Reservation extendStay(Long id) {
        Reservation reservation = this.findById(id);
        reservation.setDaysOfStay(reservation.getDaysOfStay() + 1);
        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public Page<Reservation> findPage(String guestName, RoomType roomType, Long hotel, int pageNum, int pageSize) {
        Specification<Reservation> specification = Specification
                .where(filterContainsText(Reservation.class, "guestName", guestName))
                .and(filterEqualsV(Reservation.class, "roomType", roomType))
                .and(filterEqualsV(Reservation.class, "hotel.id", hotel));

        return this.reservationRepository.findAll(
                specification,
                PageRequest.of(pageNum, pageSize)
        );
    }
    @Override
    public List<Reservation> filterEvents(String guestName, RoomType roomType, Long hotel) {
        List<Reservation> filteredReservations = listAll();
        if (guestName != null && !guestName.isEmpty()) {
            filteredReservations = filteredReservations.stream()
                    .filter(reservation -> reservation.getGuestName().contains(guestName)).toList();
        }

        if (roomType != null) {
            filteredReservations = filteredReservations.stream()
                    .filter(reservation -> reservation.getRoomType().equals(roomType)).toList();
        }

        if (hotel != null) {
            filteredReservations = filteredReservations.stream()
                    .filter(reservation -> reservation.getHotel().equals(hotelService.findById(hotel))).toList();
        }

        return filteredReservations;

    }

}
