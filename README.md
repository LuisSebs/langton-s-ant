# Hormiga de Langton

Este programa simula hasta 11,000 pasos de la hormiga de Langton, al llegar a esta cantidad se detiene el programa, pero se logra ver el comportamiento emergente que genera la carretera.

<img width="400px" src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExMHpqMmFoYmNiejUzcGw0YXd4cGh4cGVxanV6aTNqdmdheHgxaGZsNiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/1lYoe7AvVbluUMaBdN/giphy-downsized-large.gif"/>

# Ejecucion

Para correr la práctica crea un carpeta `./classes`
```bash
mkdir ./classes
```

Ejecuta el siguiente comando para compilar todas las clases

```bash
javac -d ./classes -cp lib/core.jar:. hormiga/*.java
```

Ahora ejecutamos el programa 

```bash
java -cp ./classes:lib/core.jar hormiga.HormigaDeLangton
```

