package fr.delectus98.isticlwjgl.example;

import fr.delectus98.isticlwjgl.graphics.*;
import fr.delectus98.isticlwjgl.graphics.model.OBJFormat;
import fr.delectus98.isticlwjgl.graphics.shaders.DepthBufferObject;
import fr.delectus98.isticlwjgl.opengl.CompatibilityChecking;
import fr.delectus98.isticlwjgl.system.*;
import fr.delectus98.isticlwjgl.system.camera.Camera3DMode;
import fr.delectus98.isticlwjgl.system.camera.FPSCamera3DMode;
import fr.delectus98.isticlwjgl.system.camera.SphereCamera3DMode;
import fr.delectus98.isticlwjgl.system.io.AZERTYLayout;
import fr.delectus98.isticlwjgl.system.io.AutoKeyboardLayout;
import org.lwjgl.opengl.GL20;

import java.io.IOException;

//TODO Texture ThreadLocal (list for all texture bound to slots)
public final class VAOExemple
{
    public static void main(String[] args) throws IOException, InterruptedException {
        GLFWWindow window = new GLFWWindow(new VideoMode(500, 500), "Use mouse left button to clear the screen!", WindowStyle.DEFAULT.remove(WindowStyle.RESIZABLE), CallbackMode.DEFAULT);
        window.setFrameRateLimit(60);

        Mouse mouse = new Mouse(window);
        Keyboard keyboard = new Keyboard(window);

        Camera3D camera3D = new Camera3D(90, 0.5f, 1000, window);
        /*Camera3D camera3D = new CameraOrtho(window, 0, 1000);
        ((CameraOrtho)camera3D).setZoom(10);*/
        Camera3DMode mode = new FPSCamera3DMode(keyboard);
        //Camera3DMode mode = new SphereCamera3DMode(-10, keyboard);
        camera3D.apply();
        window.setCamera(camera3D);

        CompatibilityChecking.isAvailable(VertexArrayObject.class);

        RenderTexture renderTexture = new RenderTexture(500,500);
        //
        Camera3D camera3D_2 = new Camera3D(90, 0.5f, 1000, renderTexture);
        //Camera3D camera3D_2 = new CameraOrtho(new Vector2f(2,2), 0.5f, 3000);
        camera3D_2.invert(true, true);
        renderTexture.setCamera(camera3D_2);
        Sprite sprite = new Sprite(renderTexture.getTexture());
        //Sprite sprite = new Sprite(shadowBuffer.getTexture());

        Shader vaoShader;
        Shader bumpShader;
        Shader shadowShader;

        Texture normal;
        Texture normalMap;

        OBJFormat format;
        VertexArrayObject deer;

        try
        {
            format = new OBJFormat("res/vao/deer.obj");
            deer = new VertexArrayObject(format.getVerticesCount(), format.getSamplesSize().length, new int[]{3, 4}, VertexArrayObject.Mode.TRIANGLES, VertexArrayObject.Usage.STATIC);
            deer.update(0, format.getBufferData("v"));
            deer.update(1, format.getBufferData("c"));

            vaoShader = new Shader("res/vao/vaobis.vert", "res/vao/vaobis.frag");
            bumpShader = new Shader("res/vao/bump.vert", "res/vao/bump.frag");
            shadowShader = new Shader("res/vao/shadow.vert", "res/vao/shadow.frag");
            normal = new Texture("res/vao/normal.png");
            normalMap = new Texture("res/vao/normalMap.png");
            normalMap.setSmooth(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return ;
        }
        VertexArrayObject vao = buildVAOMontains(1000, 1000, 0);
        VertexArrayObject vao2 = buildVAOMontains(1000, 1000, 2);

        VertexArrayObject mapVao = new VertexArrayObject(9, 5, new int[]{3, 4, 2, 3, 3}, VertexArrayObject.Mode.TRIANGLES, VertexArrayObject.Usage.STREAM);

        mapVao.update(0, new float[]
                {
                        0,   0,   0,
                        10, 0,   0,
                        10, 10, 0,

                        0,0,0,
                        10,10,0,
                        0,10,0,

                        0,0,0,
                        0,0,-10,
                        10,0,-10,
                }
                );
        mapVao.update(1, new float[]
                {
                        1, 1, 1, 1,
                        1, 1, 1, 1,
                        1, 1, 1, 1,

                        1, 1, 1, 1,
                        1, 1, 1, 1,
                        1, 1, 1, 1,

                        1, 1, 1, 1,
                        1, 1, 1, 1,
                        1, 1, 1, 1,
                }
        );
        mapVao.update(2, new float[]
                {
                        1, 1,
                        1, 0,
                        0, 0,

                        1,1,
                        0,0,
                        0,1,

                        1,1,
                        0,0,
                        0,1,
                }
        );
        mapVao.update(3, new float[]
                {
                        0, 0, -1,
                        0, 0, -1,
                        0, 0, -1,

                        0, 0, -1,
                        0, 0, -1,
                        0, 0, -1,

                        0, 0, -1,
                        0, 0, -1,
                        0, 0, -1,
                }
        );
        mapVao.update(4, new float[]
                {
                        1, 0, 0,
                        1, 0, 0,
                        1, 0, 0,

                        1, 0, 0,
                        1, 0, 0,
                        1, 0, 0,

                        1, 0, 0,
                        1, 0, 0,
                        1, 0, 0,
                }
        );

        Vector2f a = new Vector2f(0,1);
        Vector2f b = new Vector2f(1,0);
        System.out.println(Vector2f.radianBetween(a, b));
        Vector3f a3 = new Vector3f(1, 0, 0);
        Vector3f b3 = new Vector3f(0.5f,0.5f, 1);
        System.out.println(Vector3f.radianBetween(a3, b3));


        Clock clk = new Clock();
        Time total = Time.zero();

        boolean camera = false;

        while (window.isOpen())
        {
            Time elapsed = clk.restart();
            total.add(elapsed);

            Event event;
            while ((event = window.pollEvents()) != null) {
                if (event.type == Event.Type.CLOSE) {
                    renderTexture.getTexture().toImage().saveAs("capture.png");
                    window.close();
                }
                if (event.type == Event.Type.KEYRELEASED && event.keyReleased == AZERTYLayout.RETURN.getKeyID()) {
                    //camera = !camera;

                    camera3D_2.setPosition(camera3D.getCenter());
                    camera3D_2.look(camera3D.getOrientation());
                }

            }



            float cos = (float)Math.cos(total.asSeconds());
            float sin = (float)Math.sin(total.asSeconds());




            renderTexture.clear(Color.White);

            Texture.DefaultTexture().bind(0);
            vaoShader.bind();
            camera3D_2.invert(false, false);
            camera3D_2.apply(shadowShader, "depthMVP");
            //vao.draw();
            //vao2.draw();
            mapVao.draw(vaoShader);

            renderTexture.display();


            Vector3f pos0 = new Vector3f(0 + cos * 50,   0 + sin * - 500,   sin * 50);
            Vector3f pos1 = new Vector3f(500 + sin * 50, 0 + cos * 500,   0);
            Vector3f pos2 = new Vector3f(500 + sin * 50, 500 + sin * 500, cos * 300);


            mode.apply(camera3D, new Time((long)(elapsed.asNanoseconds())));

            //display
            window.clear(Color.Blue);


            Texture.DefaultTexture().bind(0);
            vaoShader.bind();
            camera3D.apply(vaoShader, "mvp");
            vao.draw(vaoShader);

            ConstShader testShader = bumpShader;
            normal.bind(0);
            normalMap.bind(1);
            testShader.bind();
            camera3D.apply(testShader, "mvp");
            GL20.glUniform3f(testShader.getUniformLocation("lightPos"), camera3D.getCenter().x, camera3D.getCenter().y, camera3D.getCenter().z);
            //GL20.glUniform3f(testShader.getUniformLocation("lightPos"), cos*5 + 5, sin*5 + 5, -2);
            GL20.glUniform1f(testShader.getUniformLocation("invRadius"), 1.f / 1000.f);
            GL20.glUniform1f(testShader.getUniformLocation("lightAmbiant"), 0.5f);
            GL20.glUniform1f(testShader.getUniformLocation("lightDiffuse"), 0.5f);
            GL20.glUniform1f(testShader.getUniformLocation("lightSpecular"), 0.0f);
            GL20.glUniform4f(testShader.getUniformLocation("materialAmbiant"), 0.0f, 1.0f, 1.0f, 1.f);
            GL20.glUniform4f(testShader.getUniformLocation("materialDiffuse"), 1.0f, 1.0f, 1.0f, 1.f);
            GL20.glUniform4f(testShader.getUniformLocation("materialSpecular"), 0.0f, 0.0f, 0.0f, 1.f);
            GL20.glUniform1f(testShader.getUniformLocation("materialShininess"), 10.f);
            mapVao.draw(testShader);

            Texture.DefaultTexture().bind(0);
            vaoShader.bind();
            camera3D.apply(vaoShader, "mvp");
            vao.draw(vaoShader);
            deer.draw(vaoShader);

            /*Texture.DefaultTexture().bind(0);
            renderTexture.getTexture().bind(1);
            shadowMapShader.bind();
            camera3D.apply(shadowMapShader, "mvp");
            //lightCamera.apply(shadowMapShader, "depthMVP");
            camera3D_2.invert(false, false);
            camera3D_2.apply(shadowMapShader, "depthMVP");
            //vao.draw();
            //vao2.draw();
            normalMap.bind(0);
            mapVao.draw();*/

            window.draw(sprite);

            window.display();
        }
    }


    public static VertexArrayObject buildVAOMontains(int w, int h, int y)
    {
        VertexArrayObject vao = new VertexArrayObject(w*h*6, 3, new int[]{3,4,2}, VertexArrayObject.Mode.TRIANGLES, VertexArrayObject.Usage.STREAM);

        float[] v = new float[w*h*3*3*2];
        float[] c = new float[w*h*4*3*2];

        int cc = 0;
        int vc = 0;
        for (int j=0 ; j < h ; ++j)
        {
            for (int i=0 ; i < w ; ++i)
            {
                //1
                v[(i)*18 + 0 + j*w*6] = 10*(i);
                //v[(i)*18 + 1 + j*w*6] = 10*(float)Math.random();
                v[(i)*18 + 2 + j*w*6] = 100*(j) + y;

                v[(i)*18 + 3+ j*w*6] = 10*(i+1);
                //v[(i)*18 + 4+ j*w*6] = 10*(float)Math.random();
                v[(i)*18 + 5+ j*w*6] = 100*(j);

                v[(i)*18 + 6+ j*w*6] = 10*(i+1) + y;
                //v[(i)*18 + 7+ j*w*6] = 10*(float)Math.random();
                v[(i)*18 + 8+ j*w*6] = 100*(j+1);

                c[(i)*24 + 0 + j*w*8] = j / (float)h;
                c[(i)*24 + 1 + j*w*8] = 1;
                c[(i)*24 + 2 + j*w*8] = 1;
                c[(i)*24 + 3 + j*w*8] = 1;


                c[(i)*24 + 4 + j*w*8] = i / (float)w;
                c[(i)*24 + 5 + j*w*8] = 1;
                c[(i)*24 + 6 + j*w*8] = 1;
                c[(i)*24 + 7 + j*w*8] = 1;

                c[(i)*24 + 8 + j*w*8] = 1;
                c[(i)*24 + 9 + j*w*8] = 1;
                c[(i)*24 + 10 + j*w*8] = 1;
                c[(i)*24 + 11 + j*w*8] = 1;

                //2
                v[(i)*18 + 9+ j*w*6] = 10*(i);
                v[(i)*18 + 10+ j*w*6] = 10*(float)Math.random() + y;
                v[(i)*18 + 11+ j*w*6] = 100*(j);

                v[(i)*18 + 12+ j*w*6] = 10*(i);
                v[(i)*18 + 13+ j*w*6] = 10*(float)Math.random() + y;
                v[(i)*18 + 14+ j*w*6] = 100*(j+1);

                v[(i)*18 + 15+ j*w*6] = 10*(i+1);
                v[(i)*18 + 16+ j*w*6] = 10*(float)Math.random() + y;
                v[(i)*18 + 17+ j*w*6] = 100*(j+1);

                c[(i)*24 + 12 + j*w*8] = 1;
                c[(i)*24 + 13 + j*w*8] = 1;
                c[(i)*24 + 14 + j*w*8] = 1;
                c[(i)*24 + 15 + j*w*8] = 1;

                c[(i)*24 + 16 + j*w*8] = 1;
                c[(i)*24 + 17 + j*w*8] = 1;
                c[(i)*24 + 18 + j*w*8] = 1;
                c[(i)*24 + 19 + j*w*8] = 1;

                c[(i)*24 + 20 + j*w*8] = 1;
                c[(i)*24 + 21 + j*w*8] = 1;
                c[(i)*24 + 22 + j*w*8] = 1;
                c[(i)*24 + 23 + j*w*8] = 1;

                vc += 18;
                cc += 24;
            }
        }

        System.out.println(w*h*2*3 + "> " + vc / 3.f + ":" + cc / 4.f + "(test " + v.length / 3.f + ", " + c.length / 4.f + ")");

        vao.update(0, v);
        vao.update(1, c);
        return vao;
    }


}
