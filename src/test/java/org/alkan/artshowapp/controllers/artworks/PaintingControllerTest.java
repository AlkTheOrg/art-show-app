package org.alkan.artshowapp.controllers.artworks;

import org.alkan.artshowapp.controllers.ControllerExceptionHandler;
import org.alkan.artshowapp.exceptions.NotFoundException;
import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.alkan.artshowapp.repositories.artworks.PaintingRepository;
import org.alkan.artshowapp.repositories.people.ArtistRepository;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PaintingControllerTest {

    @Mock PaintingRepository paintingRepository;
    @Mock StyleRepository styleRepository;
    @Mock ArtistRepository artistRepository;
    @Mock Model model;

    PaintingController controller;
    Painting firstPainting;

    MockMvc mockMvc;

    final String PAINTINGS_URI = "/artworks/paintings";
    final String PAINTINGS_PATH = "artworks/paintings";
    final String ERROR_PATH_404 = "errors/404error";
    final String ERROR_PATH_400 = "errors/400error";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new PaintingController(paintingRepository, styleRepository, artistRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
        firstPainting = new Painting(1L, "Painting1", null, 12, 12);
    }

    @Test
    @DisplayName("Should return a view name of paintings>index.html and add all existing paintings to the model")
    void whenGetRequestToAllPaintings_thenCorrectResponse() {
        List<Painting> paintingList = new ArrayList<>();
        paintingList.add(firstPainting);

        when(paintingRepository.findAll()).thenReturn(paintingList);
        ArgumentCaptor<List<Painting>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        // Testing if the returned view name is correct
        String viewName = controller.listPaintings(model);
        assertEquals(PAINTINGS_PATH + "/index", viewName);

        // Verifying that the findAll method is called only once
        verify(paintingRepository, times(1)).findAll();

        // Verifying that the model adds the attribute with name "paintings" for once.
        // and the ArgumentCapture object captures the object that is attached to the "painting".
        verify(model, times(1)).addAttribute(eq("paintings"), argumentCaptor.capture());

        List<Painting> listInTheController = argumentCaptor.getValue(); // getting the list that is captured by AC
        assertEquals(1, listInTheController.size()); // Asserting that it only has one object.
    }

    @Test
    @DisplayName("Should throw NumberFormatException when the id on the path variable can not be converted to type Long")
    void whenPathVariableIdCanNotBeConvertedToLong_thenResultFails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PAINTINGS_URI + "/asdfghjjk"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name(ERROR_PATH_400));
    }

    @Test
    @DisplayName("Should pass when the id of the painting belongs to an existing painting")
    void whenGetRequestToUsersAndValidPainting_thenCorrectResponse() throws Exception{
        when(paintingRepository.findById(anyLong())).thenReturn(Optional.of(firstPainting));

        mockMvc.perform(MockMvcRequestBuilders.get(PAINTINGS_URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(view().name(PAINTINGS_URI + "/show"))
                .andExpect(model().attributeExists("painting"));
    }

    @Test
    @DisplayName("Should throw exception when the id of the painting doesn't belong to an existing painting")
    void whenGetRequestToUsersAndInvalidPainting_thenResponseFail() throws Exception {
        when(paintingRepository.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(PAINTINGS_URI  + "/123"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }

    @Test
    @DisplayName("Should return a model with an empty painting obj and a view of new.html of paintings.")
    void whenGetRequestToNewPaintings_thenCorrectResponse() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/artworks/paintings/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("painting"))
                .andExpect(view().name(PAINTINGS_PATH + "/new"));
    }

    @Test
    void whenPostRequestToNewPaintingsAndValidPainting_thenCorrectResponse() throws Exception{
        Painting newPainting = new Painting();
        newPainting.setId(1L);

        when(paintingRepository.save(any())).thenReturn(newPainting);

        mockMvc.perform(MockMvcRequestBuilders.post(PAINTINGS_URI)
            .param("id", "")
            .param("name", "Painting Name")
            .param("width", "12")
            .param("length", "24")
            )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("painting"))
                .andExpect(view().name("redirect:" + PAINTINGS_URI + "/1"));
    }

    @Test
    void whenPostRequestToNewPaintingsAndInvalidPainting_thenResponseFail() throws Exception{
        when(paintingRepository.save(any())).thenReturn(firstPainting);

        mockMvc.perform(MockMvcRequestBuilders.post(PAINTINGS_URI)
            .param("id", "")
            .param("name", "")
            .param("width", "12")
            .param("length", "24"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("painting"))
                .andExpect(view().name(PAINTINGS_PATH + "/new"));
    }

    @Test
    void whenGetRequestToUpdatePaintingsAndValidPainting_thenCorrectResponse() throws Exception{
        when(paintingRepository.findById(1L)).thenReturn(Optional.of(firstPainting));

        mockMvc.perform(MockMvcRequestBuilders.get(PAINTINGS_URI + "/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name(PAINTINGS_PATH + "/update"))
                .andExpect(model().attributeExists("painting"));
    }

    @Test
    void whenGetRequestToUpdatePaintingsAndInvalidPainting_thenResponseFail() throws Exception {
        when(paintingRepository.findById(999L)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(PAINTINGS_URI + "/update/999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }

    @Test
    void whenPostRequestToUpdatePaintingsAndValidPainting_thenCorrectResponse() throws Exception{
        when(paintingRepository.save(any())).thenReturn(firstPainting);

        mockMvc.perform(MockMvcRequestBuilders.post("/artworks/paintings/update/1")
                .param("id", "")
                .param("name", "some nameee")
                .param("width", "12")
                .param("length", "24"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + PAINTINGS_URI + "/1"));
    }

    @Test
    void whenPostRequestToUpdatePaintingsAndInvalidPainting_thenResponseFail() throws Exception{
        when(paintingRepository.save(any())).thenReturn(firstPainting);

        mockMvc.perform(MockMvcRequestBuilders.post("/artworks/paintings/update/1")
                .param("id", "")
                .param("name", "")
                .param("width", "12")
                .param("length", "24"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("painting"))
                .andExpect(view().name(PAINTINGS_PATH + "/update"));
    }

    @Test
    void whenGetRequestToDeletePaintingsAndValidPainting_thenCorrectResponse() throws Exception{
        when(paintingRepository.findById(anyLong())).thenReturn(Optional.of(firstPainting));

        mockMvc.perform(MockMvcRequestBuilders.get(PAINTINGS_URI + "/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + PAINTINGS_URI));

        // verifying that the delete operation is performed
        verify(paintingRepository, times(1)).delete(any());
    }

    @Test
    void whenGetRequestToDeletePaintingsAndInvalidPainting_thenResponseFail() throws Exception{
        when(paintingRepository.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(PAINTINGS_URI + "/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ERROR_PATH_404));
    }
}