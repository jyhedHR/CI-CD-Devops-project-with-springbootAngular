pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }
    environment {
        DOCKER_IMAGE = "jyhedhr/gestion-station-ski"
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Hello Test') {
            steps {
                echo 'Hi Jihed'
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'Jihed_Horchani_4twin5',
                    url: 'https://github.com/jyhedHR/4twin5_Group2_gestion-station-skier.git',
                    credentialsId: 'gitDev'
            }
        }

        stage('Clean compile') {
            steps {
                sh 'mvn clean package -DskipTests'  // Ensure JAR is built
            }
        }

        stage('Test Project') {
            steps {
                sh 'mvn -Dtest=SkierServicesImplTest test'
            }
        }

        stage('Run SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'sudo docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .'  // Removed sudo
            }
        }
          stage('Push Docker Image') {
               steps {
        // Use the credentials stored in Jenkins
        withCredentials([string(credentialsId: 'DockerHub', variable: 'DockerHub')]) {
            // Log in to Docker Hub using the access token
            sh 'echo $DockerHub | sudo docker login -u jyhedhr --password-stdin'
            
            // Tag the image for pushing to Docker Hub
            sh "sudo docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:${DOCKER_TAG}"
            
            // Push the tagged image to Docker Hub
            sh "sudo docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
        }
    }
        }


        stage('Deploy') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
        stage('Docker Compose Up') {
                            steps {

                            sh ' sudo docker compose build  '
                                sh ' sudo docker compose up -d  '
                            }
                        }
    }
}

