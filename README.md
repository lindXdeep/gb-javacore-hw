# Home work 2.08:

Разобраться с кодом.

1. Добавить отключение неавторизованных пользователей по таймауту (120 сек. ждём после подключения клиента, и если он не авторизовался за это время, закрываем соединение).
   
2. ** Сделать на клиенте регистрацию
   
3. ** Кто хочет углубиться в тему JavaFX - поправить интерфейс: отступы, цвета, шрифты. Если что, оно работает и с css.

## &#9000; Changes:

### &#128296; Features:

- server:
  - Возможность запустить сервер на другом порту ex: `java -jar server.jar <port>`
  - Логирлвание в консоль событий подлючения.
  - Коннект пользователей в отдельных Thread'ах.

- client:
  - консолное меню коннекта к серверу. ex: `java -jar client.jar --connect 127.0.0.1 8181`
  - возможность ожидания подключения если сервер не доступен.
  - 


### &#128295; Bug Fixes: