package com.andy.examapp.backend.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andy.examapp.backend.domain.Participant;
import com.andy.examapp.backend.service.ParticipantService;
import com.andy.examapp.backend.web.dto.ParticipantDto;

@RestController
@RequestMapping("/api/participants")
@CrossOrigin
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public List<Participant> getAll() {
        return participantService.getAll();
    }

    @GetMapping("/{id}")
    public Participant getById(@PathVariable Long id) {
        return participantService.getById(id);
    }

    @PostMapping
    public Participant create(@RequestBody ParticipantDto dto) {
        Participant participant = new Participant(dto.getName(), dto.getEmail(), dto.getPhone());
        return participantService.create(participant);
    }

    @PutMapping("/{id}")
    public Participant update(@PathVariable Long id, @RequestBody ParticipantDto dto) {
        Participant updated = new Participant(dto.getName(), dto.getEmail(), dto.getPhone());
        return participantService.update(id, updated);
    }

    @PutMapping("/{id}/toggle-status")
    public Participant toggleStatus(@PathVariable Long id) {
        Participant participant = participantService.getById(id);

        participant.setActive(!participant.isActive()); // flip status

        return participantService.update(id, participant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        participantService.delete(id);
    }
}
