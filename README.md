# Arkanoid
This is a clone version of arkanoid classic game.

Technologies used: Java, JavaFX, maven.

# Game architechture
![game architechture](https://github.com/MouseCM/arkanoid/blob/main/assets/readme/uml.png)


# Feature
### Control 
- R to restart game.
- Esc to Pause game.
- Q to Exit to menu screen.

### Ball movement
- Using A/D to move left and right space to start.
- Using mouse, ball will follow mouse and left click to start.

### Paddle movement

- You can move paddle using A and D for left and right or using your mouse, paddle will follow your mouse.

### Ball bouncing

- When ball hit border or brick it will bouncing back.

- When hit paddle depend on position of hit, ball will bounce with specific angle for specific position.

### Brick

- Include normal brick and strong brick.
  
- Strong brick has 3 type depend on it hp left from 3 to 1.

### PowerUp

1. Extra life

    - Increase player live by 1.

2. X3 Balls

    - You will get more 2 ball in each ball you have, spawn at 20 angle apart from the current ball, one to the left and one to the right.
3. FireBall
   
    - Normal ball will become fireball which can break through all brick except unbreakable Brick.
4. FastBall

    - Ball speed will x2 faster but max speed is default speed x 2.
5. BigBall

    - Ball radius x2 but only active 1 even if player get 2 bigBall.
6. BigPaddle

    - Paddle width x2, only active 1 not cumulative.
7. Shooting

    - Paddle will shoot 2 bullet in each side in every 0.5s.

