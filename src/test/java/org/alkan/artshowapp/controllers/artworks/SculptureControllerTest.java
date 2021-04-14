package org.alkan.artshowapp.controllers.artworks;

import org.alkan.artshowapp.controllers.ControllerExceptionHandler;
import org.alkan.artshowapp.exceptions.NotFoundException;
import org.alkan.artshowapp.models.artworks.Sculpture;
import org.alkan.artshowapp.services.ArtistService;
import org.alkan.artshowapp.services.MaterialService;
import org.alkan.artshowapp.services.SculptureService;
import org.alkan.artshowapp.services.StyleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.sql.PseudoColumnUsage;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SculptureControllerTest {

    @Mock SculptureService sculptureService;
    @Mock StyleService styleService;
    @Mock MaterialService materialService;
    @Mock ArtistService artistService;
    @Mock Model model;

    SculptureController controller;
    Sculpture firstSculpture;

    MockMvc mockMvc;

    final String SCULPTURES_PATH = "artworks/sculptures";
    final String SCULPTURES_URI = "/" + SCULPTURES_PATH;
    final String ERROR_PATH_404 = "errors/404error";
    final String ERROR_PATH_400 = "errors/400error";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new SculptureController(sculptureService, styleService, materialService, artistService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
        firstSculpture = new Sculpture();
        firstSculpture.setId(1L);
        firstSculpture.setName("first");
    }

    @Test
    @DisplayName("Should return a view name of sculptures>index.html and add all existing sculptures to the model")
    void whenFindAllSculptures_thenCorrectResponse() {
        Set<Sculpture> sculptureSet = new HashSet<>();
        sculptureSet.add(firstSculpture);

        when(sculptureService.findAll()).thenReturn(sculptureSet);
        ArgumentCaptor<Set<Sculpture>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // Verifying the returned view's name
        String viewName = controller.listSculptures(model);
        assertEquals(SCULPTURES_PATH + "/index", viewName);

        // Verifying that the findAll method is called only once
        verify(sculptureService, times(1)).findAll();

        // Verifying that the model adds the attribute with name "sculptures" for once.
        // and the ArgumentCapture object captures the object that is attached to the "sculpture".
        verify(model, times(1)).addAttribute(eq("sculptures"), argumentCaptor.capture());

        Set<Sculpture> setInTheController = argumentCaptor.getValue(); // getting the set that is captured by AC
        assertEquals(1, setInTheController.size()); // Asserting that it only has one object.
    }

    @Test
    @DisplayName("Should throw NumberFormatException when the id on the path variable can not be converted to type Long")
    void whenPathVariableIdCanNotBeConvertedToLong_thenResultFails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(SCULPTURES_URI + "/asdfghjjk"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name(ERROR_PATH_400));
    }

    @Test
    @DisplayName("Should pass when the id of the painting belongs to an existing painting")
    void whenGetRequestToASculptureAndValidSculpture_thenCorrectResponse() throws Exception{
//        when(paintingService.findById(anyLong())).thenReturn(Optional.of(firstPainting));
        when(sculptureService.findById(anyLong())).thenReturn(firstSculpture);

        mockMvc.perform(MockMvcRequestBuilders.get(SCULPTURES_URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(view().name(SCULPTURES_PATH + "/show"))
                .andExpect(model().attributeExists("sculpture"));
    }

    @Test
    @DisplayName("Should throw exception when the id of the sculpture doesn't belong to an existing sculpture")
    void whenGetRequestToASculptureAndInvalidSculpture_thenResponseFail() throws Exception {
        when(sculptureService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(SCULPTURES_URI + "/123"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }

    @Test
    @DisplayName("Should return a model with an empty painting obj and a view of new.html of sculptures.")
    void whenGetRequestToNewSculptures_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/artworks/sculptures/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sculpture"))
                .andExpect(view().name(SCULPTURES_PATH + "/new"));
    }

    @Test
    void whenPostRequestTONewSculpturesAndValidSculpture_thenCorrectResponse() throws Exception {
        Sculpture newSculpture = new Sculpture();
        newSculpture.setId(1L);

        when(sculptureService.save(any())).thenReturn(newSculpture);

        mockMvc.perform(MockMvcRequestBuilders.post(SCULPTURES_URI)
        .param("id", "")
        .param("name", "Sculpture Name")
        .param("weight", "25")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("sculpture"))
                .andExpect(view().name("redirect:" + SCULPTURES_URI + "/1"));
    }

    @Test
    void whenPostRequestToNewSculpturesAndInvalidSculpture_thenResponseFail() throws Exception {
        when(sculptureService.save(any())).thenReturn(firstSculpture);

        mockMvc.perform((MockMvcRequestBuilders.post(SCULPTURES_URI))
        .param("id", "")
        .param("name", "name")
        .param("widht", "-12"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sculpture"))
                .andExpect(view().name(SCULPTURES_PATH + "/new"));
    }

    @Test
    void whenGetRequestToUpdateSculpturesAndValidSculpture_thenCorrectResponse() throws Exception {
        when(sculptureService.findById(anyLong())).thenReturn(firstSculpture);

        mockMvc.perform(MockMvcRequestBuilders.get(SCULPTURES_URI + "/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sculpture"))
                .andExpect(view().name(SCULPTURES_PATH + "/update"));
    }

    @Test
    void whenGetRequestToUpdateSculpturesAndInvalidSculpture_thenResponseFail() throws Exception {
        when(sculptureService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(SCULPTURES_URI + "/update/999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }

    @Test
    void whenPostRequestToUpdateSculpturesAndValidSculpture_thenCorrectResponse() throws Exception {
        when(sculptureService.save(any())).thenReturn(firstSculpture);

        mockMvc.perform(MockMvcRequestBuilders.post(SCULPTURES_URI + "/update/1")
                .param("id", "")
                .param("name", "some nameee")
                .param("weight", "12"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + SCULPTURES_URI + "/1"));
    }

    @Test
    void whenPostRequestToUpdatePaintingsAndInvalidPainting_thenResponseFail() throws Exception{
        when(sculptureService.save(any())).thenReturn(firstSculpture);

        mockMvc.perform(MockMvcRequestBuilders.post(SCULPTURES_URI + "/update/1")
                .param("id", "")
                .param("name", "")
                .param("width", "12"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sculpture"))
                .andExpect(view().name(SCULPTURES_PATH + "/update"));
    }

    @Test
    void whenGetRequestToDeletePaintingsAndValidPainting_thenCorrectResponse() throws Exception{
        when(sculptureService.findById(anyLong())).thenReturn(firstSculpture);

        mockMvc.perform(MockMvcRequestBuilders.get(SCULPTURES_URI + "/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + SCULPTURES_URI));

        // verifying that the delete operation is performed
        verify(sculptureService, times(1)).delete(any());
    }

    @Test
    void whenGetRequestToDeletePaintingsAndInvalidPainting_thenResponseFail() throws Exception{
        when(sculptureService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(SCULPTURES_URI + "/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }
}