package com.tekup.gestionimmobil.implservices;

import com.tekup.gestionimmobil.dao.entities.Client;
import com.tekup.gestionimmobil.dto.ClientForm;
import com.tekup.gestionimmobil.repositories.ClientRepository;
import com.tekup.gestionimmobil.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void saveClient(ClientForm clientForm) {
        Client client = new Client(clientForm.getCin(), clientForm.getNom(), clientForm.getPrenom(), clientForm.getEmail(), clientForm.getPassword());
        clientRepository.save(client);
    }

    @Override
    public Client getClientByCin(Long cin) {
        return clientRepository.findById(cin).orElse(null);
    }

    @Override
    public void updateClient(Long cin, ClientForm clientForm) {
        Client client = getClientByCin(cin);
        if (client != null) {
            client.setNom(clientForm.getNom());
            client.setPrenom(clientForm.getPrenom());
            client.setEmail(clientForm.getEmail());
            client.setPassword(clientForm.getPassword());
            clientRepository.save(client);
        }
    }

    @Override
    public void deleteClient(Long cin) {
        clientRepository.deleteById(cin);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}