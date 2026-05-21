package com.javalab.dinosaurpark.zone;

import com.javalab.dinosaurpark.model.Ticket;
import com.javalab.dinosaurpark.model.Tourist;
import com.javalab.dinosaurpark.model.TouristStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArrivalZone implements ParkZone {

    private final String name;
    private final int maxCapacity;
    private final double ticketPrice;

    private final Queue<Tourist> queue;
    private final List<Ticket> soldTickets;

    private long nextTicketId = 1;

    public ArrivalZone(String name,
                       int maxCapacity,
                       double ticketPrice) {

        this.name = name;
        this.maxCapacity = maxCapacity;
        this.ticketPrice = ticketPrice;

        this.queue = new LinkedList<>();
        this.soldTickets = new ArrayList<>();
    }

    public void processBatch(int batchSize) {

        int processed = 0;

        while (!queue.isEmpty() && processed < batchSize) {

            Tourist tourist = queue.poll();

            tourist.setStatus(TouristStatus.IN_PARK);

            Ticket ticket = new Ticket(
                    nextTicketId++,
                    tourist.getId(),
                    ticketPrice,
                    "GENERAL",
                    LocalDateTime.now()
            );

            soldTickets.add(ticket);

            tourist.spend(ticketPrice);

            processed++;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasCapacity() {
        return queue.size() < maxCapacity;
    }

    @Override
    public int getCurrentOccupancy() {
        return queue.size();
    }

    @Override
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void enter(Tourist tourist) {
        if (hasCapacity()) {
            queue.offer(tourist);
        }
    }

    @Override
    public void exit(Tourist tourist) {
        queue.remove(tourist);
    }

    public List<Ticket> getSoldTickets() {
        return soldTickets;
    }
}