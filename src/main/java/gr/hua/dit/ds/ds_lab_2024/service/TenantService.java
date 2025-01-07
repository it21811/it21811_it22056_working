package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Tenant;
import gr.hua.dit.ds.ds_lab_2024.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    @Autowired
    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(Integer id) {
        return tenantRepository.findById(id).orElse(null);
    }

    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant updateTenant(Integer id, Tenant tenant) {
        tenant.setId(id);
        return tenantRepository.save(tenant);
    }

    public void deleteTenant(Integer id) {
        tenantRepository.deleteById(id);
    }
}
