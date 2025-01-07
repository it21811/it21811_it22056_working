package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Request;
import gr.hua.dit.ds.ds_lab_2024.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public Request getRequestById(@PathVariable Integer id) {
        return requestService.getRequestById(id);
    }

    @PostMapping
    public Request createRequest(@RequestBody Request request) {
        return requestService.createRequest(request);
    }

    @PutMapping("/{id}")
    public Request updateRequest(@PathVariable Integer id, @RequestBody Request request) {
        return requestService.updateRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable Integer id) {
        requestService.deleteRequest(id);
    }
}
