package com.tekup.gestionimmobil.business.services;

import com.tekup.gestionimmobil.dao.entities.Client;
import com.tekup.gestionimmobil.dto.ClientForm;

import java.util.List;

public interface ClientService {
    Client getClientById(Long id);
    void saveClient(ClientForm clientForm); // Enregistre un nouveau client
    void updateClient(Long id, ClientForm clientForm); // Mise à jour d'un client existant
    void deleteClient(Long id); // Suppression d'un client
    List<Client> getAllClients(); // Récupère la liste des clients
}
