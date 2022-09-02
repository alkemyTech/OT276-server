package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface SlideControllerMapper extends CommonMapper {

    @Named("slideToSlideResponse")
    SlideResponse slideToSlideResponse(Slide slide);

    @Named("slideListToSlideResponseList")
    @IterableMapping(qualifiedByName = "slideToSlideResponse")
    List<SlideResponse> slideListToSlideResponseList(List<Slide> slides);

}
