package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.dto.AccountDto;
import model.dto.ClientDto;
import model.validation.Notification;
import service.account.AccountService;
import service.client.ClientService;
import service.report.ReportService;
import view.EmployeeView;

import java.sql.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final AccountService accountService;
    private final ClientService clientService;
    private final ReportService reportService;
    private int idEmployee;

    public EmployeeController(EmployeeView employeeView, AccountService accountService, ClientService clientService, ReportService reportService) {

        this.employeeView = employeeView;
        this.employeeView.setVisible(false);

        employeeView.setCreateAccountButtonListener(new CreateAccountButtonListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountButtonListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());

        employeeView.setCreateClientButtonListener(new CreateClientButtonListener());
        employeeView.setUpdateClientButtonListener(new UpdateClientButtonListener());
        employeeView.setDeleteClientButtonListener(new DeleteClientButtonListener());

        employeeView.setTransferButtonListener(new TransferButtonListener());
        employeeView.setBillButtonListener(new BillButtonListener());

        employeeView.setGetAccountButtonListener(new getAccountButtonListener());
        employeeView.setGetClientButtonListener(new getClientButtonListener());

        this.accountService = accountService;
        this.clientService = clientService;
        this.reportService = reportService;
    }

    public void setVisible(boolean b) {
        employeeView.setVisible(b);

    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    private class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AccountDto accountDto = new AccountDto(Integer.parseInt(employeeView.getIdNoTxt()), employeeView.getTypeTxt(), Integer.parseInt(employeeView.getAmountTxt()), Date.valueOf(employeeView.getDateTxt()), Integer.parseInt(employeeView.getIdClientTxt()));


            Notification<Boolean> accountNotification = accountService.create2(accountDto);

            if (accountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
            } else {
                if (!accountNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account creation failed");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account created");
                    updateAccountTable();
                    reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Created account.");
                }
            }
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            AccountDto accountDto = new AccountDto(Integer.parseInt(employeeView.getIdNoTxt()), employeeView.getTypeTxt(), Integer.parseInt(employeeView.getAmountTxt()), Date.valueOf(employeeView.getDateTxt()), Integer.parseInt(employeeView.getIdClientTxt()));
            accountService.update(accountDto.getId(), accountDto.getType(), accountDto.getBalance(), (Date) accountDto.getDate(), accountDto.getClientId());
            updateAccountTable();
            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Updated account.");
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            accountService.remove(getAccountFromTable().getId());
            updateAccountTable();
            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Deleted account.");
        }
    }

    private class CreateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ClientDto clientDto = new ClientDto(employeeView.getName(),employeeView.getAddressTxt(),Integer.parseInt(employeeView.getCNPTxt()));

            Notification<Boolean> clientNotification = clientService.create2(clientDto);

            if (clientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                if (!clientNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client creation failed");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client created");
                    updateClientTable();
                    reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Created client");
                }
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client client = getClientFromTable();
            ClientDto clientDto = new ClientDto(employeeView.getName(),employeeView.getAddressTxt(),Integer.parseInt(employeeView.getCNPTxt()));


            clientService.update2(clientDto);

            updateClientTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Updated client.");
        }
    }

    private class DeleteClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            for (Account a : accountService.findAll()) {
                if (a.getClient_id() == getClientFromTable().getId())
                    accountService.remove(a.getId());
            }

            clientService.remove(getClientFromTable().getId());
            updateAccountTable();
            updateClientTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Deleted client with all its accounts.");
        }
    }

    private class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account a1, a2;
            a1 = accountService.findById(Integer.parseInt(employeeView.getFirstAcc()));
            a2 = accountService.findById(Integer.parseInt(employeeView.getSecondAcc()));

            accountService.update(a1.getId(), a1.getType(), a1.getBalance() - Integer.parseInt(employeeView.getSumTxt()), a1.getCreation_date(), a1.getClient_id());
            accountService.update(a2.getId(), a2.getType(), a2.getBalance() + Integer.parseInt(employeeView.getSumTxt()), a2.getCreation_date(), a2.getClient_id());

            updateAccountTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Transfered money from " + a1.getId() + "account to" + a2.getId());
        }
    }

    private class BillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = getAccountFromTable();
            accountService.update(account.getId(), account.getType(), account.getBalance() - Integer.parseInt(employeeView.getProcessTxt()), account.getCreation_date(), account.getClient_id());

            updateAccountTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Bills payed by client " + account.getClient_id());
        }
    }

    private class getAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Account account = getAccountFromTable();

            employeeView.setAmountTxt(Integer.toString(account.getBalance()));
            employeeView.setTypeTxt(account.getType());
            employeeView.setIdClientTxt(Integer.toString(account.getClient_id()));
        }
    }

    private class getClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client client = getClientFromTable();

            employeeView.setNameTxt(client.getName());
            employeeView.setAddressTxt(client.getAddress());
            employeeView.setCNPTxt(client.getCNP());
        }
    }

    public Client getClientFromTable() {

        Integer id = (Integer) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(), 0);
        String name = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(), 1);
        String address = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(), 2);
        String CNP = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(), 3);

        Client c = new ClientBuilder()
                .setId(id)
                .setName(name)
                .setAddress(address)
                .setCNP(CNP)
                .build();

        return c;
    }

    public Account getAccountFromTable() {

        Integer accountId = (Integer) employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(), 0);
        String type = String.valueOf(employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(), 1));
        Integer balance = (Integer) employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(), 2);
        Date date = (Date) employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(), 3);
        Integer idClient = (Integer) employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(), 4);

        Account a = new AccountBuilder()
                .setId(accountId)
                .setType(type)
                .setBalance(balance)
                .setDateOfCreation()
                .setIdClient(idClient)
                .build();
        return a;
    }

    public void updateAccountTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("type");
        model.addColumn("balance");
        model.addColumn("date of creation");
        model.addColumn("client_id");

        for (Account a : accountService.findAll()) {
            Object[] o = {a.getId(), a.getType(), a.getBalance(), a.getCreation_date(), a.getClient_id()};
            model.addRow(o);
        }

        employeeView.getTableAccounts().setModel(model);
    }

    public void updateClientTable() {
        DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn("id");
        model2.addColumn("name");
        model2.addColumn("address");
        model2.addColumn("CNP");

        for (Client c : clientService.findAll()) {
            Object[] o = {c.getId(), c.getName(), c.getAddress(), c.getCNP()};
            model2.addRow(o);
        }

        employeeView.getTableClients().setModel(model2);
    }
}