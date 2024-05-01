package ru.taskapplication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.taskapplication.dto.user.UserOutDto;
import ru.taskapplication.entity.TaskEntity;
import ru.taskapplication.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserOutDto toDto(UserEntity userEntity);

    UserEntity toEntity(UserOutDto userDto);

    List<UserOutDto> toDto(List<UserEntity> userEntities);

    List<UserEntity> toEntity(List<UserOutDto> userDtos);
}
