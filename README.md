# Juego: ¡Pégale al monstruo!
## Uso: Multicast (UDP), Sockets TCP y Java RMI

### Características:
- El cliente tiene una interfaz con una rejilla de objetos (pueden ser checkboxes)
- Cada jugador tiene la misma interfaz con una rejilla de objetos
- El servidor puede enviar a los clientes (vía mensaje multicast) un “monstruo” y la posición en la cual aparecerá. El servidor abre una ventana de tiempo para recibir respuestas. Las respuestas provienen de los clientes indicando que han golpeado al “monstruo”.
- Los clientes reciben mensajes multicast (conteniendo monstruos) y lo despliegan en pantalla en la posición indicada.
- El usuario golpea al monstruo (hace clic sobre el checkbox) y envía un mensaje vía Sockets al servidor.
- El cliente que golpee N monstruos primero es el ganador. Esto es determinado por el Servidor.
- El servidor le avisa a los jugadores (vía un mensaje multicast) quien ganó el juego y se inicializa para empezar otro juego.
- Los jugadores deben de poder entrar y salir del juego dinámicamente, sin que se afecte la partida actual.
- El servidor debe ofrecer un servicio de registro desplegado en Java RMI. Como respuesta al registro, el servidor le pasará al cliente la dirección IP y los puertos con los que se realiza el juego. Está comunicación será obligatoriamente usando Java RMI.
- Evaluación experimental de desempeño: Se debe estresar tanto el registro RMI como el juego.
