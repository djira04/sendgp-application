package com.sengpgroup.sengp.service.impl;

import com.sengpgroup.sengp.domain.Client;
import com.sengpgroup.sengp.repository.ClientRepository;
import com.sengpgroup.sengp.service.ClientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(
                existingClient -> {
                    if (client.getFirstname() != null) {
                        existingClient.setFirstname(client.getFirstname());
                    }
                    if (client.getLastname() != null) {
                        existingClient.setLastname(client.getLastname());
                    }
                    if (client.getEmail() != null) {
                        existingClient.setEmail(client.getEmail());
                    }
                    if (client.getPassword() != null) {
                        existingClient.setPassword(client.getPassword());
                    }
                    if (client.getTelephone() != null) {
                        existingClient.setTelephone(client.getTelephone());
                    }
                    if (client.getBornDate() != null) {
                        existingClient.setBornDate(client.getBornDate());
                    }
                    if (client.getCin() != null) {
                        existingClient.setCin(client.getCin());
                    }
                    if (client.getPhoto() != null) {
                        existingClient.setPhoto(client.getPhoto());
                    }

                    return existingClient;
                }
            )
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
