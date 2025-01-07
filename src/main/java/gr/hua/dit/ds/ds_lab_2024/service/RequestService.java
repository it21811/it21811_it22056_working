package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Request;
import gr.hua.dit.ds.ds_lab_2024.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request getRequestById(Integer id) {
        return requestRepository.findById(id).orElse(null);
    }

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public Request updateRequest(Integer id, Request request) {
        request.setId(id);
        return requestRepository.save(request);
    }

    public void deleteRequest(Integer id) {
        requestRepository.deleteById(id);
    }
}
