/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.rendering.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.GL11.glViewport;

/**

 */
public final class OpenGLUtils {

    private OpenGLUtils() {
        // Utility class, no instance required
    }

    /**
     * Removes the rotation and scale part of the current OpenGL matrix.
     * Can be used to render billboards like particles.
     */
    public static void applyBillboardOrientation() {
        // Fetch the current modelview matrix
        final FloatBuffer model = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, model);

        // And undo all rotations and scaling
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    model.put(i * 4 + j, 1.0f);
                } else {
                    model.put(i * 4 + j, 0.0f);
                }
            }
        }

        GL11.glLoadMatrix(model);
    }

    /**
     * Sets the viewport of the currently bound FBO to the dimensions of the FBO
     * given as parameter.
     *
     * @param fbo The FBO whose dimensions will be matched by the viewport of the currently bound FBO.
     */
    public static void setViewportToSizeOf(FBO fbo) {
        glViewport(0, 0, fbo.width(), fbo.height());
    }

    /**
     * Unbinds any currently bound FBO and binds the default Frame Buffer,
     * which is usually the Display (be it the full screen or a window).
     */
    public static void bindDisplay() {
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }
}
