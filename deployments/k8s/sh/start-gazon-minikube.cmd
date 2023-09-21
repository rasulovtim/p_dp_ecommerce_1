minikube start
rem останавливаем существующие поды приложения, чтобы впоследствии закешировать актуальный образ
kubectl --namespace default scale deployment gazon-service-deployment --replicas 0
rem приостанавливаем выполнение скрипта на 10 секунд, чтобы контейнер успел остановиться
timeout /t 15
rem удаляем закешированный в миникубе образ приложения (gazon-service)
rem если на этом шаге в логах видите ошибку, попробуйте увеличить время на предыдущем шаге
minikube image rm gazon-service
rem загружаем актуальный образ приложения в кеш миникуба из локального репозитория (gazon-service)
minikube image load gazon-service
rem запускаем поду приложения с новым образом
kubectl --namespace default scale deployment gazon-service-deployment --replicas 1
minikube dashboard