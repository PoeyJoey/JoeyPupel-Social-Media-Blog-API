package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    private void postCreateAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount != null) {
            System.out.println("It worked! Should be status 200...");
            ctx.json(addedAccount);
            ctx.status(200);
        } else {
            System.out.println("Big Fail! It's No Good!");
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) {
        ctx.status(401);
    }

    private void postMessageHandler(Context ctx) {
        ctx.status(400);
    }

    private void getAllMessagesHandler(Context ctx) {
        
    }

    private void getMessageByIDHandler(Context ctx) {
        ctx.pathParam("message_id");
        
    }

    private void deleteMessageByIDHandler(Context ctx) {
        ctx.pathParam("message_id");
        
    }

    private void patchMessageByIDHandler(Context ctx) {
        ctx.pathParam("message_id");
        ctx.status(400);
    }

    private void getAllMessagesByUserHandler(Context ctx) {
        ctx.pathParam("account_id");
    }


}