package com.andy.examapp.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.andy.examapp.backend.domain.Participant;
import com.andy.examapp.backend.repository.ParticipantRepository;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> getAll() {
        return participantRepository.findAll();
    }

    public Participant getById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found: " + id));
    }

    public Participant create(Participant participant) {
        return participantRepository.save(participant);
    }

    public Participant update(Long id, Participant updated) {
        Participant existing = getById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setActive(updated.isActive());
        return participantRepository.save(existing);
    }

    public void delete(Long id) {
        participantRepository.deleteById(id);
    }
}
