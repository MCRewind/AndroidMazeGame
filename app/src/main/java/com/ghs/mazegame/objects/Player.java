package com.ghs.mazegame.objects;

        import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
        import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
        import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
        import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

        import android.renderscript.Matrix4f;
        import com.ghs.engine.math.Vector3f;

        import com.ghs.mazegame.MyGLSurfaceView;
        import com.gnarly.engine.components.Animation;
        import com.gnarly.engine.components.Texture;
        import com.ghs.engine.components.VAO;
        import com.ghs.engine.util.Hitbox;
        import com.ghs.engine.components.Shader;
        import com.gnarly.engine.display.Camera;
        import com.gnarly.engine.display.Window;
        import com.ghs.engine.util.Chain;


public class Player {

    private float x = 0, y = 0, width = 0, height = 0;
    private float rightBound = 0, bottomBound = 0;
    private Hitbox hitbox;
    private Camera camera;
    private MyGLSurfaceView window;
    private VAO vao;
    private Animation texture;
    private Shader shader;
    private Chain items;

    public Player(MyGLSurfaceView window, Camera camera, Texture texture, Shader shader, float x, float y, float width, float height, float rightBound, float bottomBound) {
        this.texture = new Animation(texture);
        this.shader = shader;
        this.camera = camera;
        this.window = window;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rightBound = rightBound;
        this.bottomBound = bottomBound;
        this.hitbox = new Hitbox(x, y, width, height);
        items = new Chain(this.hitbox, 1);
        float[] vertices = new float[] {
                0.0f,  0.0f,   0.5f, //TOP LEFT
                0.0f,  height, 0.5f, //BOTTOM LEFT
                width, height, 0.5f, //BOTTOM RIGHT
                width, 0.0f,   0.5f  //TOP RIGHT
        };
        int[] indices = new int[] {
                0, 1, 3,
                1, 2, 3
        };
        int[] texCoords = new int[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        vao = new VAO(vertices, indices, texCoords);
    }

    public Player(MyGLSurfaceView window, Camera camera, Animation animation, Shader shader, float x, float y, float width, float height, float rightBound, float bottomBound) {
        this.texture = animation;
        this.shader = shader;
        this.camera = camera;
        this.window = window;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rightBound = rightBound;
        this.bottomBound = bottomBound;
        this.hitbox = new Hitbox(x, y, width, height);
        float[] vertices = new float[] {
                0.0f,  0.0f,   0.5f, //TOP LEFT
                0.0f,  height, 0.5f, //BOTTOM LEFT
                width, height, 0.5f, //BOTTOM RIGHT
                width, 0.0f,   0.5f  //TOP RIGHT
        };
        int[] indices = new int[] {
                0, 1, 3,
                1, 2, 3
        };
        int[] texCoords = new int[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        vao = new VAO(vertices, indices, texCoords);
    }

    public void update() {
        //Handles inputs and moves player with wasd
        float speed = 7, xChange = 0, yChange = 0;
        if(window.isKeyPressed(GLFW_KEY_W))
            yChange -= speed;
        if(window.isKeyPressed(GLFW_KEY_A))
            xChange -= speed;
        if(window.isKeyPressed(GLFW_KEY_S))
            yChange += speed;
        if(window.isKeyPressed(GLFW_KEY_D))
            xChange += speed;
        //Prevents the player from moving off screen
        x = xChange < 0 ? Math.max(0, x + xChange) : Math.min(rightBound - width, x + xChange);
        y = yChange < 0 ? Math.max(0, y + yChange) : Math.min(bottomBound - height, y + yChange);
        hitbox.setPosition(x, y);
        texture.update();
    }

    public void render() {
        Matrix4f proj = new Matrix4f();
        camera.getProjection().translate(new Vector3f(hitbox.getX(), hitbox.getY(), 0), proj);
        shader.setUniformMat4f("projection", proj);
        texture.bind();
        shader.enable();
        vao.render();
        shader.disable();
        texture.unbind();
    }

    public Chain getChain() {
        return items;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        hitbox.setPosition(x, y);
    }

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
        hitbox.setPosition(this.x, this.y);
    }

    public void translate(Vector3f vector) {
        translate(vector.x, vector.y);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
