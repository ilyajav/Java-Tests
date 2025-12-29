# Автотесты

Этот проект содержит автоматизированные тесты для Wikipedia

### Стек для веба:
- Java 11 или новее
- Maven
- Браузер Chrome или Firefox

### Стек мобильных тестов:
- Java 11 или новее
- Maven
- Appium
- Android эмулятор или телефон
- Приложение Wikipedia должно быть установлено
- Android SDK (обычно устанавливается вместе с Android Studio)

## Установка

1. Скачать проект
2. Установть зависимости:
   ```bash
   mvn clean install
   ```

## Настройка мобильных тестов

### 1. Установка Node.js
Установить Node.js по ссылке - https://nodejs.org/en/download

### 2. Установка Appium

Через npm:
```bash
npm install -g appium
appium driver install uiautomator2
```

### 3. Установка Anroid Studio
Установить Android Stdio по официальной ссылке - https://developer.android.com/studio/run/emulator?hl=ru

### 4. Настройка переменных окружения Android

Appium требует переменные `ANDROID_HOME`;

1. Найдите путь к Android SDK:
    - Откройте Android Studio → **File → Settings → Android SDK**
    - Скопируйте путь из поля **"Android SDK Location"**
    - Обычно: `C:\Users\ВашеИмя\AppData\Local\Android\Sdk`

2. Установите переменную окружения:
    - Нажмите `Win + R`, введите `sysdm.cpl`, нажмите Enter
    - Вкладка **"Дополнительно"** → **"Переменные среды"**
    - В разделе **"Системные переменные"** нажмите **"Создать"**
    - **Имя:** `ANDROID_HOME`
    - **Значение:** путь к Android SDK
    - Нажмите **OK**

3. Добавьте в PATH:
    - Найдите `Path` в системных переменных → **"Изменить"**
    - Добавьте: `%ANDROID_HOME%\platform-tools`
    - Нажмите **OK**

4. **ПЕРЕЗАПУСТИТЕ** IntelliJ IDEA и терминал

### 5. Запуск эмулятора и проверка подключения

1. Запустите эмулятор в Android Studio
2. Проверьте подключение:
   ```bash
   adb devices
   ```
   Должно показать что-то вроде:
   ```
   emulator-5562    device
   ```
   Запишите имя устройства

### 6. Установка Wikipedia на эмулятор

Через Google Play Store на эмуляторе или через APK:
```bash
adb install путь_к_файлу/wikipedia.apk
```

### 6. Настройка проекта

Откройте файл `src/test/java/com/example/utils/AppiumDriverManager.java`

Найдите строку 48 и замените `emulator-5562` на имя вашего устройства:
```java
options.setDeviceName("emulator-5562");
```

### 7. Запуск Appium Server

```bash
appium
```

## Как запустить тесты

### Веб-тесты

Через IDE - правый клик на файле `WikipediaWebTests` -> Run 'WikipediaWebTests'

**Важно:** Для веб-тестов необходимо установить браузер Google Chrome!


### Мобильные тесты

Перед запуском убедитесь, что:
- Appium запущен
- Эмулятор/телефон подключен
- Wikipedia установлена и открыта на главной странице

Через IDE - правый клик на файле `WikipediaMobileTests` -> Run 'WikipediaMobileTests'+
