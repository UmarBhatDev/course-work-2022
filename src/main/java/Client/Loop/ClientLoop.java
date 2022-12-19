package Client.Loop;

import Client.Regular.InputRestriction;
import Client.ServerProvider;
import Client.View.Scenes.AccountInfo.AccountInfoLayer;
import Client.View.Scenes.AccountSettings.AccountSettingsLayer;
import Client.View.Scenes.AccountSettings.AccountSettingsView;
import Client.View.Scenes.AccountValidity.AccountValidityLayer;
import Client.View.Scenes.AccountValidity.AccountValidityView;
import Client.View.Scenes.Admin.AdminScene;
import Client.View.Scenes.Admin.AdminSceneView;
import Client.View.Scenes.ApproveAccountsList.ApproveAccountsListLayer;
import Client.View.Scenes.ApproveAccountsList.ApproveAccountsListView;
import Client.View.Scenes.Behaviors.Exit.ExitView;
import Client.View.Scenes.Behaviors.Notification.NotificationLayer;
import Client.View.Scenes.Behaviors.UnDo.ReturnView;
import Client.View.Scenes.boardCreation.BoardCreateScene;
import Client.View.Scenes.boardCreation.BoardCreateView;
import Client.View.Scenes.employeeList.EmployeesListLayer;
import Client.View.Scenes.bootstrapScene.BootstrapScene;
import Client.View.Scenes.bootstrapScene.BootstrapSceneView;
import Client.View.Scenes.Layer;
import Client.View.Scenes.SetPosition.SetPositionLayer;
import Client.View.Scenes.SetPosition.SetPositionView;
import Client.View.Scenes.login.LogInScene;
import Client.View.Scenes.login.LogInSceneView;
import Client.View.Scenes.signin.SignUpScene;
import Client.View.Scenes.signin.SignUpSceneView;
import Client.View.Window.Window;
import Infrastructure.Disposable;
import Server.Entities.Board;
import Server.Entities.Employee;
import Server.Entities.Person;
import Server.Entities.StaticEnumerable.Role;
import Server.Entities.User;
import Server.ServerContract;
import Server.TransferMessageContainer;

import java.time.LocalDate;
import java.util.Stack;

public class ClientLoop implements Disposable, ExitView, ReturnView, BootstrapSceneView, LogInSceneView, SignUpSceneView,
        BoardCreateView, AccountValidityView, AdminSceneView, ApproveAccountsListView, AccountSettingsView, SetPositionView
{
    private final ServerProvider serverProvider;
    private final Window window;

    private Employee employee;

    private final Stack<Layer> returnLayerRoadMap;
    private Board board;


    public ClientLoop(ServerProvider serverProvider, Window window)
    {
        this.serverProvider = serverProvider;
        this.window = window;
        returnLayerRoadMap = new Stack<Layer>();
    }

    public void dispose()
    {
        System.out.println(ClientLoop.class + "/dispose");
   
        window.destroy();
        serverProvider.dispose();
    }
    
    public void onLogInButtonClick()
    {
        window.switchLayer(new LogInScene(this));
        returnLayerRoadMap.push(new BootstrapScene(this));

        serverProvider.startAction(ServerContract.Operations.LOG_IN);
    }

    public void onSignUpButtonClick()
    {
        window.switchLayer(new SignUpScene(this));
        returnLayerRoadMap.push(new BootstrapScene(this));

        serverProvider.startAction(ServerContract.Operations.SIGN_UP);
    }

    public void onCreateCompanyButtonClick()
    {
        window.switchLayer(new BoardCreateScene(this));
        returnLayerRoadMap.push(new BootstrapScene(this));

        serverProvider.startAction(ServerContract.Operations.CREATE_NEW_BOARD);
    }

    public void onSetColorButtonDown(float[] color)
    {
        window.switchColor(color);
    }

    public void onLoginEndedButtonClick(LogInScene.Container container, NotificationLayer notificationLayer)
    {
        boolean isCorrectField = InputRestriction.textFieldIsValid(container.Password.toString()) && InputRestriction.textFieldIsValid(container.Login.toString());

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Wrong input!";
            return;
        }

        serverProvider.send(new User(container.Login.toString(), container.Password.toString()));

        TransferMessageContainer answer = serverProvider.waitMessage();
        boolean isUserExist = answer.Key().equals(ServerContract.Result.SUCCESS_KEY);

        if (!isUserExist)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "User not found!";
            return;
        }

        employee = answer.Employee;
        
        if (answer.Employee.getUser().getRole().equals(Role.ADMIN))
            window.switchLayer(new AdminScene(this, employee.getUser().getRole()));
        else window.switchLayer(new AccountValidityLayer(this));

        returnLayerRoadMap.push(new LogInScene(this));
    }
    
    public void onBoardCreateCompletedButtonClick(BoardCreateScene.Container container, NotificationLayer notificationLayer)
    {
        boolean isCorrectField = InputRestriction.textFieldIsNotEmpty(container.Name.toString()) && InputRestriction.dateIsValid(container.StartDate.toString()) && InputRestriction.dateIsValid(container.EndDate.toString());

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Wrong input!";
            return;
        }

        var startDate = LocalDate.parse(container.StartDate.toString());
        var endDate = LocalDate.parse(container.EndDate.toString());

        var board = new Board(container.Name.toString(), startDate, endDate);

        serverProvider.send(board);
        TransferMessageContainer message = serverProvider.waitMessage();
        boolean boardAlreadyExist = message.Key().equals(ServerContract.Result.FAILED_KEY) && message.message().equals(ServerContract.ResultDetails.COMPANY_FAILED);


        if (boardAlreadyExist)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Board with the same name already exists!";
            return;
        }

        this.board = board;

        onReturnButtonClick();
    }
    
    public void onButtonExit()
    {
        serverProvider.startAction(ServerContract.Operations.EXIT);
        dispose();
    }
    
    public void onReturnButtonClick()
    {
        Layer layer = returnLayerRoadMap.pop();

        if (layer.getClass().equals(LogInScene.class)) layer = new BootstrapScene(this);
        if (layer.getClass().equals(SignUpScene.class)) layer = new BootstrapScene(this);
        if (layer.getClass().equals(BoardCreateScene.class)) layer = new BootstrapScene(this);
        if (layer.getClass().equals(AccountValidityLayer.class)) layer = new BootstrapScene(this);


        serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.RETURN, ServerContract.Operations.RETURN));

        window.switchLayer(layer);
    }
    
    public void onRegistrationCompletedButtonClick(SignUpScene.Container container, NotificationLayer notificationLayer)
    {

        boolean isCorrectField = InputRestriction.textFieldIsNotEmpty(container.FirstName.toString()) 
                && InputRestriction.textFieldIsNotEmpty(container.LastName.toString())
                && InputRestriction.dateIsValid(container.DateOfBirth.get())
                && InputRestriction.textFieldIsValid(container.Password.toString()) 
                && InputRestriction.textFieldIsValid(container.Login.toString()) 
                && InputRestriction.textFieldIsValid(container.Email.toString())
                && InputRestriction.textFieldIsValid(container.Position.toString());

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Wrong input!";
            return;
        }

        var registrationDate = LocalDate.now();
        var dateOfBirth = LocalDate.parse(container.DateOfBirth.toString());

        var user = new User(container.Login.toString(), container.Password.toString(),container.Role, registrationDate);
        var person = new Person(container.FirstName.toString(), container.LastName.toString(), container.Email.toString(), dateOfBirth);
        var employee = new Employee(user, person, container.Position.toString());
        
//       user.setEmployee(employee);        
//       person.setEmployee(employee);

        serverProvider.send(employee);

        TransferMessageContainer message = serverProvider.waitMessage();
        boolean isUserExist = message.Key().equals(ServerContract.Result.FAILED_KEY) && message.message().equals(ServerContract.ResultDetails.USER_FAILED);

        if (isUserExist)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "User with the same login already exists!";
            return;
        }

        this.employee = employee;
        window.switchLayer(new AccountValidityLayer(this));
        returnLayerRoadMap.push(new SignUpScene(this));

    }

    public void observeState()
    {
        serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.ACCOUNT_VALIDATE_INFO, employee.getUser().getLogin()));
        var keyResult = serverProvider.waitAction();
        if (keyResult.equals(ServerContract.Result.FAILED_KEY)) return;

        window.switchLayer(new AdminScene(this, employee.getUser().getRole()));
        returnLayerRoadMap.push(new AccountValidityLayer(this));
    }
    
    public void onJoinRequestButtonClick(NotificationLayer notificationLayer)
    {
        serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.ACCOUNT_ACK, employee.getUser().getLogin()));
        TransferMessageContainer message = serverProvider.waitMessage();

        if (message.Key().equals(ServerContract.Result.FAILED_KEY))
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "No requests!";
            return;
        }

        window.switchLayer(new ApproveAccountsListLayer(this, message.EmployeeList.value));
        returnLayerRoadMap.push(new AdminScene(this, employee.getUser().getRole()));
    }

    public void onMyAccountButtonClick(NotificationLayer notificationLayer)
    {
        window.switchLayer(new AccountSettingsLayer(this));
        returnLayerRoadMap.push(new AdminScene(this,  employee.getUser().getRole()));
    }

    public void onAccountInfoButtonClick()
    {
        window.switchLayer(new AccountInfoLayer(this, employee));
        returnLayerRoadMap.push(new AdminScene(this, employee.getUser().getRole()));
    }

    public void onGetAllEmployeesInfoButtonClick(NotificationLayer notificationLayer)
    {
        serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.GET_ALL_EMPLOYEES, employee.getUser().getLogin()));
        TransferMessageContainer message = serverProvider.waitMessage();

        if (message.Key().equals(ServerContract.Result.FAILED_KEY))
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "No employees";
            return;
        }

        window.switchLayer(new EmployeesListLayer(this, message.EmployeeList.value));
        returnLayerRoadMap.push(new AdminScene(this, employee.getUser().getRole()));
    }



//    public void onBanAccountButtonClick(NotificationLayer notificationLayer)
//    {
//        serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.GET_ALL_EMPLOYEES, employee.getUser().getLogin()));
//        TransferMessageContainer message = serverProvider.waitMessage();
//
//        List<Employee> employees = new ArrayList<Employee>();
//        for (var item : message.EmployeeList.value)
//        {
//            if (item.getUser().getAccountValidity() != 0) employees.add(item);
//        }
//        if (message.Key().equals(ServerContract.Result.FAILED_KEY) || employees.isEmpty())
//        {
//            notificationLayer.NotificationFlag = true;
//            notificationLayer.Message = "only you are in the company";
//            return;
//        }
//
//
//        window.switchLayer(new BanAccountsListLayer(this, employees));
//        returnLayerRoadMap.push(new AdminScene(this, employee.getUser().getRole()));

    public void onSetPosition(NotificationLayer notificationLayer)
    {
        try
        {
            serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.GET_ALL_EMPLOYEES, employee.getUser().getLogin()));
            TransferMessageContainer message = serverProvider.waitMessage();

            if (message.Key().equals(ServerContract.Result.FAILED_KEY))
            {
                notificationLayer.NotificationFlag = true;
                notificationLayer.Message = "Only you are in the company";
                return;
            }

            window.switchLayer(new SetPositionLayer(this, message.EmployeeList.value));
            returnLayerRoadMap.push(new AdminScene(this, employee.getUser().getRole()));
        }
        catch (Exception ignored)
        {
        }
    }

    public void onBoardsButtonClicked()
    {
//        window.switchLayer(new AddTransactionLayer(this, employee.getCompany().getBudget()));
//        returnLayerRoadMap.push(new AdminScene(this, employee.getPosition()));
    }

    
    public void onViewReportsButtonClicked(NotificationLayer notificationLayer)
    {
//        serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.ALL_TRANSACTION, employee.getCompany().getName()));
//        TransferMessageContainer dto = serverProvider.waitMessage();
//
//        try (FileWriter writer = new FileWriter("notes.txt", true))
//        {
//            char[] breakLine = new char[50];
//            Arrays.fill(breakLine, '=');
//            breakLine[breakLine.length - 1] = '\n';
//            writer.write(breakLine);
//            writer.write(new Date(new java.util.Date().getTime()) + "\n");
//
//            String budgetInfo = employee.getCompany().getName() + " : " + employee.getCompany().getBudget() + "\n";
//            writer.write(budgetInfo);
//
//            for (var transaction : dto.TransactionList.value)
//                writer.write(transaction.toString() + "\n");
//
//            writer.flush();
//
//            notificationLayer.NotificationFlag = true;
//            notificationLayer.Message = "info added in notes.txt file";
//        }
//        catch (IOException ignored)
//        {
//        }
    }

    @Override
    public void onBoardsEditButtonClicked()
    {

    }

    @Override
    public void onAccountsEditButtonClicked()
    {

    }

    public void onApproveButtonClick(ApproveAccountsListLayer.Container container, NotificationLayer notificationLayer)
    {
        boolean isCorrectField = InputRestriction.amountIsValid(container.SelectedUser.get(), 0, container.EmployeeList.size() - 1);

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Incorrect index input";
            return;
        }

        serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.UN_BLOCK_ACCOUNT, container.EmployeeList.get(container.SelectedUser.get()).getUser().getLogin()));

        String operationResult = serverProvider.waitAction();
        if (operationResult.equals(ServerContract.Result.SUCCESS_KEY))
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Success!";

            serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.ACCOUNT_ACK, employee.getUser().getLogin()));
            TransferMessageContainer message = serverProvider.waitMessage();

            if (message.Key().equals(ServerContract.Result.FAILED_KEY))
            {
                notificationLayer.Message = "No requests!";
                window.switchLayer(new AdminScene(this, employee.getUser().getRole()));
                return;
            }
            container.EmployeeList = message.EmployeeList.value;
        }
    }

    public void onEditNameButtonClick(AccountSettingsLayer.Container container, NotificationLayer notificationLayer)
    {
        boolean isCorrectField =
                InputRestriction.textFieldIsNotEmpty(container.FirstName.toString()) 
                && InputRestriction.textFieldIsNotEmpty(container.LastName.toString());

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Wrong input!";
            return;
        }

        var person = new Person(container.FirstName.get(), container.LastName.get(),
                this.employee.getPerson().getEmail(), this.employee.getPerson().getDateOfBirth());

        var employee = new Employee(this.employee.getUser(), person, this.employee.getPosition());
        
        TransferMessageContainer dto = new TransferMessageContainer(ServerContract.Operations.UPDATE_ACCOUNT_INFO, "");
        dto.Employee = employee;
        serverProvider.startAction(dto);
        String message = serverProvider.waitAction();

        if (message.equals(ServerContract.Result.SUCCESS_KEY))
        {
            this.employee = employee;
        }
        else
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Error!";
            return;
        }
        container.nameFlag = false;
    }

    public void onEditPasswordButtonClick(AccountSettingsLayer.Container container, NotificationLayer notificationLayer)
    {
        boolean isCorrectField = InputRestriction.textFieldIsValid(container.Password.toString());

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Wrong input!";
            return;
        }

        var user = new User(this.employee.getUser().getLogin(), container.Password.get());

        var employee = new Employee(user, this.employee.getPerson(), this.employee.getPosition());

        var dto = new TransferMessageContainer(ServerContract.Operations.UPDATE_ACCOUNT_INFO, "");

        dto.Employee = employee;
        serverProvider.startAction(dto);
        String message = serverProvider.waitAction();

        if (message.equals(ServerContract.Result.SUCCESS_KEY))
        {
            this.employee = employee;
        }
        else
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Error!";
            return;
        }
        container.passFlag = false;

    }

    public void onEditLoginButtonClick(AccountSettingsLayer.Container container, NotificationLayer notificationLayer)
    {

        boolean isCorrectField = InputRestriction.textFieldIsValid(container.Login.toString());

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Wrong input!";
            return;
        }

        var user = new User(container.Login.get(), this.employee.getUser().getPassword());

        var employee = new Employee(user, this.employee.getPerson(), this.employee.getPosition());

        var dto = new TransferMessageContainer(ServerContract.Operations.UPDATE_ACCOUNT_INFO, "");
        dto.Employee = employee;
        serverProvider.startAction(dto);
        String message = serverProvider.waitAction();

        if (message.equals(ServerContract.Result.SUCCESS_KEY))
        {
            this.employee = employee;
        }
        else
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Error!";
            return;
        }
        container.loginFlag = false;
    }

    public void onEditDateOfBirthButtonClick(AccountSettingsLayer.Container container, NotificationLayer notificationLayer)
    {
        boolean isCorrectField = InputRestriction.dateIsValid(container.DateOfBirth.get());

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Wrong input!";
            return;
        }
        
        var dateOfBirth = LocalDate.parse(container.DateOfBirth.get());
        
        var person = new Person(this.employee.getPerson().getFirstName(), 
                this.employee.getPerson().getLastName(), this.employee.getPerson().getEmail(), dateOfBirth);
        
        var employee = new Employee(this.employee.getUser(), person, this.employee.getPosition());

        var dto = new TransferMessageContainer(ServerContract.Operations.UPDATE_ACCOUNT_INFO, "");
        
        dto.Employee = employee;
        serverProvider.startAction(dto);
        
        var message = serverProvider.waitAction();

        if (message.equals(ServerContract.Result.SUCCESS_KEY))
        {
            this.employee = employee;
        }
        else
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "ERROR";
            return;
        }
        container.ageFlag = false;
    }

   
    public void onSetButtonClick(SetPositionLayer.Container container, NotificationLayer notificationLayer)
    {

        boolean isCorrectField = InputRestriction.amountIsValid(container.SelectedUser.get(), 0, container.EmployeeList.size() - 1);

        if (!isCorrectField)
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Incorrect index input";
            return;
        }

        Employee selectedEmp = container.EmployeeList.get(container.SelectedUser.get());
        selectedEmp.getUser().setRole(container.Position);

        TransferMessageContainer dto = new TransferMessageContainer(ServerContract.Operations.UPDATE_ACCOUNT_INFO, "");
        dto.Employee = selectedEmp;

        serverProvider.startAction(dto);

        String operationResult = serverProvider.waitAction();
        if (operationResult.equals(ServerContract.Result.SUCCESS_KEY))
        {
            notificationLayer.NotificationFlag = true;
            notificationLayer.Message = "Success";

            serverProvider.startAction(new TransferMessageContainer(ServerContract.Operations.GET_ALL_EMPLOYEES, employee.getUser().getLogin()));

            TransferMessageContainer message = serverProvider.waitMessage();
            if (message.Key().equals(ServerContract.Result.FAILED_KEY))
            {
                notificationLayer.Message = "ERROR";
                window.switchLayer(new AdminScene(this, employee.getUser().getRole()));
                return;
            }
            container.EmployeeList = message.EmployeeList.value;
        }
    }
}
