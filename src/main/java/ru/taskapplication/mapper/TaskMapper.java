package ru.taskapplication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.taskapplication.dto.task.TaskOutDto;
import ru.taskapplication.entity.TaskEntity;

import java.util.List;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskOutDto toMonoDto(Mono<TaskEntity> task);

    TaskEntity toEntity(TaskOutDto taskOutDto);

    TaskEntity toDto(Mono<TaskOutDto> byId);

    List<TaskOutDto> toListDto(List<TaskEntity> taskEntities);

    TaskEntity toEntityFromFlux(Flux<TaskEntity> taskEntities);

    List<TaskOutDto> toListDtoFromFlux(Flux<TaskEntity> taskEntities);
}
