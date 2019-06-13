package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
    int enemyAmount = 5;

    Texture mapImg = new Texture("Map.png");
    Texture Map2 = new Texture("M2.png");
    Texture Map3 = new Texture("GreenM.png");

    Texture playerHealth = new Texture("Player_Health.png");

    Texture bossHealthbar = new Texture("Boss-health.png");
    Texture bossHealthOutline = new Texture("Boss-healthbar.png");
    Texture boss2HealthOutline = new Texture("Boss2-healthbar.png");
    Texture boss3HealthOutline = new Texture("Boss3-healthbar.png");



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



    int healthAmount;
    public static float mapBoundX;
    public static float mapBoundY;

    public static float mapBoundX2;
    public static float mapBoundY2;
    public GameScreen(MyGdxGame game){
        this.game = game;
    }


    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        boss1OutlineSprite.setPosition(camera.position.x, camera.position.y);
        bossHealthSprite.setPosition(camera.position.x, camera.position.y);

        bossHealthSprite.setScale(10,10);
        boss1OutlineSprite.setScale(10, 10);

        enemies = new DelayedRemovalArray<Enemy>();
        batch = new SpriteBatch();
        hud = new Hud(batch);
        if(currentLevel == 0){
            for(int i = 0; i < enemyAmount; i++){
               enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
               //enemies.add(new Minotaur(new Vector2(MathUtils.random(600), MathUtils.random(400))));
                //enemies.add(new Boss1(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }
        else if(currentLevel == 1){
            for(int i = 0; i < enemyAmount + 5; i++){
                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
                //enemies.add(new Minotaur(new Vector2(MathUtils.random(600), MathUtils.random(400))));
                //enemies.add(new Boss1(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }
        else if(currentLevel == 2){
            for(int i = 0; i < 1 ; i++){
                //enemies.add(new Minotaur(new Vector2(MathUtils.random(600), MathUtils.random(400))));
                enemies.add(new Boss1(new Vector2(MathUtils.random(600), MathUtils.random(400))));}
        }
//        else if(currentLevel == 3){
//            for(int i = 0; i < enemyAmount + 15; i++){
//                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
//                //enemies.add(new Minotaur(new Vector2(MathUtils.random(600), MathUtils.random(400))));
//            }
//        }


        mapSprite2.setPosition((-mapSprite2.getWidth() / 4) + 30, -mapSprite2.getHeight() / 4);
        mapSprite2.setScale(.5f, .6f);
//        mapBoundX2 = mapSprite2.getWidth() / 2;
//        mapBoundY2 = mapSprite2.getHeight() / 2;

        mapSprite3.setPosition((-mapSprite3.getWidth() / 4) + 30, -mapSprite3.getHeight() / 4);
        mapSprite3.setScale(.5f, .6f);
//        mapBoundX2 = mapSprite2.getWidth() / 2;
//        mapBoundY2 = mapSprite2.getHeight() / 2;

//        levelChangeDoor = new Interactable(new Vector2(mapBoundX, mapBoundY), 100);
//        player = new Player(mapSprite2.getWidth() / 4, mapSprite2.getHeight() / 4);

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


        healthAmount = player.health / 10;

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            currentLevel += 1;
        }  if(currentLevel == 0){
            mapSprite.draw(batch);

        }else if(currentLevel == 1){
            mapSprite3.draw(batch);
        }else{
           mapSprite2.draw(batch);
           boss1OutlineSprite.draw(batch);
           boss1OutlineSprite.setPosition(camera.position.x, camera.position.y - 400);
           bossHealthSprite.draw(batch);
           bossHealthSprite.setPosition(camera.position.x, camera.position.y - 400);
           if(enemies.size > 0)
                bossHealthSprite.setScale((float) enemies.get(0).health / enemies.get(0).maxHealth * 10,10);
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
    }

    public void ScreenShake(float amount){
        camera.position.x += MathUtils.random(-1.0f, 1.0f) * amount;
        camera.position.y += MathUtils.random(-1.0f, 1.0f) * amount;
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
