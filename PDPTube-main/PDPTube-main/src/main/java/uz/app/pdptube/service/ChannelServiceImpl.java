package uz.app.pdptube.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.app.pdptube.entity.Channel;
import uz.app.pdptube.dto.ChannelDTO;
import uz.app.pdptube.entity.User;
import uz.app.pdptube.repository.ChannelRepository;
import uz.app.pdptube.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Channel createChannel(ChannelDTO channelDTO) {
        // Foydalanuvchining emailini olish (SecurityContextHolder yordamida)
        String email = getCurrentUserEmail();
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        Channel channel = Channel.builder()
                .name(channelDTO.getName())
                .description(channelDTO.getDescription())
                .profilePicture(channelDTO.getProfilePicture())
                .owner(owner)
                .build();

        return channelRepository.save(channel);
    }

    @Override
    public Channel updateChannel(Integer channelId, ChannelDTO channelDTO) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Kanal topilmadi"));

        channel.setName(channelDTO.getName());
        channel.setDescription(channelDTO.getDescription());
        channel.setProfilePicture(channelDTO.getProfilePicture());

        return channelRepository.save(channel);
    }

    @Override
    public void deleteChannel(Integer channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Kanal topilmadi"));

        channelRepository.delete(channel);
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    @Override
    public Channel getChannelById(Integer channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Kanal topilmadi"));
    }

    // Foydalanuvchining emailini olish uchun yordamchi metod
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername(); // Spring Securityda username = email
            } else {
                return principal.toString();
            }
        }
        return null; // Foydalanuvchi autentifikatsiyadan o'tmagan bo'lsa
    }
}
