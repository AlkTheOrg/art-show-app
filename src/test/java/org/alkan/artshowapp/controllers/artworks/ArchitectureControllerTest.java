package org.alkan.artshowapp.controllers.artworks;

import org.alkan.artshowapp.controllers.ControllerExceptionHandler;
import org.alkan.artshowapp.exceptions.NotFoundException;
import org.alkan.artshowapp.models.artworks.Architecture;
import org.alkan.artshowapp.models.artworks.Sculpture;
import org.alkan.artshowapp.services.ArchitectService;
import org.alkan.artshowapp.services.ArchitectureService;
import org.alkan.artshowapp.services.StyleService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ArchitectureControllerTest {

    @Mock ArchitectureService architectureService;
    @Mock StyleService styleService;
    @Mock ArchitectService architectService;
    @Mock Model model;

    ArchitectureController controller;
    MockMvc mockMvc;
    Architecture firstArchitecture;

    final String ARCHITECTURES_PATH = "artworks/architectures";
    final String ARCHITECTURES_URI = "/" + ARCHITECTURES_PATH;
    final String ERROR_PATH_404 = "errors/404error";
    final String ERROR_PATH_400 = "errors/400error";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ArchitectureController(architectureService, styleService, architectService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
        firstArchitecture = new Architecture();
        firstArchitecture.setId(1L);
    }


    @Test
    @DisplayName("Should throw NumberFormatException when the id on the path variable can not be converted to type Long")
    void whenPathVariableIdCanNotBeConvertedToLong_thenResultFails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/asdfghjjk"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name(ERROR_PATH_400));
    }

    @Test
    @DisplayName("Should pass when the id of the sculpture belongs to an existing sculpture")
    void whenGetRequestToASculptureAndValidSculpture_thenCorrectResponse() throws Exception{
        when(architectureService.findById(anyLong())).thenReturn(firstArchitecture);

        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(view().name(ARCHITECTURES_PATH + "/show"))
                .andExpect(model().attributeExists("architecture"));
    }

    @Test
    @DisplayName("Should throw exception when the id of the sculpture doesn't belong to an existing sculpture")
    void whenGetRequestToASculptureAndInvalidSculpture_thenResponseFail() throws Exception {
        when(architectureService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/123"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }

    @Test
    @DisplayName("Should return a model with an empty painting obj and a view of new.html of sculptures.")
    void whenGetRequestToNewSculptures_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("architecture"))
                .andExpect(view().name(ARCHITECTURES_PATH + "/new"));
    }

    @Test
    void whenPostRequestTONewSculpturesAndValidSculpture_thenCorrectResponse() throws Exception {
        Architecture newArchitecture = new Architecture();
        newArchitecture.setId(1L);

        when(architectureService.save(any())).thenReturn(newArchitecture);

        mockMvc.perform(MockMvcRequestBuilders.post(ARCHITECTURES_URI)
                .param("id", "")
                .param("name", "architecture Name")
                .param("width", "12")
                .param("height", "12")
                .param("length", "12")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("architecture"))
                .andExpect(view().name("redirect:" + ARCHITECTURES_URI + "/1"));
    }

    @Test
    void whenPostRequestToNewSculpturesAndInvalidSculpture_thenResponseFail() throws Exception {
        when(architectureService.save(any())).thenReturn(firstArchitecture);

        mockMvc.perform((MockMvcRequestBuilders.post(ARCHITECTURES_URI))
                .param("id", "")
                .param("name", "name")
                .param("widht", "-12"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("architecture"))
                .andExpect(view().name(ARCHITECTURES_PATH + "/new"));
    }

    @Test
    void whenGetRequestToUpdateSculpturesAndValidSculpture_thenCorrectResponse() throws Exception {
        when(architectureService.findById(anyLong())).thenReturn(firstArchitecture);

        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("architecture"))
                .andExpect(view().name(ARCHITECTURES_PATH + "/update"));
    }

    @Test
    void whenGetRequestToUpdateSculpturesAndInvalidSculpture_thenResponseFail() throws Exception {
        when(architectureService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/update/999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }

    @Test
    void whenPostRequestToUpdateSculpturesAndValidSculpture_thenCorrectResponse() throws Exception {
        when(architectureService.save(any())).thenReturn(firstArchitecture);

        mockMvc.perform(MockMvcRequestBuilders.post(ARCHITECTURES_URI + "/update/1")
                .param("id", "")
                .param("name", "some nameee")
                .param("width", "12")
                .param("height", "12")
                .param("length", "12"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + ARCHITECTURES_URI + "/1"));
    }

    @Test
    void whenPostRequestToUpdatePaintingsAndInvalidPainting_thenResponseFail() throws Exception{
        when(architectureService.save(any())).thenReturn(firstArchitecture);

        mockMvc.perform(MockMvcRequestBuilders.post(ARCHITECTURES_URI + "/update/1")
                .param("id", "")
                .param("name", "")
                .param("width", "12"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("architecture"))
                .andExpect(view().name(ARCHITECTURES_PATH + "/update"));
    }

    @Test
    void whenGetRequestToDeletePaintingsAndValidPainting_thenCorrectResponse() throws Exception{
        when(architectureService.findById(anyLong())).thenReturn(firstArchitecture);

        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + ARCHITECTURES_URI));

        // verifying that the delete operation is performed
        verify(architectureService, times(1)).delete(any());
    }

    @Test
    void whenGetRequestToDeletePaintingsAndInvalidPainting_thenResponseFail() throws Exception{
        when(architectureService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(ARCHITECTURES_URI + "/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }
}