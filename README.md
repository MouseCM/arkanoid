# Arkanoid
This is a clone version of arkanoid classic game.

Technologies used: Java, JavaFX, maven.

# Game architechture
![game architechture](https://github.com/MouseCM/taixiu/blob/main/arkanoid%20(1).png)


# Feature
### Control 
| Button | Active |
|-----------|-----------|
| Esc | Pause|
| Q| Back to menu screen|
| R | Restart|
| A, D |Move paddle left or right |
| Space, left mouse click | Start game |
| Mouse | Paddle will follow mouse position|




### Ball bouncing

- When ball hit border or brick it will bouncing back.

- When hit paddle depend on position of hit, ball will bounce with specific angle for specific position.

### Brick

| Brick | Type | Explaination |
|-----------|-----------|------------|
| ![](https://github.com/MouseCM/arkanoid/blob/main/assets/iceburg/normalbrick.png)     |  Normal | Has 1 Hp |
| ![](https://github.com/MouseCM/arkanoid/blob/main/assets/iceburg/brick3.png)     |  Strong | has 3 hp |
| ![](https://github.com/MouseCM/arkanoid/blob/main/assets/iceburg/brick2.png)     |  Strong | has 2 hp |
| ![](https://github.com/MouseCM/arkanoid/blob/main/assets/iceburg/brick1.png)     |  Strong | has 1 hp |
| ![](https://github.com/MouseCM/arkanoid/blob/main/assets/iceburg/unbreakablebrick.png)     |  Unbreakable | unbreakable |
| ![](https://github.com/MouseCM/taixiu/blob/main/explosionbrick.png)     |  Explosion | Will explode when hitted |


### PowerUp

| PowerUp | Type | Effect |
|-----------|-----------|------------|
| ![Extra life](https://github.com/MouseCM/arkanoid/blob/main/assets/powerup/HP.png)     |  Extra Life  | Increase player live by 1 |
| ![x3 Balls](https://github.com/MouseCM/arkanoid/blob/main/assets/powerup/x3Ball.png)     |  x3 Balls  | You will get more 2 ball in each ball you have, spawn at 20 angle apart from the current ball, one to the left and one to the right |
| ![FireBall](https://github.com/MouseCM/arkanoid/blob/main/assets/powerup/FireBall.png)     | FireBall   | Normal ball will become fireball which can break through all brick except unbreakable Brick  |
| ![FastBall ](https://github.com/MouseCM/arkanoid/blob/main/assets/powerup/FastBall.png)     | FastBall   | Ball speed will x2 faster but max speed is default speed x 2   |
| ![BigBall](https://github.com/MouseCM/arkanoid/blob/main/assets/powerup/BigBall.png)     | BigBall  | Ball radius x2 but only active 1 even if player get 2 bigBall    |
| ![BigPaddle](https://github.com/MouseCM/arkanoid/blob/main/assets/powerup/BigPaddle.png)     | BigPaddle   | Paddle width x2, only active 1 not cumulative    |
| ![Shooting](https://github.com/MouseCM/arkanoid/blob/main/assets/powerup/Shooting.png)     | Shooting   | Paddle will shoot 2 bullet in each side in every 0.5s    |


  
# Demo
### Menu
![menu](https://github.com/MouseCM/taixiu/blob/main/menu.png)
### Start Game
![start](https://github.com/MouseCM/taixiu/blob/main/Screenshot%202025-11-13%20at%2016.32.22.png)
### Playing
![playing](https://github.com/MouseCM/taixiu/blob/main/Screenshot%202025-11-13%20at%2016.33.54.png)



# Techonologies Used

| Technologies | Version |
|-----------|-----------|
| Java      | 17   |
| JavaFX     | 21.0.1   | 
