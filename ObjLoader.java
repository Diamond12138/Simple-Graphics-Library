package com.Diamond.SGL;

import java.io.InputStream;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import android.util.Log;

public class ObjLoader {
    public float[] vertices = null;
    public float[] colors = null;
    public float[] normals = null;
    public float[] texCoords = null;
    public int[] indices = null;

    public ObjLoader() {}
    public ObjLoader(InputStream is) {
        ObjLoader result = ObjLoader.loadFromStream_Triangles(is);
        vertices = result.vertices;
        normals = result.normals;
        texCoords = result.texCoords;
        indices = result.indices;
    }
    public ObjLoader(float[] v) {
        vertices = v;
    }
    public ObjLoader(float[] v,float[] c) {
        vertices = v;
        colors = c;
    }
    public ObjLoader(float[] v,float[] c, int[] i) {
        vertices = v;
        colors = c;
        indices = i;
    }
    public ObjLoader(float[] v, float[] n, float[] t, int[] i) {
        vertices = v;
        normals = n;
        texCoords = t;
        indices = i;
    }
    public ObjLoader(float[] v,float[] c, float[] n, float[] t, int[] i) {
        vertices = v;
        colors = c;
        normals = n;
        texCoords = t;
        indices = i;
    }

    public static ObjLoader loadFromStreamOnlyVerticesAndIndices(InputStream is) {
        ArrayList<Float> vertexArray = new ArrayList<Float>();
        ArrayList<Integer> indexArray = new ArrayList<Integer>();

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = null;

        try {
            while ((temp = br.readLine()) != null) {
                String[] templist = temp.split("[ ]+");
                if (templist[0].trim().equals("v")) {
                    vertexArray.add(Float.parseFloat(templist[1]));
                    vertexArray.add(Float.parseFloat(templist[2]));
                    vertexArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("f")) {
                    String[] tempf1 = templist[1].split("[/]+");
                    String[] tempf2 = templist[2].split("[/]+");
                    String[] tempf3 = templist[3].split("[/]+");
                    indexArray.add(toInt(tempf1[0]) - 1);
                    indexArray.add(toInt(tempf2[0]) - 1);
                    indexArray.add(toInt(tempf3[0]) - 1);
                }
            }
        } catch (IOException e) {
            Log.e("ObjLoader", "");
            e.printStackTrace();
        }

        float[] vertices = new float[vertexArray.size()];
        for (int i = 0;i < vertexArray.size();i++) {
            vertices[i] = vertexArray.get(i);
        }
        int[] indices = new int[indexArray.size()];
        for (int i = 0;i < indexArray.size();i++) {
            indices[i] = indexArray.get(i);
        }

        ObjLoader result = new ObjLoader(vertices, null, null, indices);

        return result;
    }



    public static ObjLoader loadFromStream_Triangles(InputStream is) {
        ArrayList<Float> vertexArray = new ArrayList<Float>();
        ArrayList<Float> normalArray = new ArrayList<Float>();
        ArrayList<Float> texCoordArray = new ArrayList<Float>();

        ArrayList<Float> vertexArrayResult = new ArrayList<Float>();
        ArrayList<Float> normalArrayResult = new ArrayList<Float>();
        ArrayList<Float> texCoordArrayResult = new ArrayList<Float>();

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = null;

        try {
            while ((temp = br.readLine()) != null) {
                String[] templist = temp.split("[ ]+");
                if (templist[0].trim().equals("v")) {
                    vertexArray.add(Float.parseFloat(templist[1]));
                    vertexArray.add(Float.parseFloat(templist[2]));
                    vertexArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("vn")) {
                	normalArray.add(Float.parseFloat(templist[1]));
                    normalArray.add(Float.parseFloat(templist[2]));
                    normalArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("vt")) {
                	texCoordArray.add(Float.parseFloat(templist[1]));
                    texCoordArray.add(Float.parseFloat(templist[2]));
                    texCoordArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("f")) {
                    String[][] temps = new String[templist.length - 1][];
                    for (int i = 0;i < temps.length;i++) {
                        temps[i] = templist[i + 1].split("[/]+");
                    }

                    int index = 0;
                    int n = temps.length - 2;

                    for (int i = 0; i < n; i++) {
                        index = toInt(temps[0][0]) - 1;
                        vertexArrayResult.add(vertexArray.get(3 * index));
                        vertexArrayResult.add(vertexArray.get(3 * index + 1));
                        vertexArrayResult.add(vertexArray.get(3 * index + 2));
                        index = toInt(temps[i + 1][0]) - 1;
                        vertexArrayResult.add(vertexArray.get(3 * index));
                        vertexArrayResult.add(vertexArray.get(3 * index + 1));
                        vertexArrayResult.add(vertexArray.get(3 * index + 2));
                        index = toInt(temps[i + 2][0]) - 1;
                        vertexArrayResult.add(vertexArray.get(3 * index));
                        vertexArrayResult.add(vertexArray.get(3 * index + 1));
                        vertexArrayResult.add(vertexArray.get(3 * index + 2));
                    }

                    if (texCoordArray.size() != 0) {
                        for (int i = 0; i < n; i++) {
                            index = toInt(temps[0][1]) - 1;
                            texCoordArrayResult.add(texCoordArray.get(3 * index));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                            index = toInt(temps[i + 1][1]) - 1;
                            texCoordArrayResult.add(texCoordArray.get(3 * index));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                            index = toInt(temps[i + 2][1]) - 1;
                            texCoordArrayResult.add(texCoordArray.get(3 * index));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                        }
                    }

                    if (normalArray.size() != 0) {
                        for (int i = 0; i < n; i++) {
                            index = toInt(temps[0][2]) - 1;
                            normalArrayResult.add(normalArray.get(3 * index));
                            normalArrayResult.add(normalArray.get(3 * index + 1));
                            normalArrayResult.add(normalArray.get(3 * index + 2));
                            index = toInt(temps[i + 1][2]) - 1;
                            normalArrayResult.add(normalArray.get(3 * index));
                            normalArrayResult.add(normalArray.get(3 * index + 1));
                            normalArrayResult.add(normalArray.get(3 * index + 2));
                            index = toInt(temps[i + 2][2]) - 1;
                            normalArrayResult.add(normalArray.get(3 * index));
                            normalArrayResult.add(normalArray.get(3 * index + 1));
                            normalArrayResult.add(normalArray.get(3 * index + 2));
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("ObjLoader", "");
            e.printStackTrace();
        }

        float[] vertices = new float[vertexArrayResult.size()];
        for (int i = 0;i < vertexArrayResult.size();i++) {
            vertices[i] = vertexArrayResult.get(i);
        }
        float[] normals = new float[normalArrayResult.size()];
        for (int i = 0;i < normalArrayResult.size();i++) {
            normals[i] = normalArrayResult.get(i);
        }
        float[] texCoords = new float[texCoordArrayResult.size()];
        for (int i = 0;i < texCoordArrayResult.size();i++) {
            texCoords[i] = texCoordArrayResult.get(i);
        }

        ObjLoader result = new ObjLoader(vertices, normals, texCoords, null);

        return result;
    }



    public static ObjLoader loadFromStream_TriangleFan(InputStream is) {
        ArrayList<Float> vertexArray = new ArrayList<Float>();
        ArrayList<Float> normalArray = new ArrayList<Float>();
        ArrayList<Float> texCoordArray = new ArrayList<Float>();

        ArrayList<Float> vertexArrayResult = new ArrayList<Float>();
        ArrayList<Float> normalArrayResult = new ArrayList<Float>();
        ArrayList<Float> texCoordArrayResult = new ArrayList<Float>();

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = null;

        try {
            while ((temp = br.readLine()) != null) {
                String[] templist = temp.split("[ ]+");
                if (templist[0].trim().equals("v")) {
                    vertexArray.add(Float.parseFloat(templist[1]));
                    vertexArray.add(Float.parseFloat(templist[2]));
                    vertexArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("vn")) {
                	normalArray.add(Float.parseFloat(templist[1]));
                    normalArray.add(Float.parseFloat(templist[2]));
                    normalArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("vt")) {
                	texCoordArray.add(Float.parseFloat(templist[1]));
                    texCoordArray.add(Float.parseFloat(templist[2]));
                    texCoordArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("f")) {
                    String[][] temps = new String[templist.length - 1][];
                    for (int i = 0;i < temps.length;i++) {
                        temps[i] = templist[i + 1].split("[/]+");
                    }

                    int index = 0;
                    int n = temps.length - 2;

                    for (int i = 0; i < n; i++) {
                        index = toInt(temps[i][0]) - 1;
                        vertexArrayResult.add(vertexArray.get(3 * index));
                        vertexArrayResult.add(vertexArray.get(3 * index + 1));
                        vertexArrayResult.add(vertexArray.get(3 * index + 2));
                    }

                    if (texCoordArray.size() != 0) {
                        for (int i = 0; i < n; i++) {
                            index = toInt(temps[i][1]) - 1;
                            texCoordArrayResult.add(texCoordArray.get(3 * index));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                        }
                    }

                    if (normalArray.size() != 0) {
                        for (int i = 0; i < n; i++) {
                            index = toInt(temps[i][2]) - 1;
                            normalArrayResult.add(normalArray.get(3 * index));
                            normalArrayResult.add(normalArray.get(3 * index + 1));
                            normalArrayResult.add(normalArray.get(3 * index + 2));
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("ObjLoader", "");
            e.printStackTrace();
        }

        float[] vertices = new float[vertexArrayResult.size()];
        for (int i = 0;i < vertexArrayResult.size();i++) {
            vertices[i] = vertexArrayResult.get(i);
        }
        float[] normals = new float[normalArrayResult.size()];
        for (int i = 0;i < normalArrayResult.size();i++) {
            normals[i] = normalArrayResult.get(i);
        }
        float[] texCoords = new float[texCoordArrayResult.size()];
        for (int i = 0;i < texCoordArrayResult.size();i++) {
            texCoords[i] = texCoordArrayResult.get(i);
        }

        ObjLoader result = new ObjLoader(vertices, normals, texCoords, null);

        return result;
    }



    public static int toInt(String str) {
        return Integer.parseInt(str);
    }
}
