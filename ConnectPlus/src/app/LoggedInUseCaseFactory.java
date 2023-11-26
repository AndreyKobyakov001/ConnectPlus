package app;

import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInViewModel;
import view.LoggedInView;

public class LoggedInUseCaseFactory {

        private LoggedInUseCaseFactory() {}

        public static LoggedInView create(LoggedInViewModel loggedInViewModel) {
            try{
                LoggedInController loggedInController = createLoggedInUseCase(loggedInViewModel);
                return new LoggedInView(loggedInController, loggedInViewModel);

            } catch (Exception e) {
                System.out.println("Can't open LoggedIn");
            } return null;
        }

        private static LoggedInController createLoggedInUseCase() {
            LoggedIn
            return new LoggedInController(loggedInViewModel);
        }
}
