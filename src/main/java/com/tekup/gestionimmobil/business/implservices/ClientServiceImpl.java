package com.tekup.gestionimmobil.business.implservices;

import com.tekup.gestionimmobil.dao.entities.Client;
import com.tekup.gestionimmobil.dao.entities.User;
import com.tekup.gestionimmobil.dto.ClientForm;
import com.tekup.gestionimmobil.dao.repositories.UserRepository;
import com.tekup.gestionimmobil.business.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveClient(ClientForm clientForm) {
        Client client = new Client(clientForm.getCin(), clientForm.getNom(), clientForm.getPrenom(), clientForm.getEmail(), clientForm.getPassword());
        userRepository.save(client);
    }

    @Override
    public Client getClientByCin(Long cin) {
        User user = userRepository.findById(cin).orElse(null);
        if (user instanceof Client) {
            return (Client) user;
        }
        return null;
    }

    @Override
    public void updateClient(Long cin, ClientForm clientForm) {
        Client client = getClientByCin(cin);
        if (client != null) {
            client.setNom(clientForm.getNom());
            client.setPrenom(clientForm.getPrenom());
            client.setEmail(clientForm.getEmail());
            client.setPassword(clientForm.getPassword());
            userRepository.save(client);
        }
    }

    @Override
    public void deleteClient(Long cin) {
        userRepository.deleteById(cin);
    }

    @Override
    public List<Client> getAllClients() {
        return userRepository.findByRole("client").stream()
                .filter(user -> user instanceof Client)
                .map(user -> (Client) user)
                .collect(Collectors.toList());
    }
}