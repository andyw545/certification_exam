package com.andy.examapp.backend.web;

import com.andy.examapp.backend.domain.Participant;
import com.andy.examapp.backend.service.ParticipantService;
import com.andy.examapp.backend.web.dto.ParticipantDto;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        participantService.delete(id);
    }
}
