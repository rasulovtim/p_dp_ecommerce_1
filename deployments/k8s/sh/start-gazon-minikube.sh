#!/bin/sh
minikube start
# останавливаем существующие поды приложения, чтобы впоследствии закешировать актуальный образ
kubectl --namespace default scale deployment gazon-service-deployment --replicas 0
# приостанавливаем выполнение скрипта на 10 секунд, чтобы пода успела удалиться
sleep 15
# удаляем закешированный в миникубе образ приложения (gazon-service)
# если на этом шаге в логах видите ошибку, попробуйте увеличить время на предыдущем шаге
minikube image rm gazon-service
# загружаем актуальный образ приложения в кеш миникуба из локального репозитория (gazon-service)
minikube image load gazon-service
# запускаем поду приложения с новым образом
kubectl --namespace default scale deployment gazon-service-deployment --replicas 1
minikube dashboard