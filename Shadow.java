package com.Diamond.SGL;
import android.opengl.GLES32;

public class Shadow {
    public int FBO;
    public int RBO;
    public int shadow_texture;
    
    public Shadow(int width,int height) {
        shadow_texture = Texture.genTexture();
        FBO = BufferUtil.genFBO();
        RBO = BufferUtil.genRBO();
        
        BufferUtil.bindRBO(RBO,GLES32.GL_DEPTH_COMPONENT16,width,height);
        
        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,shadow_texture);
        Texture.texParameter(GLES32.GL_TEXTURE_2D,Texture.default_parameters);
        GLES32.glTexImage2D(GLES32.GL_TEXTURE_2D,0,GLES32.GL_R16F,width,height,0,GLES32.GL_RED,GLES32.GL_FLOAT,null);
        BufferUtil.bindFBO(FBO);
        GLES32.glFramebufferTexture2D(GLES32.GL_FRAMEBUFFER,GLES32.GL_COLOR_ATTACHMENT0,GLES32.GL_TEXTURE_2D,shadow_texture,0);
        GLES32.glFramebufferRenderbuffer(GLES32.GL_FRAMEBUFFER,GLES32.GL_DEPTH_ATTACHMENT,GLES32.GL_RENDERBUFFER,RBO);
        BufferUtil.bindFBO(0);
        BufferUtil.bindFBO(0);
        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,0);
    }
    
    public Shadow genShadow() {
        BufferUtil.bindFBO(FBO);
        return this;
    }
    
    public Shadow endGen() {
        BufferUtil.bindFBO(0);
        return this;
    }
    
    public Shadow drawShadow(int textureLocation) {
        Texture.enableTextureUnit(shadow_texture,GLES32.GL_TEXTURE_2D,textureLocation);
        return this;
    }
}
