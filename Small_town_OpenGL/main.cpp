#define GLEW_STATIC
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/matrix_inverse.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "Shader.hpp"
#include "Model3D.hpp"
#include "Camera.hpp"
#include "SkyBox.hpp"

#include <iostream>


gps::SkyBox skyboxD, skyboxS, skyboxR;
gps::Shader skyboxShader;

int glWindowWidth = 1900;
int glWindowHeight = 2000;
int retina_width, retina_height;
GLFWwindow* glWindow = NULL;

const unsigned int SHADOW_WIDTH = 2048;
const unsigned int SHADOW_HEIGHT = 2048;

glm::mat4 model;
GLuint modelLoc;
glm::mat4 view;
GLuint viewLoc;
glm::mat4 projection;
GLuint projectionLoc;
glm::mat3 normalMatrix;
GLuint normalMatrixLoc;
glm::mat4 lightRotation;

glm::vec3 lightDir;
GLuint lightDirLoc;
glm::vec3 lightColor;
GLuint lightColorLoc;

gps::Camera myCamera(
	glm::vec3(40.0f, 20.0f, 0.0f),
	glm::vec3(-10.0f, 0.0f, 55.0f),
	glm::vec3(0.0f, 1.0f, 0.0f));
GLfloat cameraSpeed = 0.7f;

GLboolean pressedKeys[1024];
float angleY = 0.0f;
GLfloat lightAngle;


gps::Model3D screenQuad;
gps::Model3D sun;
gps::Model3D snow;
gps::Model3D rain;
gps::Model3D scene;
gps::Model3D ironman;


gps::Shader myBasicShader;
gps::Shader lightSun;
gps::Shader screenQuadShader;
gps::Shader depthMapShader;


GLuint shadowMapFBO;
GLuint depthMapTexture;
GLint fog;
GLuint rainNormalMatrixLoc;
GLuint snowNormalMatrixLoc;
GLuint ironmanNormalMatrixLoc;

glm::mat4 ironmanModel;
glm::mat3 ironmanNormalMatrix;

glm::mat4 snowModel;
glm::mat3 snowNormalMatrix;

glm::mat4 rainModel;
glm::mat3 rainNormalMatrix;

bool showDepthMap;
float snowAux = -70.0f;
float snowSpeed = 0.5f;
float rainAux = -70.0f;
float rainSpeed = 1.0f;

float ironmanAux = 0.0f;
float ironmanSpeed = 0.01f;

float auxX = -90;
float auxY;
float rotationSpeed = 0.8f;
float cameraAngle = 270;
int sunSnowRain = 1;
bool bSunSnowRain = false;
bool bNoapte = false;
int control = 1;
float suny = -1;
float sunx=3.5;
float fogDensity = 0;
float bFog = false;

float xaux = 0.0f, yaux = 90.0f;
GLboolean camera = false;

GLenum glCheckError_(const char* file, int line)
{
	GLenum errorCode;
	while ((errorCode = glGetError()) != GL_NO_ERROR) {
		std::string error;
		switch (errorCode) {
		case GL_INVALID_ENUM:
			error = "INVALID_ENUM";
			break;
		case GL_INVALID_VALUE:
			error = "INVALID_VALUE";
			break;
		case GL_INVALID_OPERATION:
			error = "INVALID_OPERATION";
			break;
		case GL_STACK_OVERFLOW:
			error = "STACK_OVERFLOW";
			break;
		case GL_STACK_UNDERFLOW:
			error = "STACK_UNDERFLOW";
			break;
		case GL_OUT_OF_MEMORY:
			error = "OUT_OF_MEMORY";
			break;
		case GL_INVALID_FRAMEBUFFER_OPERATION:
			error = "INVALID_FRAMEBUFFER_OPERATION";
			break;
		}
		std::cout << error << " | " << file << " (" << line << ")" << std::endl;
	}
	return errorCode;
}
#define glCheckError() glCheckError_(__FILE__, __LINE__)

void windowResizeCallback(GLFWwindow* window, int width, int height) {
	fprintf(stdout, "window resized to width: %d , and height: %d\n", width, height);
	//TODO	
}

void keyboardCallback(GLFWwindow* window, int key, int scancode, int action, int mode) {
	if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
		glfwSetWindowShouldClose(window, GL_TRUE);

	if (key == GLFW_KEY_M && action == GLFW_PRESS)
		showDepthMap = !showDepthMap;

	if (key >= 0 && key < 1024)
	{
		if (action == GLFW_PRESS)
			pressedKeys[key] = true;
		else if (action == GLFW_RELEASE)
			pressedKeys[key] = false;
	}
}

void mouseCallback(GLFWwindow* window, double xpos, double ypos) {
	int width, height;
	glfwGetWindowSize(window, &width, &height);

	float x = (xpos - width / 2) * 0.09f;
	float y = (height / 2 - ypos) * 0.09f;
	auxX += x;
	auxY += y;

	myCamera.rotate(auxY, auxX);

	view = myCamera.getViewMatrix();
	myBasicShader.useShaderProgram();
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
	normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	glfwSetCursorPos(window, width / 2, height / 2);
}

void initSkyBox()
{
	std::vector<const GLchar*> face1;
	face1.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Day/posx.jpg");
	face1.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Day/negx.jpg");
	face1.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Day/posy.jpg");
	face1.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Day/negy.jpg");
	face1.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Day/posz.jpg");
	face1.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Day/negz.jpg");

	skyboxD.Load(face1);

	std::vector<const GLchar*> face2;
	face2.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Clouds/left.png");
	face2.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Clouds/right.png");
	face2.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Clouds/top.png");
	face2.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Clouds/bottom.png");
	face2.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Clouds/front.png");
	face2.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Clouds/back.png");

	skyboxR.Load(face2);

	std::vector<const GLchar*> face3;
	face3.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Snow/posx.jpg");
	face3.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Snow/negx.jpg");
	face3.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Snow/posy.jpg");
	face3.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Snow/negy.jpg");
	face3.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Snow/posz.jpg");
	face3.push_back("/Users/crist/Desktop/GP/MyGLproj/skybox/Snow/negz.jpg");

	skyboxS.Load(face3);
}
void cameraAnimation()
{
	myCamera.move(gps::MOVE_FORWARD, 0.25);

	view = myCamera.getViewMatrix();
	myBasicShader.useShaderProgram();
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

	normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	yaux -= 0.2f;
	myCamera.rotate(xaux, yaux);
	view = myCamera.getViewMatrix();
	myBasicShader.useShaderProgram();
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
	normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
}
void processMovement()
{
	if (pressedKeys[GLFW_KEY_UP]) {
		myCamera.move(gps::MOVE_FORWARD, cameraSpeed);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_DOWN]) {
		myCamera.move(gps::MOVE_BACKWARD, cameraSpeed);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_LEFT]) {
		myCamera.move(gps::MOVE_LEFT, cameraSpeed);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_RIGHT]) {
		myCamera.move(gps::MOVE_RIGHT, cameraSpeed);
		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_P])
	{
		cameraAngle += rotationSpeed;
		if (cameraAngle > 360.0f)
			cameraAngle -= 360.0f;

		myCamera.rotate(0, cameraAngle);

		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_O])
	{
		cameraAngle -= rotationSpeed;
		if (cameraAngle < 0.0f)
			cameraAngle += 360.0f;
		myCamera.rotate(0, cameraAngle);

		view = myCamera.getViewMatrix();
		myBasicShader.useShaderProgram();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	}
	if (pressedKeys[GLFW_KEY_U])
	{
		bSunSnowRain = true;
	}
	else
	{
		if (bSunSnowRain)
		{
			if (sunSnowRain >=3)
			{
				sunSnowRain = 0;
			}
			sunSnowRain++;
		}

		bSunSnowRain = false;
	}
	if (pressedKeys[GLFW_KEY_M]) 
	{
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	if (pressedKeys[GLFW_KEY_N]) 
	{
		glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
	}
	if (pressedKeys[GLFW_KEY_B])
	{
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	if (pressedKeys[GLFW_KEY_I])
	{
		bNoapte = true;
	}
	else if (bNoapte)
	{
		myBasicShader.useShaderProgram();

		switch (control) {

		case 1:
			lightColor = glm::vec3(false);
			lightColor += glm::vec3(0.03f, 0.03f, 0.3f) * (1.0f - false);
			glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightColor"), 1, glm::value_ptr(lightColor));
			control = 2;
			break;
		case 2:
			lightColor = glm::vec3(false);
			lightColor += glm::vec3(0.03f, 0.2f, 0.03f) * (1.0f - false);
			glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightColor"), 1, glm::value_ptr(lightColor));
			control = 3;
			break;
		case 3:
			lightColor = glm::vec3(false);
			lightColor += glm::vec3(0.2f, 0.03f, 0.03f) * (1.0f - false);
			glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightColor"), 1, glm::value_ptr(lightColor));
			control = 4;
			break;
		case 4:
			lightColor = glm::vec3(true);
			lightColor += glm::vec3(0.03f, 0.03f, 0.3f) * (1.0f - true);
			glUniform3fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightColor"), 1, glm::value_ptr(lightColor));
			control = 1;
			break;
		default:
			break;
		}

		bNoapte = false;
	}
	if (pressedKeys[GLFW_KEY_Q]) {
		angleY -= 1.0f;
	}
	if (pressedKeys[GLFW_KEY_E]) {
		angleY += 1.0f;
	}
	if (pressedKeys[GLFW_KEY_F])
	{
		bFog = true;
	}
	else
	{
		if (bFog)
		{
			fogDensity = (fogDensity + 0.015) * (fogDensity <= 0.05);
			myBasicShader.useShaderProgram();
			fog = glGetUniformLocation(myBasicShader.shaderProgram, "fogDensity");
			glUniform1f(fog, fogDensity);
		}
		bFog = false;
	}
	if (pressedKeys[GLFW_KEY_S]) {
		if (sunSnowRain == 3) {
			snowAux = 70.0f;
		}
		if (sunSnowRain == 2) {
			rainAux = 70.0f;
		}
	}
	if (pressedKeys[GLFW_KEY_R]) {
		camera = false;
	}
	if (pressedKeys[GLFW_KEY_T]) {
		camera = true;
	}
}

bool initOpenGLWindow()
{
	if (!glfwInit()) {
		fprintf(stderr, "ERROR: could not start GLFW3\n");
		return false;
	}

	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	glfwWindowHint(GLFW_SRGB_CAPABLE, GLFW_TRUE);
	glfwWindowHint(GLFW_SAMPLES, 4);

	glWindow = glfwCreateWindow(glWindowWidth, glWindowHeight, "OpenGL Shader Example", NULL, NULL);
	if (!glWindow) {
		fprintf(stderr, "ERROR: could not open window with GLFW3\n");
		glfwTerminate();
		return false;
	}

	glfwSetWindowSizeCallback(glWindow, windowResizeCallback);
	glfwSetKeyCallback(glWindow, keyboardCallback);
	glfwSetCursorPosCallback(glWindow, mouseCallback);
	//glfwSetInputMode(glWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	glfwMakeContextCurrent(glWindow);

	glfwSwapInterval(1);

	glewExperimental = GL_TRUE;
	glewInit();

	const GLubyte* renderer = glGetString(GL_RENDERER); 
	const GLubyte* version = glGetString(GL_VERSION); 
	printf("Renderer: %s\n", renderer);
	printf("OpenGL version supported %s\n", version);

	glfwGetFramebufferSize(glWindow, &retina_width, &retina_height);

	return true;
}

void initOpenGLState()
{
	glClearColor(0.3, 0.3, 0.3, 1.0);
	glViewport(0, 0, retina_width, retina_height);

	glEnable(GL_DEPTH_TEST); // enable depth-testing
	glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
	glEnable(GL_CULL_FACE); // cull face
	glCullFace(GL_BACK); // cull back face
	glFrontFace(GL_CCW); // GL_CCW for counter clock-wise

	glEnable(GL_FRAMEBUFFER_SRGB);
}

void initObjects() {
	screenQuad.LoadModel("objects/quad/quad.obj");
	scene.LoadModel("objects/scene/scene.obj");
	sun.LoadModel("objects/sol/Sun.obj");
	snow.LoadModel("objects/snow/snow1.obj");
	rain.LoadModel("objects/rain/rain.obj");
	ironman.LoadModel("objects/ironman/ironman.obj");
}

void initShaders() {
	myBasicShader.loadShader("shaders/shaderStart.vert", "shaders/shaderStart.frag");
	myBasicShader.useShaderProgram();
	lightSun.loadShader("shaders/lightCube.vert", "shaders/lightCube.frag");
	lightSun.useShaderProgram();
	screenQuadShader.loadShader("shaders/screenQuad.vert", "shaders/screenQuad.frag");
	screenQuadShader.useShaderProgram();
	depthMapShader.loadShader("shaders/depthMapShader.vert", "shaders/depthMapShader.frag");
	depthMapShader.useShaderProgram();

}

void initUniforms() {
	myBasicShader.useShaderProgram();

	model = glm::mat4(1.0f);
	modelLoc = glGetUniformLocation(myBasicShader.shaderProgram, "model");
	glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

	view = myCamera.getViewMatrix();
	viewLoc = glGetUniformLocation(myBasicShader.shaderProgram, "view");
	glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

	normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
	normalMatrixLoc = glGetUniformLocation(myBasicShader.shaderProgram, "normalMatrix");
	glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));

	projection = glm::perspective(glm::radians(45.0f), (float)retina_width / (float)retina_height, 0.1f, 1000.0f);
	projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
	glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));

	//set the light direction (direction towards the light)
	lightDir = glm::vec3(3.50f, -1.0f, 2.0f);
	lightRotation = glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f));
	lightDirLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightDir");
	glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightRotation)) * lightDir));

	//set light color
	lightColor = glm::vec3(1.0f, 1.0f, 1.0f); //white light
	lightColorLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor");
	glUniform3fv(lightColorLoc, 1, glm::value_ptr(lightColor));

	lightSun.useShaderProgram();
	glUniformMatrix4fv(glGetUniformLocation(lightSun.shaderProgram, "projection"), 1, GL_FALSE, glm::value_ptr(projection));

	fog = glGetUniformLocation(myBasicShader.shaderProgram, "fogDensity");
	glUniform1f(fog, (GLfloat)fogDensity);
}

void initFBO() {
	glGenFramebuffers(1, &shadowMapFBO);

	//create depth texture for FBO
	glGenTextures(1, &depthMapTexture);
	glBindTexture(GL_TEXTURE_2D, depthMapTexture);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	float borderColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);

	//attach texture to FBO
	glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMapTexture, 0);

	//bind nothing to attachment points
	glDrawBuffer(GL_NONE);
	glReadBuffer(GL_NONE);

	//unbind until ready to use
	glBindFramebuffer(GL_FRAMEBUFFER, 0);

}

glm::mat4 computeLightSpaceTrMatrix() {

	//glm::mat4 lightView = glm::lookAt(lightDir, glm::vec3(0.0f), glm::vec3(0.0f, 1.0f, 0.0f));
	glm::mat4 lightView = glm::lookAt(glm::mat3(lightRotation) * lightDir, glm::vec3(0.0f), glm::vec3(0.0f, 1.0f, 0.0f)); //incepe lumina
	const GLfloat near_plane = -80.0f, far_plane = 40.0f;
	glm::mat4 lightProjection = glm::ortho(-100.0f, 100.0f, -100.0f, 100.0f, near_plane, far_plane);// de la near la far se vad umbrele
	glm::mat4 lightSpaceTrMatrix = lightProjection * lightView;

	return lightSpaceTrMatrix;
}


void drawObjects(gps::Shader shader, bool depthPass) {

	shader.useShaderProgram();

	model = glm::rotate(glm::mat4(1.0f), glm::radians(angleY), glm::vec3(0.0f, 1.0f, 0.0f));
	glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));

	if (!depthPass) { 
		normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
		glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
	}
	scene.Draw(shader);


	ironmanModel = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, ironmanAux, 0.0f));
	glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(ironmanModel));
	if (!depthPass) {
		ironmanNormalMatrix = glm::mat3(glm::inverseTranspose(view * ironmanModel));
		glUniformMatrix3fv(ironmanNormalMatrixLoc, 1, GL_FALSE, glm::value_ptr(ironmanNormalMatrix));
	}
	ironman.Draw(shader);


	myBasicShader.useShaderProgram();
	if (sunSnowRain == 3) {
		snowModel = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, snowAux, 0.0f));
		glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(snowModel));
		if (!depthPass) {
			snowNormalMatrix = glm::mat3(glm::inverseTranspose(view * snowModel));
			glUniformMatrix3fv(snowNormalMatrixLoc, 1, GL_FALSE, glm::value_ptr(snowNormalMatrix));
		}

		snow.Draw(shader);
	}

	if (sunSnowRain == 2) {
		rainModel = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, rainAux, 0.0f));
		glUniformMatrix4fv(glGetUniformLocation(shader.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(rainModel));
		if (!depthPass) {
			rainNormalMatrix = glm::mat3(glm::inverseTranspose(view * rainModel));
			glUniformMatrix3fv(rainNormalMatrixLoc, 1, GL_FALSE, glm::value_ptr(rainNormalMatrix));
		}

		rain.Draw(shader);
	}
}

int i = 0;

void renderScene() {

	depthMapShader.useShaderProgram();


	glUniformMatrix4fv(glGetUniformLocation(depthMapShader.shaderProgram, "lightSpaceTrMatrix"), 1, GL_FALSE, glm::value_ptr(computeLightSpaceTrMatrix()));
	glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
	glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
	glClear(GL_DEPTH_BUFFER_BIT);
	drawObjects(depthMapShader, 1);
	glBindFramebuffer(GL_FRAMEBUFFER, 0);
	
	if (i >=0)
	{
		ironmanAux += ironmanSpeed;
		i++;
	}
	if (i == 200)
		i = -200;
	if (i < 0)
	{
		ironmanAux -= ironmanSpeed;
		i++;
	}
	if (sunSnowRain == 1)
	{
		if (suny < 27.4 && sunx < 22.9)
		{
			lightDir += glm::vec3(0.01f, 0.01f, 0.0f);
			suny += 0.01;
			sunx += 0.01;
		}

		if (sunx >= 22)
		{
			lightDir += glm::vec3(0.01f, -0.01f, 0.0f);
			suny -= 0.01;
			sunx += 0.01;
		}
	}
	if (sunSnowRain == 3)
	{
		if (snowAux > -70.0)
			snowAux -= snowSpeed;
	}
	if (sunSnowRain == 2)
	{
		if (rainAux > -70.0)
			rainAux -= rainSpeed;
	}
	if (showDepthMap) {
		glViewport(0, 0, retina_width, retina_height);

		glClear(GL_COLOR_BUFFER_BIT);

		screenQuadShader.useShaderProgram();

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, depthMapTexture);
		glUniform1i(glGetUniformLocation(screenQuadShader.shaderProgram, "depthMap"), 0);

		glDisable(GL_DEPTH_TEST);
		screenQuad.Draw(screenQuadShader);
		glEnable(GL_DEPTH_TEST);
	}
	else {
		glViewport(0, 0, retina_width, retina_height);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		myBasicShader.useShaderProgram();

		view = myCamera.getViewMatrix();
		glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

		lightRotation = glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f));
		glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightRotation)) * lightDir));

		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, depthMapTexture);
		glUniform1i(glGetUniformLocation(myBasicShader.shaderProgram, "shadowMap"), 3);

		glUniformMatrix4fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightSpaceTrMatrix"),
			1,
			GL_FALSE,
			glm::value_ptr(computeLightSpaceTrMatrix()));

		drawObjects(myBasicShader, false);

		lightSun.useShaderProgram();

		glUniformMatrix4fv(glGetUniformLocation(lightSun.shaderProgram, "view"), 1, GL_FALSE, glm::value_ptr(view));

		model = lightRotation;
		model = glm::scale(model, glm::vec3(15.0f, 15.0f, 15.0f));
		model = glm::translate(model, 2.0f * lightDir);
		glUniformMatrix4fv(glGetUniformLocation(lightSun.shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));

		if (sunSnowRain == 1) {
			sun.Draw(lightSun);
		}

	
		if (sunSnowRain==1) {
			skyboxShader.useShaderProgram();
			skyboxD.Draw(skyboxShader, view, projection);
		}
		else {
			if (sunSnowRain == 2)
			{
				skyboxShader.useShaderProgram();
				skyboxR.Draw(skyboxShader, view, projection);
			}
			else
			{
				skyboxShader.useShaderProgram();
				skyboxS.Draw(skyboxShader, view, projection);
			}
		}
	}


}

void cleanup() {
	glDeleteTextures(1, &depthMapTexture);
	glBindFramebuffer(GL_FRAMEBUFFER, 0);
	glDeleteFramebuffers(1, &shadowMapFBO);
	glfwDestroyWindow(glWindow);
	glfwTerminate();
}

void initSkyBoxShader()
{
	skyboxShader.loadShader("shaders/skyboxShader.vert", "shaders/skyboxShader.frag");
	skyboxShader.useShaderProgram();
	view = myCamera.getViewMatrix();
	glUniformMatrix4fv(glGetUniformLocation(skyboxShader.shaderProgram, "view"), 1, GL_FALSE,
		glm::value_ptr(view));

	projection = glm::perspective(glm::radians(45.0f), (float)retina_width / (float)retina_height, 0.1f, 1000.0f);
	glUniformMatrix4fv(glGetUniformLocation(skyboxShader.shaderProgram, "projection"), 1, GL_FALSE,
		glm::value_ptr(projection));
}


int main(int argc, const char* argv[]) {

	if (!initOpenGLWindow()) {
		glfwTerminate();
		return 1;
	}

	initOpenGLState();
	initObjects();
	initShaders();
	initUniforms();
	initFBO();
	initSkyBox();
	initSkyBoxShader();

	glCheckError();


	while (!glfwWindowShouldClose(glWindow)) {
		if (camera == true)
			cameraAnimation();
		processMovement();
		renderScene();

		glfwPollEvents();
		glfwSwapBuffers(glWindow);
	}

	cleanup();

	return 0;
}
