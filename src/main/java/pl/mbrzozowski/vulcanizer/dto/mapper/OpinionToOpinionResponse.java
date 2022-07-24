package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.OpinionResponse;
import pl.mbrzozowski.vulcanizer.entity.Opinion;

public class OpinionToOpinionResponse implements Converter<Opinion, OpinionResponse> {

    @Override
    public OpinionResponse convert(Opinion source) {
        return OpinionResponse.builder()
                .id(source.getId())
                .stars(source.getStars())
                .description(source.getDescription())
                .visibility(source.isVisibility())
                .createdTime(source.getCreatedTime())
                .authorName(source.getAuthorName())
                .build();
    }
}
