/*
 * package com.tekup.gestionimmobil.business.implservices;
 * 
 * import com.tekup.gestionimmobil.dao.entities.Client;
 * import com.tekup.gestionimmobil.dto.ClientForm;
 * import com.tekup.gestionimmobil.dao.repositories.ClientRepository;
 * import com.tekup.gestionimmobil.business.services.ClientService;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 * 
 * import java.util.List;
 * 
 * @Service
 * public class ClientServiceImpl implements ClientService {
 * 
 * @Autowired
 * private ClientRepository clientRepository;
 * 
 * @Override
 * public Client getClientById(Long id) {
 * return clientRepository.findById(id).orElse(null);
 * }
 * 
 * @Override
 * public void saveClient(ClientForm clientForm) {
 * Client client = new Client(clientForm.getUsername(), clientForm.getEmail(),
 * clientForm.getPassword());
 * clientRepository.save(client);
 * }
 * 
 * @Override
 * public void updateClient(Long id, ClientForm clientForm) {
 * Client client = clientRepository.findById(id).orElse(null);
 * if (client != null) {
 * client.setUsername(clientForm.getUsername());
 * client.setEmail(clientForm.getEmail());
 * client.setPassword(clientForm.getPassword());
 * clientRepository.save(client);
 * }
 * }
 * 
 * @Override
 * public void deleteClient(Long id) {
 * clientRepository.deleteById(id);
 * }
 * 
 * @Override
 * public List<Client> getAllClients() {
 * return clientRepository.findAll();
 * }
 * }
 */