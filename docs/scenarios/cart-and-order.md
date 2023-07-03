## Добавление в корзину

1. Пользователь переходит на карточку товара (запрос на Product API)
2. Пользователь выбирает нужное количество товара и нажимает на кнопку "добавить в корзину"
3. Выполняется проверка, что пользователь залогинен. Если нет, то ему предлагается залогиниться
4. Товар добавляется в корзину - выполняется запрос на ShoppingCart API и создается сущность SelectedProduct 
5. Если пользователь переходит в корзину, то видит все добавленные им товары (запрос на ShoppingCart API)

## Заказ

1. Находясь в корзине, клиент нажимает на кнопку "Перейти к оформлению"
2. Отображаются все ранее выбранные товары (запрос на ShoppingCart API)
3. Отображаются все равнее сохраненные способы оплаты (запрос на BankCard API), либо предоставляется возможность создать новый способ оплаты (POST запрос на BankCard API)
4. Клиент жмет на кнопку "Оплатить онлайн"
5. Создается новый заказ (запрос на Order API)
6. Создается новый платеж (без запроса на Payment API, Payment создается сам)
7. TODO логика оплаты
8. После успешной оплаты заказа клиент видит статус своего заказа в личном кабинете (запрос на Order API)

#### Идеи:

1. Ограниченное время на оплату товара
2. Резервирование товара на время оплаты, если его кто-то сейчас оплачивает
3. Возможность указать получателем другого человека
4. Ограничения на покупку по возрасту - если залогинен и 18<, то можно покупать взрослые товары
5. Ожидаемая дата доставки, динамическая дата доставки для каждого товара, а не хардкод для всего заказа целиком
6. Промокоды и бонусы
7. Скидки на товары