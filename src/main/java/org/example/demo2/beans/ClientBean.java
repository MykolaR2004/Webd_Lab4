package org.example.demo2.beans;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.example.demo2.dao.ClientDao;
import org.example.demo2.model.Client;
import org.example.demo2.model.DatingTable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class ClientBean implements Serializable {

    @EJB
    ClientDao clientDao;

    private Client client = new Client();

    private int selectedClientId;
    private Date newDate;

    public List<Client> getClients() {
        return clientDao.findAll();
    }

    public Client getClient() {
        return client;
    }

    public Object viewDates(int id) {
        client = clientDao.find(id);
        clientDao.showDates(client);
        return "show_dates";
    }

    public String addDate() {
        Client secondClient = clientDao.find(selectedClientId);
        clientDao.addDate(client, secondClient, new java.sql.Date(newDate.getTime()));
        clientDao.showDates(client);
        return "show_dates";
    }

    public List<Client> getAvailableClients() {
        return clientDao.findAllExcept(client.getId());
    }

    public int getSelectedClientId() {
        return selectedClientId;
    }

    public void setSelectedClientId(int selectedClientId) {
        this.selectedClientId = selectedClientId;
    }

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
    }

    public String deleteDate(int id) {
        clientDao.deleteDate(id);
        clientDao.showDates(client);
        return "show_dates";
    }
}
