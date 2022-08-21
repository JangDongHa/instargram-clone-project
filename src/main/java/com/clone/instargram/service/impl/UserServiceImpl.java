package com.clone.instargram.service.impl;

import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.request.RegisterDto;
import com.clone.instargram.dto.request.UpdateUserProfileDto;
import com.clone.instargram.exception.definition.UserExceptionNaming;
import com.clone.instargram.service.UserService;
import com.clone.instargram.service.definition.UserReturnNaming;
import com.clone.instargram.util.AwsS3Connector;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AwsS3Connector awsS3Connector;

    @Override
    @Transactional
    public String register(RegisterDto dto){
        User user = dto.toUser(bCryptPasswordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return "회원가입 완료";
    }

    @Override
    @Transactional
    public String updateProfileImage(String username, UpdateUserProfileDto dto){
        User userPS = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException(UserExceptionNaming.CANNOT_FIND_USERNAME));

        String imageSource = updateFileToS3(dto, userPS);
        userRepository.save(dto.toUser(userPS, imageSource));

        return UserReturnNaming.USER_PROFILE_UPDATE_COMPLETE;
    }

    private String updateFileToS3(UpdateUserProfileDto updateDto, User user){
        String recentImageSource = user.getProfileImage();
        awsS3Connector.deleteFileV1(recentImageSource);
        return awsS3Connector.uploadFileV1(updateDto.getFile());
    }
}
