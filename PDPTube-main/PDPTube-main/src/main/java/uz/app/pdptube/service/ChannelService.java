package uz.app.pdptube.service;

import uz.app.pdptube.entity.Channel;
import uz.app.pdptube.dto.ChannelDTO;

import java.util.List;

public interface ChannelService {
    Channel createChannel(ChannelDTO channelDTO);
    Channel updateChannel(Integer channelId, ChannelDTO channelDTO);
    void deleteChannel(Integer channelId);
    List<Channel> getAllChannels();
    Channel getChannelById(Integer channelId);
}
