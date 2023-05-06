package locale;

/**
 * @author draginsky
 * @since 06.05.2023
 */
public class LocaleAdapter {
    private final Language language;

    public LocaleAdapter(Language language) {
        this.language = language;
    }

    public String translate(String text) {
        switch (text) {
            case "Протокол работает" -> {
                return language == Language.RUSSIAN ? "Протокол работает" :
                        (language == Language.ENGLISH ? "Protocol works" : "???");
            }
            case "Вы точно хотите закрыть приложение?" -> {
                return language == Language.RUSSIAN ? "Вы точно хотите закрыть приложение?" :
                        (language == Language.ENGLISH ? "Are you sure you want to close the app?" : "???");
            }
            case "Подтверджение выхода" -> {
                return language == Language.RUSSIAN ? "Подтверджение выхода" :
                        (language == Language.ENGLISH ? "Exit Confirmation" : "???");
            }
            case "Да" -> {
                return language == Language.RUSSIAN ? "Да" :
                        (language == Language.ENGLISH ? "Yes" : "???");
            }
            case "Нет" -> {
                return language == Language.RUSSIAN ? "Нет" :
                        (language == Language.ENGLISH ? "No" : "???");
            }
            case "Режим отображения" -> {
                return language == Language.RUSSIAN ? "Режим отображения" :
                        (language == Language.ENGLISH ? "Display mode" : "???");
            }
            case "Управление режимом отображения приложения" -> {
                return language == Language.RUSSIAN ? "Управление режимом отображения приложения" :
                        (language == Language.ENGLISH ? "Managing the application display mode" : "???");
            }
            case "Системная схема" -> {
                return language == Language.RUSSIAN ? "Системная схема" :
                        (language == Language.ENGLISH ? "System scheme" : "???");
            }
            case "Универсальная схема" -> {
                return language == Language.RUSSIAN ? "Универсальная схема" :
                        (language == Language.ENGLISH ? "Universal scheme" : "???");
            }
            case "Тесты" -> {
                return language == Language.RUSSIAN ? "Тесты" :
                        (language == Language.ENGLISH ? "Tests" : "???");
            }
            case "Тестовые команды" -> {
                return language == Language.RUSSIAN ? "Тестовые команды" :
                        (language == Language.ENGLISH ? "Test commands" : "???");
            }
            case "Сообщение в лог" -> {
                return language == Language.RUSSIAN ? "Сообщение в лог" :
                        (language == Language.ENGLISH ? "Message to the log" : "???");
            }
            case "Новая строка" -> {
                return language == Language.RUSSIAN ? "Новая строка" :
                        (language == Language.ENGLISH ? "New line" : "???");
            }
            case "Выход" -> {
                return language == Language.RUSSIAN ? "Выход" :
                        (language == Language.ENGLISH ? "Exit" : "???");
            }
            case "Закрытие приложения" -> {
                return language == Language.RUSSIAN ? "Закрытие приложения" :
                        (language == Language.ENGLISH ? "Closing the application" : "???");
            }
            case "Игровое поле" -> {
                return language == Language.RUSSIAN ? "Игровое поле" :
                        (language == Language.ENGLISH ? "Game field" : "???");
            }
            case "Протокол работы" -> {
                return language == Language.RUSSIAN ? "Протокол работы" :
                        (language == Language.ENGLISH ? "Work protocol" : "???");
            }
            default -> {
                return "???";
            }
        }
    }
}
