package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class GameScreen implements Screen {

    Hud hud;

    int currentLevel = 0;
    OrthographicCamera camera;

    MyGdxGame game;
    Player player;
    DelayedRemovalArray<Enemy> enemies;
    DelayedRemovalArray<DestoryFX> destoryFXs = new DelayedRemovalArray<DestoryFX>();


    SpriteBatch batch;
    SpriteBatch uiBatch;
    int enemyAmount = 5;

    Texture mapImg = new Texture("Map.png");
    Texture Map2 = new Texture("M2.png");
    Texture Map3 = new Texture("GreenM.png");

    Texture playerHealth = new Texture("Player_Health.png");

    Texture bossHealthbar = new Texture("Boss-health.png");
    Texture bossHealthOutline = new Texture("Boss-healthbar.png");
    Texture boss2HealthOutline = new Texture("Boss2-healthbar.png");
    Texture boss3HealthOutline = new Texture("Boss3-healthbar.png");

    Sound enemyExplodeSound = Gdx.audio.newSound(Gdx.files.internal("EnemyExplode.wav" ));

    //Texture levelChangeDoor = new Texture("LevelChangeDoor.png");
    Interactable levelChangeDoor;

    Sprite mapSprite = new Sprite(mapImg);
    Sprite mapSprite2 = new Sprite(Map2);
    Sprite mapSprite3 = new Sprite(Map3);

    Sprite bossHealthSprite = new Sprite(bossHealthbar);

    Sprite boss1OutlineSprite = new Sprite(bossHealthOutline);
    Sprite boss2OutlineSprite = new Sprite(boss2HealthOutline);
    Sprite boss3OutlineSprite = new Sprite(boss3HealthOutline);


    Sprite playerHealthSprite = new Sprite(playerHealth);

   //button
   Texture buttonSquareTexture = new Texture("buttonSquare_blue.png");
   Texture buttonSquareDownTexture = new Texture("buttonSquare_beige_pressed.png");

//   Texture buttonattactTexture
    Button moveLeftButton;
    Button moveRightButton;
    Button moveDownButton;
    Button moveUpButton;
    Button attactButton;
    Button dashButton;


    int healthAmount;
    public static float mapBoundX;
    public static float mapBoundY;

    public static float mapBoundX2;
    public static float mapBoundY2;
    public GameScreen(MyGdxGame game){
        this.game = game;
    }

    public enum GameState { PLAYING, COMPLETE };

    GameState gameState = GameState.PLAYING;
    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        uiBatch = new SpriteBatch();
        //button
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float buttonSize = h * 0.1f;
        moveLeftButton = new Button(0.0f, buttonSize, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);
        moveRightButton = new Button(buttonSize*2, buttonSize, buttonSize, buttonSize, buttonSquareTexture,buttonSquareDownTexture);
        moveDownButton = new Button(buttonSize, 0.0f, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);
        moveUpButton = new Button(buttonSize, buttonSize*2, buttonSize, buttonSize, buttonSquareTexture,buttonSquareDownTexture);
        attactButton = new Button(w - buttonSize*2, buttonSize, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);
        dashButton = new Button(w - buttonSize*4, buttonSize, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);

        gameState = GameState.PLAYING;

        boss1OutlineSprite.setPosition(camera.position.x, camera.position.y);
        bossHealthSprite.setPosition(camera.position.x, camera.position.y);

        bossHealthSprite.setScale(8,8);
        boss1OutlineSprite.setScale(8, 8);

        boss2OutlineSprite.setScale(8, 8);

        boss3OutlineSprite.setScale(8, 8);
        enemies = new DelayedRemovalArray<Enemy>();
        batch = new SpriteBatch();
        hud = new Hud(batch);
        if(currentLevel == 0){
            for(int i = 0; i < enemyAmount; i++){
               enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
            //enemies.add(new Boss3(new Vector2(MathUtils.random(600), MathUtils.random(400))));
        }
        else if(currentLevel == 1){
            enemies.add(new Boss1(new Vector2(MathUtils.random(600), MathUtils.random(400))));
        }

        else if(currentLevel == 2){
            for(int i = 0; i < enemyAmount + 5 ; i++){
                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }

        else if(currentLevel == 3){
            enemies.add(new Boss2(new Vector2(MathUtils.random(600), MathUtils.random(400))));
        }

        else if(currentLevel == 4){
            for(int i = 0; i < enemyAmount + 10 ; i++){
                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }
        else if(currentLevel == 5){
            enemies.add(new Boss3(new Vector2(MathUtils.random(600), MathUtils.random(400))));
        }


        mapSprite2.setPosition((-mapSprite2.getWidth() / 4) + 30, -mapSprite2.getHeight() / 4);
        mapSprite2.setScale(.5f, .6f);

        mapSprite3.setPosition((-mapSprite3.getWidth() / 4) + 30, -mapSprite3.getHeight() / 4);
        mapSprite3.setScale(.5f, .6f);

        mapSprite.setPosition((-mapSprite.getWidth() / 4) + 30, -mapSprite.getHeight() / 4);
        mapSprite.setScale(.5f, .6f);


        mapBoundX = mapSprite.getWidth() / 2;
        mapBoundY = mapSprite.getHeight() / 2;

        levelChangeDoor = new Interactable(new Vector2(mapBoundX - 300, mapBoundY), 200);
        player = new Player(mapSprite.getWidth() / 4, mapSprite.getHeight() / 4);

        player.enemiesToAttack = enemies;

        healthAmount = player.health / 10;
        playerHealthSprite.setScale(1.5f);

        for (Enemy e:
                enemies) {
            e.target = player;
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f,.1f,.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        ButtonControl();

        healthAmount = player.health / 10;

         if(currentLevel == 0 || currentLevel == 1){
            mapSprite.draw(batch);
            if(currentLevel == 1){
                bossHealthSprite.draw(batch);
                boss1OutlineSprite.draw(batch);
                boss1OutlineSprite.setPosition(camera.position.x, camera.position.y - 400);
                bossHealthSprite.setPosition(camera.position.x, camera.position.y - 400);
                if(enemies.size > 0)
                    bossHealthSprite.setScale((float) enemies.get(0).health / enemies.get(0).maxHealth * 10,10);
            }
        }
         else if(currentLevel == 2 || currentLevel == 3){
            mapSprite3.draw(batch);
             if(currentLevel == 3){
                 bossHealthSprite.draw(batch);
                 boss2OutlineSprite.draw(batch);
                 boss2OutlineSprite.setPosition(camera.position.x, camera.position.y - 400);
                 bossHealthSprite.setPosition(camera.position.x, camera.position.y - 400);
                 if(enemies.size > 0)
                    bossHealthSprite.setScale((float) enemies.get(0).health / enemies.get(0).maxHealth * 10,10);
             }
        }else{
           mapSprite2.draw(batch);
           if(currentLevel == 5){
               boss3OutlineSprite.setPosition(camera.position.x, camera.position.y - 400);
               bossHealthSprite.draw(batch);
               boss3OutlineSprite.draw(batch);
               bossHealthSprite.setPosition(camera.position.x, camera.position.y - 400);
               if(enemies.size > 0)
                    bossHealthSprite.setScale((float) enemies.get(0).health / enemies.get(0).maxHealth * 10,10);
           }
        }

        camera.position.x = MathUtils.lerp(camera.position.x, player.position.x, delta * 5);
        camera.position.y = MathUtils.lerp(camera.position.y, player.position.y, delta * 5);
        camera.update();
        //System.out.println(player.position);
        batch.setProjectionMatrix(camera.combined);

        for(int i = 0; i < healthAmount; i++){

            playerHealthSprite.draw(batch);
            playerHealthSprite.setPosition(camera.position.x - 700 + (i * 32), camera.position.y + 400);
        }

        for (Enemy e : enemies){
            e.render(delta, batch);
            //e.EnemyDetect(player.position.x, player.position.y);
            e.EnemyTrace(player.position.x, player.position.y);
            if(e.health <= 0){
                destoryFXs.add(new DestoryFX(e.position));
                enemyExplodeSound.play(.5f);
                enemies.removeValue(e, false);
                ScreenShake(40);
            }
        }



        for(int i = 0; i < destoryFXs.size; i++){
            destoryFXs.get(i).render(batch);
            destoryFXs.get(i).update(delta);
            if(destoryFXs.get(i).fxAnimation.getFrameNum() == 2){
                destoryFXs.removeIndex(i);
            }
        }



        player.render(delta, batch);

        if(enemies.size <= 0){
            levelChangeDoor.render(batch);
            levelChangeDoor.DetactDisToPlayer(player.position.x, player.position.y);
            if(levelChangeDoor.canSetScene == true){
                game.setScreen(MyGdxGame.levelClearScreen);
            }
        }

        if(player.health <= 0){
            game.setScreen(MyGdxGame.gameOverScreen);
        }
        batch.end();


        uiBatch.begin();
        switch(gameState) {
            //if gameState is Running: Draw Controls
            case PLAYING:
                moveLeftButton.draw(uiBatch);
                moveRightButton.draw(uiBatch);
                moveDownButton.draw(uiBatch);
                moveUpButton.draw(uiBatch);
                attactButton.draw(uiBatch);
                dashButton.draw(uiBatch);
                break;
            //if gameState is Complete: Draw Restart button
//            case COMPLETE:
//                restartButton.draw(uiBatch);
//                break;
        }
        uiBatch.end();
        //check
        boolean checkTouch = Gdx.input.isTouched();
        int touchX = Gdx.input.getX();
        int touchY = Gdx.input.getY();

        moveLeftButton.update(checkTouch, touchX, touchY);
        moveRightButton.update(checkTouch, touchX, touchY);
        moveDownButton.update(checkTouch, touchX, touchY);
        moveUpButton.update(checkTouch, touchX, touchY);
        attactButton.update(checkTouch, touchX, touchY);
        dashButton.update(checkTouch, touchX, touchY);

        if (Gdx.input.isKeyPressed(Input.Keys.A) || moveLeftButton.isDown) {
            moveLeftButton.isDown = true;

        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || moveRightButton.isDown) {
            moveRightButton.isDown = true;

        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || moveDownButton.isDown) {
            moveDownButton.isDown = true;

        } else if (Gdx.input.isKeyPressed(Input.Keys.W) || moveUpButton.isDown) {
            moveUpButton.isDown = true;

        }
        if(attactButton.isDown){
            attactButton.isDown = true;
        }
        if(dashButton.isDown){
            dashButton.isDown = true;
        }
    }

    public void ScreenShake(float amount){
        camera.position.x += MathUtils.random(-1.0f, 1.0f) * amount;
        camera.position.y += MathUtils.random(-1.0f, 1.0f) * amount;
    }


    public void ButtonControl(){
        if(moveRightButton.isDown == true){
            player.isMovingRight = true;
        }
        else if(!moveRightButton.isDown){
            player.isMovingRight = false;
        }
        if(moveLeftButton.isDown == true){
            player.isMovingLeft = true;
        }
        else if(!moveLeftButton.isDown){
            player.isMovingLeft = false;
        }
        if(moveUpButton.isDown == true){
            player.isMovingUp = true;
        }
        else if(!moveUpButton.isDown){
            player.isMovingUp = false;
        }
        if(moveDownButton.isDown == true){
            player.isMovingDown = true;
        }
        else if(!moveDownButton.isDown){
            player.isMovingDown = false;
        }
        if(attactButton.isDown == true){
            player.buttonAttack = true;
        }
        else if(!attactButton.isDown){
            player.buttonAttack = false;
        }
        if(dashButton.isDown == true){
            player.buttonDodge = true;
        }
        else if(!dashButton.isDown){
            player.buttonDodge = false;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
