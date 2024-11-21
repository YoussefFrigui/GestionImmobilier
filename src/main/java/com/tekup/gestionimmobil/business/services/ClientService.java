package com.tekup.gestionimmobil.business.services;

import com.tekup.gestionimmobil.dao.entities.Client;
import com.tekup.gestionimmobil.dto.ClientForm;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    void saveClient(ClientForm clientForm);
    Client getClientByCin(Long cin);
    void updateClient(Long cin, ClientForm clientForm);
    void deleteClient(Long cin);
    List<Client> getAllClients();
}