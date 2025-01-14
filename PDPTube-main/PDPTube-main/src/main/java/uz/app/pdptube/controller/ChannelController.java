package uz.app.pdptube.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.pdptube.dto.ChannelDTO;
import uz.app.pdptube.entity.Channel;
import uz.app.pdptube.service.ChannelService;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    // Kanal yaratish
    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody ChannelDTO channelDTO) {
        Channel channel = channelService.createChannel(channelDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(channel);
    }

    // Kanalni yangilash
    @PutMapping("/{channelId}")
    public ResponseEntity<Channel> updateChannel(@PathVariable Integer channelId, @RequestBody ChannelDTO channelDTO) {
        Channel updatedChannel = channelService.updateChannel(channelId, channelDTO);
        return ResponseEntity.ok(updatedChannel);
    }

    // Kanalni o'chirish
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Integer channelId) {
        channelService.deleteChannel(channelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Barcha kanallarni olish
    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels() {
        List<Channel> channels = channelService.getAllChannels();
        return ResponseEntity.ok(channels);
    }

    // Kanalni ID bo'yicha olish
    @GetMapping("/{channelId}")
    public ResponseEntity<Channel> getChannelById(@PathVariable Integer channelId) {
        Channel channel = channelService.getChannelById(channelId);
        return ResponseEntity.ok(channel);
    }
}
