#include "Camera.hpp"

namespace gps {

	Camera::Camera(glm::vec3 cameraPosition, glm::vec3 cameraTarget, glm::vec3 cameraUpDirection) {

		this->cameraPosition = cameraPosition;
		this->cameraTarget = cameraTarget;
		this->cameraFrontDirection = glm::normalize(cameraPosition - cameraTarget);
		this->cameraRightDirection = glm::normalize(glm::cross(cameraUpDirection, cameraFrontDirection));
		this->cameraUpDirection = glm::cross(cameraFrontDirection, cameraRightDirection);

	}

	glm::mat4 Camera::getViewMatrix() {
		return glm::lookAt(cameraPosition, cameraPosition + cameraFrontDirection, cameraUpDirection);

	}
	glm::vec3 Camera::getCameraPosition() {
		return cameraPosition;
	}
	glm::vec3 Camera::getCameraTarget()
	{
		return cameraTarget;
	}

	void Camera::move(MOVE_DIRECTION direction, float speed) {
		switch (direction) {
		case MOVE_FORWARD:
			cameraPosition += cameraFrontDirection * speed;
			break;

		case MOVE_BACKWARD:
			cameraPosition -= cameraFrontDirection * speed;
			break;

		case MOVE_RIGHT:
			cameraPosition -= cameraRightDirection * speed;
			break;

		case MOVE_LEFT:
			cameraPosition += cameraRightDirection * speed;
			break;
		}
	}

	void Camera::rotate(float pitch, float yaw) {
		glm::vec3 front;
		front.x = cos(glm::radians(yaw)) * cos(glm::radians(pitch));
		front.y = sin(glm::radians(pitch));
		front.z = sin(glm::radians(yaw)) * cos(glm::radians(pitch));

		cameraFrontDirection = glm::normalize(front);
		cameraRightDirection = glm::cross(cameraUpDirection, cameraFrontDirection);
	}

}