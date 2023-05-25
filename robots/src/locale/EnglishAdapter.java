package locale;

/**
 * @author draginsky
 * @since 11.05.2023
 */
public class EnglishAdapter implements LanguageAdapter {
    public String translate(String text) {
        return switch (text) {
            case "Протокол работает" -> "Protocol works";
            case "Вы точно хотите закрыть приложение?" -> "Are you sure you want to close the app?";
            case "Подтверджение выхода" -> "Exit Confirmation";
            case "Да" -> "Yes";
            case "Нет" -> "No";
            case "Режим отображения" -> "Display mode";
            case "Управление режимом отображения приложения" -> "Managing the application display mode";
            case "Системная схема" -> "System scheme";
            case "Универсальная схема" -> "Universal scheme";
            case "Тесты" ->  "Tests";
            case "Тестовые команды" -> "Test commands";
            case "Сообщение в лог" -> "Message to the log";
            case "Новая строка" -> "New line";
            case "Выход" -> "Exit";
            case "Закрытие приложения" -> "Closing the application";
            case "Игровое поле" -> "Game field";
            case "Протокол работы" -> "Work protocol";
            case "Сохранение и загрузка" -> "Save and load";
            case "Сохранение и загрузка состояния приложения" -> "Saving and loading the application state";
            case "Сохранение" -> "Save";
            case "Загрузка" -> "Load";
            default -> "???";
        };
    }
}
