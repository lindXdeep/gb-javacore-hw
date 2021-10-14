# Практическое задание

1. Добавить на серверную сторону чата логирование, с выводом информации о действиях на сервере (запущен, произошла ошибка, клиент подключился, клиент прислал сообщение/команду). Как минимум, заменить все sout
   
2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив. Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.

> Написать набор тестов для этого метода (по 3-4 варианта входных данных).

```
Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
```

3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы, то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).

```
[ 1 1 1 4 4 1 4 4 ] -> true
[ 1 1 1 1 1 1 ] -> false
[ 4 4 4 4 ] -> false
[ 1 4 4 1 1 4 3 ] -> false
```

3. Добавить на серверную сторону сетевого чата логирование событий (сервер запущен, произошла ошибка, клиент подключился, клиент прислал сообщение/команду).
   
4. *Добавить на клиентскую сторону сетевого чата логирование событий.
   
5. *Покрыть сетевой чат тестами - насколько хватит терпения и возможностей)