package controller;

import model.MarsPhoto;
import service.NasaApiService;

import java.time.LocalDate;
import java.util.List;

public class NasaApiViewerController {
    private final NasaApiService apiService;

    public NasaApiViewerController(NasaApiService apiService) {
        this.apiService = apiService;
    }

    public List<MarsPhoto> fetchPhotos(String rover, int sol, String camera, LocalDate startDate, LocalDate endDate) throws Exception {
        return apiService.getPhotos(rover, sol, camera, startDate, endDate);
    }
}
