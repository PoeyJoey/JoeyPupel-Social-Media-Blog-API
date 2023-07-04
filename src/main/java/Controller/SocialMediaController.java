package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::postCreateAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIDHandler);
        app.get("/accounts/{account_id}", this::getAllMessagesByUserHandler);

        //app.start(8080);
        return app;
    }

    /**
     * 1
     */
    private void postCreateAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount != null) {
            ctx.json(addedAccount);
        } else {
            ctx.status(400);
        }
    }

    /**
     * 2
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if (loginAccount != null) {
            ctx.json(loginAccount);
        } else {
            ctx.status(401);
        }
    }

    /**
     * 3
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        if (accountService.checkAccountExists(message.getPosted_by()) != null) {
            Message addedMessage = messageService.postMessage(message);
            if (addedMessage != null) {
                ctx.json(addedMessage);
            } else {
                ctx.status(400);
            }
        } else {
            ctx.status(400);
        }
    }

    /**
     * 4
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * 5
     */
    private void getMessageByIDHandler(Context ctx) {
        ctx.pathParam("message_id");
        
    }

    /**
     * 6
     */
    private void deleteMessageByIDHandler(Context ctx) {
        ctx.pathParam("message_id");
        
    }

    /**
     * 7
     */
    private void patchMessageByIDHandler(Context ctx) {
        ctx.pathParam("message_id");
        ctx.status(400);
    }

    /**
     * 8
     */
    private void getAllMessagesByUserHandler(Context ctx) {
        ctx.pathParam("account_id");
    }


}