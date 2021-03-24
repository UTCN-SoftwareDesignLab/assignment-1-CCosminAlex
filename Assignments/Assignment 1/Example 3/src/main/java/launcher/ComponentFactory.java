package launcher;

import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import repository.userRole.UserRole;
import service.account.AccountService;
import service.account.AccountServiceIMP;
import service.client.ClientService;
import service.client.ClientServiceIMP;
import service.report.ReportService;
import service.report.ReportServiceIMP;
import service.user.AdminService;
import service.user.AdminServiceIMP;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final AuthenticationService authenticationService;
    private final AdminService adminService;
    private final ReportService reportService;
    private final ClientService clientService;
    private final AccountService accountService;

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;


    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final LoginView loginView;

    private final AdminController adminController;
    private final EmployeeController employeeController;
    private final LoginController loginController;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {

        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();

        this.reportRepository = new ReportRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection);


        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.reportService = new ReportServiceIMP(this.reportRepository);
        this.adminService = new AdminServiceIMP(this.userRepository, this.authenticationService);
        this.clientService = new ClientServiceIMP(this.clientRepository);
        this.accountService = new AccountServiceIMP(this.accountRepository);

        this.loginView = new LoginView();
        this.adminView = new AdminView();
        this.employeeView = new EmployeeView();


        this.adminController = new AdminController(this.adminView,this.adminService, this.authenticationService, this.reportService);
        this.employeeController = new EmployeeController(this.employeeView, this.accountService, this.clientService, this.reportService);
        this.loginController = new LoginController(this.loginView,this.authenticationService,this.adminController,this.employeeController,this.userRepository);

    }

    public AdminController getAdminController() {
        return adminController;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public ReportRepository getReportRepository() {
        return reportRepository;
    }

    public static ComponentFactory getInstance() {
        return instance;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }
}