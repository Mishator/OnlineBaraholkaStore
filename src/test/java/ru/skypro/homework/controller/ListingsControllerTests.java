package ru.skypro.homework.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.listing.CreateOrUpdateListing;
import ru.skypro.homework.dto.listing.ExtendedListingDTO;
import ru.skypro.homework.dto.listing.ListingDTO;
import ru.skypro.homework.dto.listing.ListingsDTO;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.ListingsServiceImpl;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ListingsControllerTests {

    @Mock
    private ListingsServiceImpl listingService;

    @Mock
    private ImageServiceImpl imageService;

    @InjectMocks
    private ListingsController listingsController;

    // Тест метода getAllListings
    @Test
    public void getAllListings_ReturnsListingsDTO() {
        ListingsDTO listingsDTO = new ListingsDTO();
        when(listingService.getAllListings()).thenReturn((List<ListingDTO>) listingsDTO);

        ResponseEntity<ListingsDTO> responseEntity = listingsController.getAllListings();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listingsDTO, responseEntity.getBody());
    }

    // Тест метода addListing
    @Test
    public void addListing_ReturnsListingDTO() throws IOException {
        CreateOrUpdateListing createOrUpdateListing = new CreateOrUpdateListing();
        MultipartFile image = mock(MultipartFile.class);
        Authentication authentication = mock(Authentication.class);
        ListingDTO listingDTO = new ListingDTO();
        when(listingService.addListing(eq(createOrUpdateListing), eq(image), eq(authentication))).thenReturn(listingDTO);

        ResponseEntity<ListingDTO> responseEntity = listingsController.addListing(createOrUpdateListing, image, authentication);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listingDTO, responseEntity.getBody());
    }

    // Тест метода getListing
    @Test
    public void getListing_ReturnsExtendedListingDTO() {
        long id = 1L;
        ExtendedListingDTO extendedListingDTO = new ExtendedListingDTO();
        when(listingService.getListing(eq(id))).thenReturn(extendedListingDTO);

        ResponseEntity<ExtendedListingDTO> responseEntity = listingsController.getListing(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(extendedListingDTO, responseEntity.getBody());
    }

    // Тест метода deleteListing
    @Test
    public void deleteListing_ReturnsNoContent() throws AccessDeniedException {
        long id = 1L;
        Authentication authentication = mock(Authentication.class);

        ResponseEntity<?> responseEntity = listingsController.deleteListing(id, authentication);

        verify(listingService).deleteListing(eq(id), eq(authentication));
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void updateListingInformation_ReturnsListingDTO() throws AccessDeniedException {
        long id = 1L;
        CreateOrUpdateListing createOrUpdateListing = new CreateOrUpdateListing();
        Authentication authentication = mock(Authentication.class);
        ListingDTO listingDTO = new ListingDTO();

        when(listingService.updateListing(eq(id), eq(createOrUpdateListing), eq(authentication))).thenReturn(listingDTO);

        ResponseEntity<ListingDTO> responseEntity = listingsController.updateListingInformation(id, createOrUpdateListing, authentication);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listingDTO, responseEntity.getBody());
    }

    @Test
    public void getListingsFromAuthorizedUser_ReturnsListingsDTO() {
        Authentication authentication = mock(Authentication.class);
        ListingsDTO listingsDTO = new ListingsDTO();

        when(listingService.getListingsMe(eq(authentication))).thenReturn(listingsDTO);

        ResponseEntity<ListingsDTO> responseEntity = listingsController.getListingsFromAuthorizedUser(authentication);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listingsDTO, responseEntity.getBody());
    }

    @Test
    public void updateImage_ReturnsOk() throws IOException {
        long id = 1L;
        MultipartFile image = mock(MultipartFile.class);
        Authentication authentication = mock(Authentication.class);

        ResponseEntity<?> responseEntity = listingsController.updateImage(id, image, authentication);

        verify(listingService).updateListingImage(eq(id), eq(image), eq(authentication));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

 //   @Test
 //   public void getListingsImage_ReturnsImage() {
 //       long id = 1L;
 //       byte[] imageData = new byte[10];
 //       when(imageService.getImage(eq(id))).thenReturn(new Image(imageData));

 //       ResponseEntity<byte[]> responseEntity = listingsController.getListingsImage(id);

 //       assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 //       assertArrayEquals(imageData, responseEntity.getBody());
 //   }


}
